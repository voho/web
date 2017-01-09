package cz.voho.wiki.page.parsed;

import cz.voho.wiki.model.ParsedWikiPage;
import cz.voho.wiki.model.WikiContext;
import cz.voho.wiki.page.source.WikiPageSourceRepository;

import java.util.List;

public interface ParsedWikiPageRepository extends WikiPageSourceRepository {
    ParsedWikiPage load(String wikiPageId);

    List<String> getSubPages(String wikiPageId);

    WikiContext getCurrentContext();
}
