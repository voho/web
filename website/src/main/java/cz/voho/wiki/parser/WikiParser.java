package cz.voho.wiki.parser;

import cz.voho.wiki.model.ParsedWikiPage;
import cz.voho.wiki.model.WikiContext;
import cz.voho.wiki.model.WikiPageSource;

public interface WikiParser {
    ParsedWikiPage parse(WikiContext context, WikiPageSource page);
}
