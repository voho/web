package cz.voho.wiki.page.parsed;

import com.google.common.collect.ImmutableSet;
import cz.voho.wiki.model.ParsedWikiPage;
import cz.voho.wiki.model.WikiContext;
import cz.voho.wiki.model.WikiPageSource;
import cz.voho.wiki.page.source.WikiPageSourceRepository;
import cz.voho.wiki.parser.WikiParser;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultParsedWikiPageRepository implements ParsedWikiPageRepository {
    private final WikiParser wikiParser;
    private final WikiPageSourceRepository wikiPageSourceRepository;
    private final WikiContext wikiContext;

    public DefaultParsedWikiPageRepository(final WikiParser wikiParser, final WikiPageSourceRepository wikiPageSourceRepository) {
        this.wikiParser = wikiParser;
        this.wikiPageSourceRepository = wikiPageSourceRepository;
        this.wikiContext = new WikiContext();
        getWikiPageIds().forEach(wikiContext::addPage);
    }

    @Override
    public ParsedWikiPage load(final String wikiPageId) {
        return wikiParser.parse(wikiContext, wikiPageSourceRepository.getWikiPageSourceById(wikiPageId));
    }

    @Override
    @Deprecated // TODO move to WikiContext
    public List<String> getSubPages(final String wikiPageId) {
        return wikiPageSourceRepository
                .getWikiPageIds()
                .stream()
                .map(wikiPageSourceRepository::getWikiPageSourceById)
                .filter(tempPageSource -> isParentOf(wikiPageId, tempPageSource.getParentId()))
                .map(WikiPageSource::getId)
                .collect(Collectors.toList());
    }

    @Override
    public WikiContext getCurrentContext() {
        return wikiContext;
    }

    private boolean isParentOf(final String maybeParentId, final String maybeChildId) {
        return maybeParentId.equals(maybeChildId);
    }

    @Override
    @Deprecated // TODO move to WikiContext
    public ImmutableSet<String> getWikiPageIds() {
        return wikiPageSourceRepository.getWikiPageIds();
    }

    @Override
    public WikiPageSource getWikiPageSourceById(final String wikiPageId) {
        return wikiPageSourceRepository.getWikiPageSourceById(wikiPageId);
    }
}
