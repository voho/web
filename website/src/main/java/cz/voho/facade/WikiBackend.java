package cz.voho.facade;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import cz.voho.common.exception.ContentNotFoundException;
import cz.voho.common.utility.WebsiteConstants;
import cz.voho.wiki.model.Missing;
import cz.voho.wiki.model.ParsedWikiPage;
import cz.voho.wiki.model.Toc;
import cz.voho.wiki.model.Todo;
import cz.voho.wiki.model.WikiPageReference;
import cz.voho.wiki.model.WikiPageReferences;
import cz.voho.wiki.repository.image.CachingWikiImageRepository;
import cz.voho.wiki.repository.image.WikiImageRepository;
import cz.voho.wiki.repository.page.ParsedWikiPageRepository;
import cz.voho.wiki.repository.page.WikiPageSourceRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class WikiBackend {
    private final WikiImageRepository wikiImageRepository;
    private final ParsedWikiPageRepository parsedWikiPages;

    WikiBackend(final WikiPageSourceRepository wikiPageSourceRepository,
                final ParsedWikiPageRepository parsedWikiPageRepository,
                final WikiImageRepository wikiImageRepository
    ) {
        this.wikiImageRepository = wikiImageRepository;

        final List<ParsedWikiPage> parsedWikiPages = wikiPageSourceRepository
                .getWikiPageIds()
                .stream()
                .map(parsedWikiPageRepository::getParsedWikiPageById)
                .collect(Collectors.toList());

        this.parsedWikiPages = parsedWikiPageRepository;
    }

    public String getExternalWikiPageLink(final String wikiPageId) {
        return WebsiteConstants.WEBSITE_URL_WITH_SLASH + "wiki/" + wikiPageId + "/";
    }

    public ParsedWikiPage load(final String wikiPageId) {
        return parsedWikiPages.getParsedWikiPageById(wikiPageId);
    }

    private WikiPageReference toRef(final String wikiPageId) {
        final ParsedWikiPage page = parsedWikiPages.getParsedWikiPageById(wikiPageId);

        final WikiPageReference result = new WikiPageReference();
        result.setId(wikiPageId);
        result.setTitle(page.getTitle());
        result.setChildren(getSubPages(wikiPageId));
        return result;
    }

    public WikiPageReferences getBreadCrumbs(final String wikiPageId) {
        try {
            return toRefs(parsedWikiPages.getBreadCrumbs(wikiPageId), false, true);
        } catch (final ContentNotFoundException e) {
            return fallback(wikiPageId);
        }
    }

    public WikiPageReferences getSubPages(final String wikiPageId) {
        try {
            return toRefs(parsedWikiPages.getSubPages(wikiPageId), true, true);
        } catch (final ContentNotFoundException e) {
            return fallback(wikiPageId);
        }
    }

    public WikiPageReferences getWikiIndexSubPages() {
        final WikiPageReferences result = getSubPages(WikiPageSourceRepository.INDEX_PAGE_ID);

        result.setItems(result
                .getItems()
                .stream()
                .sorted(Comparator.comparingInt(a -> WikiPageSourceRepository.INDEX_CHILDREN_SORT_ORDER.indexOf(a.getId().toLowerCase(Locale.ROOT))))
                .collect(Collectors.toList())
        );

        return result;
    }

    private WikiPageReferences fallback(final String wikiPageId) {
        return new WikiPageReferences();
    }

    private WikiPageReferences toRefs(final List<String> wikiPageIds, final boolean sort, final boolean firstLevel) {
        final WikiPageReferences result = new WikiPageReferences();

        result.setItems(wikiPageIds
                .stream()
                .filter(parsedWikiPages::exists)
                .map(this::toRef)
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
                Lists.newArrayList(parsedWikiPages.getLinksFromPage(wikiPageId)),
                true,
                true
        );
    }

    public WikiPageReferences getLinksToHere(final String wikiPageId) {
        return toRefs(
                Lists.newArrayList(parsedWikiPages.getLinksToPage(wikiPageId)),
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

    public Set<Todo> getTodoPages() {
        return parsedWikiPages.getTodoPages();
    }

    public List<Missing> getMissingPages() {
        return parsedWikiPages.getMissingPages();
    }

    public Toc getNonTrivialToc(final String wikiPageId) {
        return parsedWikiPages.getNonTrivialToc(wikiPageId);
    }

    public boolean exists(final String wikiPageId) {
        return parsedWikiPages.exists(wikiPageId);
    }
}
