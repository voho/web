package cz.voho.wiki.repository.page;

import cz.voho.wiki.model.ParsedWikiPages;

public class DefaultTopLevelWikiPageRepository implements TopLevelWikiPageRepository {
    private final WikiPageSourceRepository wikiPageSourceRepository;
    private final ParsedWikiPageRepository parsedWikiPageRepository;

    public DefaultTopLevelWikiPageRepository(WikiPageSourceRepository wikiPageSourceRepository, ParsedWikiPageRepository parsedWikiPageRepository) {
        this.wikiPageSourceRepository = wikiPageSourceRepository;
        this.parsedWikiPageRepository = parsedWikiPageRepository;
    }

    @Override
    public ParsedWikiPages getParsedWikiPages() {
        ParsedWikiPages parsedWikiPages = new ParsedWikiPages();

        wikiPageSourceRepository
                .getWikiPageIds()
                .forEach(wikiPageId -> parsedWikiPages.addPage(parsedWikiPageRepository.getParsedWikiPageById(wikiPageId)));

        return parsedWikiPages;
    }
}
