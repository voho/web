package cz.voho.wiki.repository.parsed;

import cz.voho.wiki.model.ParsedWikiPage;
import cz.voho.wiki.repository.source.WikiPageSourceRepository;
import cz.voho.wiki.parser.WikiParser;

import java.util.Set;

public class DefaultParsedWikiPageRepository implements ParsedWikiPageRepository {
    private final WikiParser wikiParser;
    private final WikiPageSourceRepository wikiPageSourceRepository;
    private final WikiParsingContext wikiParsingContext;

    public DefaultParsedWikiPageRepository(WikiParser wikiParser, WikiPageSourceRepository wikiPageSourceRepository, WikiParsingContext wikiParsingContext) {
        this.wikiParser = wikiParser;
        this.wikiPageSourceRepository = wikiPageSourceRepository;
        this.wikiParsingContext = wikiParsingContext;
    }

    @Override
    public Set<String> getWikiPageIds() {
        return wikiPageSourceRepository.getWikiPageIds();
    }

    @Override
    public ParsedWikiPage getParsedWikiPageById(final String wikiPageId) {
        return wikiParser.parse(wikiParsingContext, wikiPageSourceRepository.getWikiPageSourceById(wikiPageId));
    }
}
