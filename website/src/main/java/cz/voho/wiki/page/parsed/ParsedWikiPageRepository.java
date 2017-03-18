package cz.voho.wiki.page.parsed;

import cz.voho.wiki.model.ParsedWikiPage;

import java.util.Set;

public interface ParsedWikiPageRepository {
    Set<String> getWikiPageIds();

    ParsedWikiPage getParsedWikiPageById(String wikiPageId);
}
