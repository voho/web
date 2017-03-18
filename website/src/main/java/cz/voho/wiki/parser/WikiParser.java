package cz.voho.wiki.parser;

import cz.voho.wiki.model.ParsedWikiPage;
import cz.voho.wiki.page.parsed.WikiParsingContext;
import cz.voho.wiki.model.WikiPageSource;

public interface WikiParser {
    ParsedWikiPage parse(WikiParsingContext context, WikiPageSource page);
}
