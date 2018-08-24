package cz.voho.wiki.repository.page;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import cz.voho.wiki.model.Missing;
import cz.voho.wiki.model.ParsedWikiPage;
import cz.voho.wiki.model.Toc;
import cz.voho.wiki.model.Todo;

import java.util.List;
import java.util.Set;

public interface ParsedWikiPageRepository {
    boolean exists(String wikiPageId);

    ParsedWikiPage getParsedWikiPageById(String wikiPageId);

    ImmutableList<String> getBreadCrumbs(String wikiPageId);

    ImmutableList<String> getSubPages(String wikiPageId);

    ImmutableSet<String> getWikiPageIds();

    Toc getNonTrivialToc(String wikiPageId);

    ImmutableSet<String> getLinksFromPage(String wikiPageId);

    ImmutableSet<String> getLinksToPage(String wikiPageId);

    List<Missing> getMissingPages();

    Set<Todo> getTodoPages();
}
