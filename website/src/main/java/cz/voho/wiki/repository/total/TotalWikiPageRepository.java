package cz.voho.wiki.repository.total;

import cz.voho.wiki.repository.parsed.WikiParsingContext;

public interface TotalWikiPageRepository {
    WikiParsingContext createContext();
}
