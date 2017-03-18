package cz.voho.wiki.repository.page;

import cz.voho.wiki.model.ParsedWikiPage;
import cz.voho.wiki.parser.WikiParser;

public class DefaultParsedWikiPageRepository implements ParsedWikiPageRepository {
    private final WikiParser wikiParser;
    private final WikiPageSourceRepository wikiPageSourceRepository;

    public DefaultParsedWikiPageRepository(WikiPageSourceRepository wikiPageSourceRepository, WikiParser wikiParser) {
        this.wikiParser = wikiParser;
        this.wikiPageSourceRepository = wikiPageSourceRepository;
    }

    @Override
    public ParsedWikiPage getParsedWikiPageById(final String wikiPageId) {
        return wikiParser.parse(wikiPageSourceRepository.getWikiPageSourceById(wikiPageId));
    }
}
