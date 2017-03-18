package cz.voho.facade;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import cz.voho.common.exception.ContentNotFoundException;
import cz.voho.common.utility.Constants;
import cz.voho.common.utility.LambdaClient;
import cz.voho.common.utility.WikiLinkUtility;
import cz.voho.wiki.image.CachingWikiImageRepository;
import cz.voho.wiki.image.LambdaWikiImageRepository;
import cz.voho.wiki.image.WikiImageRepository;
import cz.voho.wiki.model.*;
import cz.voho.wiki.page.parsed.WikiParsingContext;
import cz.voho.wiki.page.total.DefaultTotalWikiPageRepository;
import cz.voho.wiki.page.total.TotalWikiPageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.stream.Collectors;

import static cz.voho.wiki.page.source.WikiPageSourceRepository.MISSING_PAGE_ID;

public class WikiBackend {
    private static final Logger LOG = LoggerFactory.getLogger(WikiBackend.class);

    private final TotalWikiPageRepository totalWikiPageRepository;
    private WikiParsingContext wikiParsingContext;
    private WikiImageRepository wikiImageRepository;

    WikiBackend() {
        totalWikiPageRepository = new DefaultTotalWikiPageRepository();
        wikiParsingContext = totalWikiPageRepository.createContext();
        wikiImageRepository = new CachingWikiImageRepository(new LambdaWikiImageRepository(new LambdaClient()));
    }

    public String getExternalWikiPageLink(final String wikiPageId) {
        return Constants.WEBSITE_URL_WITH_SLASH + "wiki/" + wikiPageId + "/";
    }

    public ParsedWikiPage load(final String wikiPageId) {
        ParsedWikiPage parsedWikiPage = wikiParsingContext.getPage(wikiPageId);

        if (parsedWikiPage != null) {
            return parsedWikiPage;
        }

        if (wikiPageId.equals(MISSING_PAGE_ID)) {
            throw new ContentNotFoundException("No error page found.");
        } else {
            return load(MISSING_PAGE_ID);
        }
    }

    public WikiPageReferences getBreadCrumbs(final String wikiPageId) {
        try {
            return toRefs(getCurrentContext().getBreadCrumbs(wikiPageId), false, true);
        } catch (ContentNotFoundException e) {
            return fallback(wikiPageId);
        }
    }

    public WikiPageReferences getSubPages(final String wikiPageId) {
        try {
            return toRefs(getCurrentContext().getSubPages(wikiPageId), true, true);
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
                .filter(a -> getCurrentContext().exists(a))
                .map(id -> {
                    final WikiPageReference ref = new WikiPageReference();
                    ref.setId(id);
                    try {
                        ref.setTitle(getCurrentContext().getPage(id).getTitle());// TODO bullshit
                    } catch (ContentNotFoundException e) {
                        ref.setTitle("N/A");
                    }
                    try {
                        ref.setChildren(toRefs(getCurrentContext().getSubPages(id), true, false));
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
                Lists.newArrayList(getCurrentContext().getLinksFromPage(wikiPageId)),
                true,
                true
        );
    }

    public WikiPageReferences getLinksToHere(final String wikiPageId) {
        return toRefs(
                Lists.newArrayList(getCurrentContext().getLinksToPage(wikiPageId)),
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
        return wikiParsingContext.getWikiPageIds();
    }

    public WikiParsingContext getCurrentContext() {
        return wikiParsingContext;
    }

    public String resolveWikiPageId(final String uri) {
        String result = WikiLinkUtility.lastWikiPart(WikiLinkUtility.splitWikiParts(WikiLinkUtility.stripWikiResourcePrefixSuffix(uri)));

        if (!getCurrentContext().exists(result)) {
            final Set<String> candidates = Sets.newHashSet();

            for (final String wikiPageId : getWikiPageIds()) {
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
                    String id = resolveWikiPageId(c.getFilename());

                    ParsedWikiPage parsedWikiPage = load(id);

                    if (parsedWikiPage != null) {
                        WikiPageCommit result = new WikiPageCommit();
                        String title = parsedWikiPage.getTitle();
                        result.setMessage(c.getLatestCommitMessage());
                        result.setFormattedDate(formattedDate);
                        result.setId(id);
                        result.setTitle(title);
                        result.setUrl(shaLinkUrl);
                        results.add(result);
                    }

                    return results;
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
