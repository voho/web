package cz.voho.wiki.repository.page;

import com.google.common.collect.ImmutableSet;
import cz.voho.wiki.model.WikiPageSource;

import java.util.Arrays;
import java.util.List;

public interface WikiPageSourceRepository {
    List<String> INDEX_CHILDREN_SORT_ORDER = Arrays.asList("informatika", "jazyk", "matematika", "fyzika");

    String INDEX_PAGE_ID = "index";
    String MISSING_PAGE_ID = "404";

    ImmutableSet<String> getWikiPageIds();

    WikiPageSource getWikiPageSourceById(String wikiPageId);
}
