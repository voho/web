package cz.voho.wiki.page.source;

import com.google.common.collect.ImmutableSet;
import cz.voho.wiki.model.WikiPageSource;

public interface WikiPageSourceRepository {
    String INDEX_PAGE_ID = "index";
    String MISSING_PAGE_ID = "404";

    ImmutableSet<String> getWikiPageIds();

    WikiPageSource getWikiPageSourceById(String wikiPageId);
}
