package cz.voho.facade;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import cz.voho.common.exception.ContentNotFoundException;
import cz.voho.common.utility.Constants;
import cz.voho.common.utility.WikiLinkUtility;
import cz.voho.wiki.model.*;
import cz.voho.wiki.repository.image.CachingWikiImageRepository;
import cz.voho.wiki.repository.image.WikiImageRepository;
import cz.voho.wiki.repository.page.ParsedWikiPageRepository;
import cz.voho.wiki.repository.page.WikiPageSourceRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static cz.voho.wiki.repository.page.WikiPageSourceRepository.MISSING_PAGE_ID;

// TODO improve code
public class WikiBackend {
    private final WikiImageRepository wikiImageRepository;
    private final ParsedWikiPages parsedWikiPages;

    WikiBackend(final WikiPageSourceRepository wikiPageSourceRepository, final ParsedWikiPageRepository parsedWikiPageRepository, final WikiImageRepository wikiImageRepository) {
        this.wikiImageRepository = wikiImageRepository;

        this.parsedWikiPages = new ParsedWikiPages(wikiPageSourceRepository
                .getWikiPageIds()
                .stream()
                .map(parsedWikiPageRepository::getParsedWikiPageById)
                .collect(Collectors.toList()));
    }

    public String getExternalWikiPageLink(final String wikiPageId) {
        return Constants.WEBSITE_URL_WITH_SLASH + "wiki/" + wikiPageId + "/";
    }

    public ParsedWikiPage load(final String wikiPageId) {
        final ParsedWikiPage parsedWikiPage = parsedWikiPages.getPage(wikiPageId);

        if (parsedWikiPage != null) {
            return parsedWikiPage;
        }

        if (wikiPageId.equals(MISSING_PAGE_ID)) {
            throw new ContentNotFoundException("No error page found: " + wikiPageId);
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
        return parsedWikiPages.getWikiPageIds();
    }

    public ParsedWikiPages getCurrentContext() {
        return parsedWikiPages;
    }

    public List<WikiPageCommit> enrichCommits(final List<WikiPageCommitGroup> recentWikiChanges) {
        return recentWikiChanges
                .stream()
                .map(c -> {
                    List<WikiPageCommit> results = new LinkedList<WikiPageCommit>();
                    LocalDateTime date = LocalDateTime.parse(c.getLatestDate(), DateTimeFormatter.ISO_DATE_TIME);
                    String formattedDate = date.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(Constants.CZECH_LOCALE));
                    String shaLinkUrl = String.format("https://github.com/voho/web/commit/%s", c.getLatestCommitSha());
                    String id = WikiLinkUtility.resolveWikiPageId(c.getFilename());

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
