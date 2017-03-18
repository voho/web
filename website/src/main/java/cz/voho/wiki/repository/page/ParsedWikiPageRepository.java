package cz.voho.wiki.repository.page;

import cz.voho.wiki.model.ParsedWikiPage;

public interface ParsedWikiPageRepository {
    ParsedWikiPage getParsedWikiPageById(String wikiPageId);


}
