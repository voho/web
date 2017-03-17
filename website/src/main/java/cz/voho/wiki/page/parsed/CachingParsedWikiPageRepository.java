package cz.voho.wiki.page.parsed;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import cz.voho.common.exception.ContentNotFoundException;
import cz.voho.wiki.model.ParsedWikiPage;

public class CachingParsedWikiPageRepository implements ParsedWikiPageRepository {
    private final ParsedWikiPageRepository delegate;
    private final LoadingCache<String, ParsedWikiPage> pageCache;

    public CachingParsedWikiPageRepository(final ParsedWikiPageRepository delegate) {
        this.delegate = delegate;
        this.pageCache = CacheBuilder.newBuilder().build(new CacheLoader<String, ParsedWikiPage>() {
            @Override
            public ParsedWikiPage load(String wikiPageId) throws Exception {
                return delegate.load(wikiPageId);
            }
        });
    }

    public void refresh(final String wikiPageId) {
        pageCache.refresh(wikiPageId);
    }

    @Override
    public ParsedWikiPage load(final String wikiPageId) {
        try {
            return pageCache.get(wikiPageId, () -> delegate.load(wikiPageId));
        } catch (Exception e) {
            throw new ContentNotFoundException("Error while loading the page cache for: " + wikiPageId, e);
        }
    }
}
