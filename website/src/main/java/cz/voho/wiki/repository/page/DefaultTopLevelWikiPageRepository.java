package cz.voho.wiki.repository.page;

import cz.voho.wiki.model.ParsedWikiPages;

import java.util.stream.Collectors;

public class DefaultTopLevelWikiPageRepository implements TopLevelWikiPageRepository {
    private final WikiPageSourceRepository wikiPageSourceRepository;
    private final ParsedWikiPageRepository parsedWikiPageRepository;
    private final ParsedWikiPages parsedWikiPages;

    public DefaultTopLevelWikiPageRepository(WikiPageSourceRepository wikiPageSourceRepository, ParsedWikiPageRepository parsedWikiPageRepository) {
        this.wikiPageSourceRepository = wikiPageSourceRepository;
        this.parsedWikiPageRepository = parsedWikiPageRepository;
        this.parsedWikiPages = new ParsedWikiPages(wikiPageSourceRepository
                .getWikiPageIds()
                .stream()
                .map(parsedWikiPageRepository::getParsedWikiPageById)
                .collect(Collectors.toList()));
    }

    @Override
    public ParsedWikiPages getParsedWikiPages() {
        return parsedWikiPages;
    }
}
