package cz.voho.facade;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import cz.voho.common.exception.ContentNotFoundException;
import cz.voho.common.utility.Constants;
import cz.voho.common.utility.LambdaClient;
import cz.voho.wiki.image.CachingWikiImageRepository;
import cz.voho.wiki.image.LambdaWikiImageRepository;
import cz.voho.wiki.image.WikiImageRepository;
import cz.voho.wiki.model.*;
import cz.voho.wiki.page.parsed.CachingParsedWikiPageRepository;
import cz.voho.wiki.page.parsed.DefaultParsedWikiPageRepository;
import cz.voho.wiki.page.parsed.ParsedWikiPageRepository;
import cz.voho.wiki.page.source.DefaultWikiPageSourceRepository;
import cz.voho.wiki.page.source.WikiPageSourceRepository;
import cz.voho.wiki.parser.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class WikiBackend {
    private static final Logger LOG = LoggerFactory.getLogger(WikiBackend.class);
    private static final String MISSING_WIKI_PAGE_ID = "404";

    private final WikiContext wikiContext;
    private final ParsedWikiPageRepository parsedWikiPageRepository;
    private final WikiImageRepository wikiImageRepository;

    WikiBackend() {
        this.wikiContext = new WikiContext();
        final CachingWikiImageRepository cachingWikiImageRepository = new CachingWikiImageRepository(new LambdaWikiImageRepository(new LambdaClient()));
        this.wikiImageRepository = cachingWikiImageRepository;

        final FlexmarkWikiParser wikiParser = new FlexmarkWikiParser(
                new CodePreprocessor(cachingWikiImageRepository),
                new QuotePreprocessor(),
                new MathPreprocessor(),
                new WikiLinkPreprocessor(),
                new TodoPreprocessor(),
                new ImagePreprocessor(),
                new JavadocPreprocessor(),
                new TocPreprocessor()
        );

        final WikiPageSourceRepository wikiPageSourceRepository = new DefaultWikiPageSourceRepository(wikiContext);
        final DefaultParsedWikiPageRepository parsedWikiPageRepository = new DefaultParsedWikiPageRepository(wikiParser, wikiPageSourceRepository, wikiContext);

        final CachingParsedWikiPageRepository cachedRepository = new CachingParsedWikiPageRepository(parsedWikiPageRepository);
        this.parsedWikiPageRepository = cachedRepository;
        wikiContext.getWikiPageIds().forEach(cachedRepository::refresh);
    }

    public String getExternalWikiPageLink(final String wikiPageId) {
        return Constants.WEBSITE_URL_WITH_SLASH + "wiki/" + wikiPageId + "/";
    }

    public ParsedWikiPage load(final String wikiPageId) {
        try {
            return parsedWikiPageRepository.load(wikiPageId);
        } catch (ContentNotFoundException e) {
            if (wikiPageId.equals(MISSING_WIKI_PAGE_ID)) {
                throw e;
            } else {
                return load(MISSING_WIKI_PAGE_ID);
            }
        }
    }

    public WikiPageReferences getBreadCrumbs(final String wikiPageId) {
        try {
            return toRefs(wikiContext.getBreadCrumbs(wikiPageId), false, true);
        } catch (ContentNotFoundException e) {
            return fallback(wikiPageId);
        }
    }

    public WikiPageReferences getSubPages(final String wikiPageId) {
        try {
            return toRefs(wikiContext.getSubPages(wikiPageId), true, true);
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
                .filter(wikiContext::exists)
                .map(id -> {
                    final WikiPageReference ref = new WikiPageReference();
                    ref.setId(id);
                    try {
                        ref.setTitle(parsedWikiPageRepository.load(id).getTitle());
                    } catch (ContentNotFoundException e) {
                        ref.setTitle("N/A");
                    }
                    try {
                        ref.setChildren(toRefs(wikiContext.getSubPages(id), true, false));
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
                Lists.newArrayList(wikiContext.getLinksFromPage(wikiPageId)),
                true,
                true
        );
    }

    public WikiPageReferences getLinksToHere(final String wikiPageId) {
        return toRefs(
                Lists.newArrayList(wikiContext.getLinksToPage(wikiPageId)),
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
        return wikiContext.getWikiPageIds();
    }

    public WikiContext getCurrentContext() {
        return wikiContext;
    }

    public ImmutableSet<String> getWikiPageAutoCompleteValues() {
        final Set<String> values = new TreeSet<>();
        values.addAll(wikiContext.getWikiPageIds());
        values.addAll(wikiContext.getWikiPageIds().stream().map(parsedWikiPageRepository::load).map(ParsedWikiPage::getTitle).collect(Collectors.toSet()));
        return ImmutableSet.copyOf(values);
    }

    public String resolveWikiPageId(final String path) {
        String result = path;

        // multi-part wiki page

        final String[] parts = result.split(Pattern.quote("/"));

        if (parts.length > 1) {
            result = parts[parts.length - 1];
        }

        if (!getCurrentContext().exists(result)) {
            final Set<String> candidates = Sets.newHashSet();

            for (final String wikiPageId : wikiContext.getWikiPageIds()) {
                final boolean containsAsPrefix = wikiPageId.contains(result + "-");
                final boolean containsAsSuffix = wikiPageId.contains("-" + result);

                if (containsAsPrefix || containsAsSuffix) {
                    candidates.add(wikiPageId);
                }
            }

            if (candidates.size() == 1) {
                result = candidates.iterator().next();
            }
        }

        return result;
    }

    public List<WikiPageCommit> enrichCommits(List<WikiPageCommitGroup> recentWikiChanges) {
        return recentWikiChanges
                .stream()
                .map(c -> {
                    List<WikiPageCommit> results = new LinkedList<WikiPageCommit>();
                    LocalDateTime date = LocalDateTime.parse(c.getLatestDate(), DateTimeFormatter.ISO_DATE_TIME);
                    String formattedDate = date.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(new Locale("cs", "CZ")));
                    String shaLinkUrl = String.format("https://github.com/voho/web/commit/%s", c.getLatestCommitSha());
                    String id = c.getFilename();

                    if (id.startsWith("website/src/main/resources/")) {
                        id = id.substring("website/src/main/resources/".length());
                    }

                    if (id.endsWith(".md")) {
                        id = id.substring(0, id.length() - ".md".length());
                    }

                    id = resolveWikiPageId(id);

                    WikiPageCommit result = new WikiPageCommit();
                    String title = load(id).getTitle();
                    result.setMessage(c.getLatestCommitMessage());
                    result.setFormattedDate(formattedDate);
                    result.setId(id);
                    result.setTitle(title);
                    result.setUrl(shaLinkUrl);
                    results.add(result);

                    return results;
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
