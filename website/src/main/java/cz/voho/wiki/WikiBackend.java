package cz.voho.wiki;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import cz.voho.exception.ContentNotFoundException;
import cz.voho.utility.Constants;
import cz.voho.utility.LambdaClient;
import cz.voho.wiki.image.CachingWikiImageRepository;
import cz.voho.wiki.image.DummyWikiImageRepository;
import cz.voho.wiki.image.LambdaWikiImageRepository;
import cz.voho.wiki.image.WikiImageRepository;
import cz.voho.wiki.model.ParsedWikiPage;
import cz.voho.wiki.model.WikiContext;
import cz.voho.wiki.model.WikiPageReference;
import cz.voho.wiki.model.WikiPageReferences;
import cz.voho.wiki.page.parsed.CachingParsedWikiPageRepository;
import cz.voho.wiki.page.parsed.DefaultParsedWikiPageRepository;
import cz.voho.wiki.page.parsed.ParsedWikiPageRepository;
import cz.voho.wiki.page.source.DefaultWikiPageSourceRepository;
import cz.voho.wiki.page.source.WikiPageSourceRepository;
import cz.voho.wiki.parser.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class WikiBackend {
    public static final WikiBackend SINGLETON = new WikiBackend();

    private static final Logger LOG = LoggerFactory.getLogger(WikiBackend.class);
    private static final String MISSING_WIKI_PAGE_ID = "404";

    private final ParsedWikiPageRepository parsedWikiPageRepository;
    private final WikiImageRepository wikiImageRepository;

    private WikiBackend() {
        final WikiImageRepository fallbackDelegate = new DummyWikiImageRepository();
        final WikiImageRepository primaryDelegate = new LambdaWikiImageRepository(new LambdaClient());
        final CachingWikiImageRepository cachingWikiImageRepository = new CachingWikiImageRepository(primaryDelegate, fallbackDelegate);
        this.wikiImageRepository = cachingWikiImageRepository;

        final FlexmarkWikiParser wikiParser = new FlexmarkWikiParser(
                new CodePreprocessor(cachingWikiImageRepository),
                new QuotePreprocessor(),
                new MathPreprocessor(),
                new WikiLinkPreprocessor(),
                new TodoPreprocessor(),
                new ImagePreprocessor(),
                new JavadocPreprocessor()
        );

        final WikiPageSourceRepository wikiPageSourceRepository = new DefaultWikiPageSourceRepository();
        final DefaultParsedWikiPageRepository parsedWikiPageRepository = new DefaultParsedWikiPageRepository(wikiParser, wikiPageSourceRepository);

        this.parsedWikiPageRepository = new CachingParsedWikiPageRepository(parsedWikiPageRepository);
    }

    private WikiBackend(final ParsedWikiPageRepository parsedWikiPageRepository, final WikiImageRepository wikiImageRepository) {
        this.parsedWikiPageRepository = parsedWikiPageRepository;
        this.wikiImageRepository = wikiImageRepository;
    }

    public String getExternalWikiPageLink(String wikiPageId) {
        return Constants.WEBSITE_URL_WITH_SLASH + "wiki/" + wikiPageId + "/";
    }

    public ParsedWikiPage load(final String wikiPageId) {
        try {
            return parsedWikiPageRepository.load(wikiPageId);
        } catch (ContentNotFoundException e) {
            if (wikiPageId.equals(MISSING_WIKI_PAGE_ID)) {
                throw e;
            } else {
                return load(resolveWikiPageFallback(wikiPageId));
            }
        }
    }

    private String resolveWikiPageFallback(final String missingWikiPageId) {
        final Set<String> candidates = Sets.newHashSet();

        for (final String wikiPageId : parsedWikiPageRepository.getWikiPageIds()) {
            final boolean containsAsPrefix = wikiPageId.contains(missingWikiPageId + "-");
            final boolean containsAsSuffix = wikiPageId.contains("-" + missingWikiPageId);

            if (containsAsPrefix || containsAsSuffix) {
                candidates.add(wikiPageId);
            }
        }

        if (candidates.size() == 1) {
            return candidates.iterator().next();
        }

        return MISSING_WIKI_PAGE_ID;
    }

    public WikiPageReferences getBreadCrumbs(final String wikiPageId) {
        try {
            return toRefs(parsedWikiPageRepository.getBreadCrumbs(wikiPageId), false, true);
        } catch (ContentNotFoundException e) {
            return fallback(wikiPageId);
        }
    }

    public WikiPageReferences getSubPages(final String wikiPageId) {
        try {
            return toRefs(parsedWikiPageRepository.getSubPages(wikiPageId), true, true);
        } catch (ContentNotFoundException e) {
            return fallback(wikiPageId);
        }
    }

    private WikiPageReferences fallback(final String wikiPageId) {
        return new WikiPageReferences();
    }

    private WikiPageReferences toRefs(final List<String> wikiPageIds, final boolean sort, final boolean firstLevel) {
        final WikiPageReferences result = new WikiPageReferences();

        result.setItems(wikiPageIds
                .stream()
                .filter(parsedWikiPageRepository::exists)
                .map(id -> {
                    final WikiPageReference ref = new WikiPageReference();
                    ref.setId(id);
                    try {
                        ref.setTitle(parsedWikiPageRepository.load(id).getTitle());
                    } catch (ContentNotFoundException e) {
                        ref.setTitle("N/A");
                    }
                    try {
                        ref.setChildren(toRefs(parsedWikiPageRepository.getSubPages(id), true, false));
                    } catch (ContentNotFoundException e) {
                        ref.setChildren(fallback(id));
                    }
                    return ref;
                })
                .collect(Collectors.toList()));

        if (sort) {
            result.setItems(result
                    .getItems()
                    .stream()
                    .sorted(Comparator.comparing(WikiPageReference::getTitle))
                    .collect(Collectors.toList())
            );

            if (firstLevel) {
                result.setItems(result
                        .getItems()
                        .stream()
                        .sorted(Comparator.comparing(WikiPageReference::getChildrenCount).reversed())
                        .collect(Collectors.toList())
                );
            }
        }

        return result;
    }

    public WikiPageReferences getLinksFromHere(final String wikiPageId) {
        return toRefs(
                Lists.newArrayList(parsedWikiPageRepository.getCurrentContext().getLinksFromPage(wikiPageId)),
                true,
                true
        );
    }

    public WikiPageReferences getLinksToHere(final String wikiPageId) {
        return toRefs(
                Lists.newArrayList(parsedWikiPageRepository.getCurrentContext().getLinksToPage(wikiPageId)),
                true,
                true
        );
    }

    public byte[] generateDotSvg(final String source) throws Exception {
        return wikiImageRepository.generateDotSvg(source);
    }

    public byte[] generatePlantUmlSvg(final String source) throws Exception {
        return wikiImageRepository.generatePlantUmlSvg(source);
    }

    public long getImageCacheSizeInBytes() {
        if (wikiImageRepository instanceof CachingWikiImageRepository) {
            return ((CachingWikiImageRepository) wikiImageRepository).getCacheSizeInBytes();
        }
        return 0L;
    }

    public long getImageCacheSizeInItems() {
        if (wikiImageRepository instanceof CachingWikiImageRepository) {
            return ((CachingWikiImageRepository) wikiImageRepository).getCacheSizeInItems();
        }
        return 0;
    }

    public ImmutableSet<String> getWikiPageIds() {
        return parsedWikiPageRepository.getWikiPageIds();
    }

    public WikiContext getCurrentContext() {
        return parsedWikiPageRepository.getCurrentContext();
    }
}
