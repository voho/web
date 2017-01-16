package cz.voho.wiki.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import cz.voho.wiki.page.source.WikiPageSourceRepository;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class WikiContext {
    private final SetMultimap<String, String> linksToPage;
    private final SetMultimap<String, String> linksFromPage;
    private final SetMultimap<String, String> quotesByAuthor;
    private final Set<String> todoPages;
    private final Set<String> allPages;
    private final Map<String, String> parentPage;

    public WikiContext() {
        this.linksToPage = Multimaps.newSortedSetMultimap(Maps.newTreeMap(), TreeSet::new);
        this.linksFromPage = Multimaps.newSortedSetMultimap(Maps.newTreeMap(), TreeSet::new);
        this.quotesByAuthor = Multimaps.newSortedSetMultimap(Maps.newTreeMap(), TreeSet::new);
        this.todoPages = Sets.newTreeSet();
        this.allPages = Sets.newTreeSet();
        this.parentPage = Maps.newHashMap();
    }

    public ImmutableList<String> getBreadCrumbs(final String wikiPageId) {
        if (wikiPageId.equals(WikiPageSourceRepository.INDEX_PAGE_ID)) {
            return ImmutableList.of();
        }

        final List<String> result = Lists.newLinkedList();
        result.addAll(getParentPages(wikiPageId));
        return ImmutableList.copyOf(Lists.reverse(result));
    }

    public List<String> getSubPages(final String wikiPageId) {
        return parentPage.entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(wikiPageId))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public ImmutableList<String> getParentPages(final String wikiPageId) {
        final List<String> parents = Lists.newLinkedList();

        String tempId = wikiPageId;

        while (tempId != null) {
            parents.add(tempId);
            tempId = parentPage.get(tempId);
        }

        // remove the current page (which is first)
        parents.remove(0);

        return ImmutableList.copyOf(parents);
    }

    public ImmutableSet<String> getWikiPageIds() {
        return ImmutableSet.copyOf(allPages);
    }

    public void addPage(final String wikiPageId) {
        this.allPages.add(wikiPageId);
    }

    public void setParentPage(final String childPageId, final String parentPageId) {
        parentPage.put(childPageId, parentPageId);
    }

    public void addLink(final String sourceWikiPageId, final String targetWikiPageId) {
        linksFromPage.put(sourceWikiPageId, targetWikiPageId);
        linksToPage.put(targetWikiPageId, sourceWikiPageId);
    }

    public ImmutableSet<String> getLinksFromPage(final String wikiPageId) {
        return nullToEmpty(linksFromPage.get(wikiPageId));
    }

    public ImmutableSet<String> getLinksToPage(final String wikiPageId) {
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

    private ImmutableSet<String> nullToEmpty(final Set<String> strings) {
        if (strings == null) {
            return ImmutableSet.of();
        }

        return ImmutableSet.copyOf(strings);
    }

    public void addTodo(final String id) {
        todoPages.add(id);
    }

    public void addQuote(final String quote, final String author) {
        quotesByAuthor.put(author, quote);
    }

    public boolean exists(final String wikiPageId) {
        return allPages.contains(wikiPageId);
    }
}
