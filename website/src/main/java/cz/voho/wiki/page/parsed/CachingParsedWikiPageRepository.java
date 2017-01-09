package cz.voho.wiki.page.parsed;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableSet;
import cz.voho.exception.ContentNotFoundException;
import cz.voho.wiki.model.ParsedWikiPage;
import cz.voho.wiki.model.WikiContext;
import cz.voho.wiki.model.WikiPageSource;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CachingParsedWikiPageRepository implements ParsedWikiPageRepository {
    private final ParsedWikiPageRepository delegate;
    private final LoadingCache<String, ParsedWikiPage> pageCache;
    private final LoadingCache<String, List<String>> subPagesCache;

    public CachingParsedWikiPageRepository(final ParsedWikiPageRepository delegate) {
        this.delegate = delegate;
        this.pageCache = CacheBuilder.newBuilder().build(new CacheLoader<String, ParsedWikiPage>() {
            @Override
            public ParsedWikiPage load(String wikiPageId) throws Exception {
                return delegate.load(wikiPageId);
            }
        });
        this.subPagesCache = CacheBuilder.newBuilder().build(new CacheLoader<String, List<String>>() {
            @Override
            public List<String> load(String wikiPageId) throws Exception {
                return delegate.getSubPages(wikiPageId);
            }
        });
        preload();
    }

    private void preload() {
        this.delegate.getWikiPageIds().forEach(this::preload);
    }

    private void preload(final String wikiPageId) {
        pageCache.refresh(wikiPageId);
        subPagesCache.refresh(wikiPageId);
    }

    @Override
    public ParsedWikiPage load(final String wikiPageId) {
        try {
            return pageCache.get(wikiPageId, () -> delegate.load(wikiPageId));
        } catch (Exception e) {
            throw new ContentNotFoundException("Error while loading the page cache for: " + wikiPageId, e);
        }
    }

    @Override
    public List<String> getSubPages(final String wikiPageId) {
        try {
            return subPagesCache.get(wikiPageId, () -> {
                return delegate.getSubPages(wikiPageId);
            });
        } catch (ExecutionException e) {
            throw new ContentNotFoundException("Error while loading the sub page cache for: " + wikiPageId, e);
        }
    }

    @Override
    public WikiContext getCurrentContext() {
        return delegate.getCurrentContext();
    }

    @Override
    public ImmutableSet<String> getWikiPageIds() {
        return delegate.getWikiPageIds();
    }

    @Override
    public WikiPageSource getWikiPageSourceById(final String wikiPageId) {
        return delegate.getWikiPageSourceById(wikiPageId);
    }
}
