package cz.voho.wiki.page.source;

import cz.voho.wiki.model.WikiPageSource;

public interface WikiPageSourceRepository {
    String INDEX_PAGE_ID = "index";

    WikiPageSource getWikiPageSourceById(String wikiPageId);
}
