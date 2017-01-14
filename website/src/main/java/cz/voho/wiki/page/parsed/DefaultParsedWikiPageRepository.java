package cz.voho.wiki.page.parsed;

import cz.voho.wiki.model.ParsedWikiPage;
import cz.voho.wiki.model.WikiContext;
import cz.voho.wiki.page.source.WikiPageSourceRepository;
import cz.voho.wiki.parser.WikiParser;

public class DefaultParsedWikiPageRepository implements ParsedWikiPageRepository {
    private final WikiParser wikiParser;
    private final WikiPageSourceRepository wikiPageSourceRepository;
    private final WikiContext wikiContext;

    public DefaultParsedWikiPageRepository(WikiParser wikiParser, WikiPageSourceRepository wikiPageSourceRepository, WikiContext wikiContext) {
        this.wikiParser = wikiParser;
        this.wikiPageSourceRepository = wikiPageSourceRepository;
        this.wikiContext = wikiContext;
    }

    @Override
    public ParsedWikiPage load(final String wikiPageId) {
        return wikiParser.parse(wikiContext, wikiPageSourceRepository.getWikiPageSourceById(wikiPageId));
    }
}
