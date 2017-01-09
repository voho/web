package cz.voho.wiki.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class WikiContext {
    private final SetMultimap<String, String> linksToPage;
    private final SetMultimap<String, String> linksFromPage;
    private final SetMultimap<String, String> quotesByAuthor;
    private final Set<String> todoPages;
    private final Set<String> allPages;

    public WikiContext() {
        this.linksToPage = Multimaps.newSortedSetMultimap(Maps.newTreeMap(), TreeSet::new);
        this.linksFromPage = Multimaps.newSortedSetMultimap(Maps.newTreeMap(), TreeSet::new);
        this.quotesByAuthor = Multimaps.newSortedSetMultimap(Maps.newTreeMap(), TreeSet::new);
        this.todoPages = Sets.newTreeSet();
        this.allPages = Sets.newTreeSet();
    }

    public void addPage(final String wikiPageId) {
        this.allPages.add(wikiPageId);
    }

    public void addLink(final String sourceWikiPageId, final String targetWikiPageId) {
        if (sourceWikiPageId == null || targetWikiPageId == null) {
            // safety for tests TODO
            return;
        }
        linksFromPage.put(sourceWikiPageId, targetWikiPageId);
        linksToPage.put(targetWikiPageId, sourceWikiPageId);
    }

    public Set<String> getLinksFromPage(final String wikiPageId) {
        return nullToEmpty(linksFromPage.get(wikiPageId));
    }

    public Set<String> getLinksToPage(final String wikiPageId) {
        return nullToEmpty(linksToPage.get(wikiPageId));
    }

    public List<Missing> getMissingPages() {
        final List<Missing> result = Lists.newLinkedList();

        linksFromPage.entries().forEach(e -> {
            if (!allPages.contains(e.getValue())) {
                final Missing missing = new Missing();
                missing.setSourceWikiPageId(e.getKey());
                missing.setMissingWikiPageId(e.getValue());
                result.add(missing);
            }
        });

        return result;
    }

    public Set<Quote> getQuotes() {
        return quotesByAuthor
                .entries()
                .stream()
                .map(a -> {
                    Quote q = new Quote();
                    q.setAuthor(a.getKey());
                    q.setText(a.getValue());
                    return q;
                })
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public Set<Todo> getTodoPages() {
        return todoPages
                .stream()
                .map(a -> {
                    Todo t = new Todo();
                    t.setWikiPageId(a);
                    return t;
                })
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private Set<String> nullToEmpty(final Set<String> strings) {
        if (strings == null) {
            return Collections.emptySet();
        }

        return strings;
    }

    public void addTodo(final String id) {
        todoPages.add(id);
    }

    public void addQuote(final String quote, final String author) {
        quotesByAuthor.put(author, quote);
    }
}
