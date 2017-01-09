package cz.voho.wiki.page.source;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import cz.voho.wiki.model.WikiPageSource;

import java.util.List;

public interface WikiPageSourceRepository {
    String INDEX_PAGE_ID = "index";

    ImmutableSet<String> getWikiPageIds();

    default ImmutableList<String> getBreadCrumbs(final String wikiPageId) {
        if (wikiPageId.equals(INDEX_PAGE_ID)) {
            return ImmutableList.of();
        }

        final List<String> result = Lists.newLinkedList();
        result.addAll(getParentPages(wikiPageId));
        return ImmutableList.copyOf(Lists.reverse(result));
    }

    default ImmutableList<String> getParentPages(final String wikiPageId) {
        final List<String> parents = Lists.newLinkedList();

        String tempId = wikiPageId;

        while (tempId != null) {
            parents.add(tempId);
            tempId = getWikiPageSourceById(tempId).getParentId();
        }

        // remove the current page (which is first)
        parents.remove(0);

        return ImmutableList.copyOf(parents);
    }

    WikiPageSource getWikiPageSourceById(String wikiPageId);
}
