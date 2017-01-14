package cz.voho.wiki.page.parsed;

import cz.voho.wiki.model.ParsedWikiPage;

public interface ParsedWikiPageRepository {
    ParsedWikiPage load(String wikiPageId);
}
