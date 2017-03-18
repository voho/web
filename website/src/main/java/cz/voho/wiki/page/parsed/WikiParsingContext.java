package cz.voho.wiki.page.parsed;

import com.google.common.collect.*;
import cz.voho.wiki.model.*;
import cz.voho.wiki.page.source.WikiPageSourceRepository;

import java.util.*;
import java.util.stream.Collectors;

public class WikiParsingContext {
    private final SetMultimap<String, String> linksToPage;
    private final SetMultimap<String, String> linksFromPage;
    private final SetMultimap<String, String> quotesByAuthor;
    private final Set<String> todoPages;
    private final Set<String> allPages;
    private final Map<String, String> parentPage;
    private final Map<String, Toc> pageToc;
    private final Map<String, ParsedWikiPage> parsedWikiPages;

    public WikiParsingContext() {
        this.linksToPage = Multimaps.newSortedSetMultimap(Maps.newTreeMap(), TreeSet::new);
        this.linksFromPage = Multimaps.newSortedSetMultimap(Maps.newTreeMap(), TreeSet::new);
        this.quotesByAuthor = Multimaps.newSortedSetMultimap(Maps.newTreeMap(), TreeSet::new);
        this.todoPages = Sets.newTreeSet();
        this.allPages = Sets.newTreeSet();
        this.parentPage = Maps.newHashMap();
        this.pageToc = Maps.newHashMap();
        this.parsedWikiPages = Maps.newHashMap();
    }

    public ImmutableList<String> getBreadCrumbs(final String wikiPageId) {
        if (wikiPageId.equals(WikiPageSourceRepository.INDEX_PAGE_ID)) {
            return ImmutableList.of();
        }

        final List<String> result = Lists.newLinkedList();
        result.addAll(getParentPages(wikiPageId));
        return ImmutableList.copyOf(Lists.reverse(result));
    }

    public ImmutableList<String> getSubPages(final String wikiPageId) {
        return ImmutableList.copyOf(parentPage.entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(wikiPageId))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList()));
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

    public Toc getNonTrivialToc(final String wikiPageId) {
        Toc toc = pageToc.get(wikiPageId);
        if (toc.isTrivial()) {
            return null;
        }
        return toc;
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
                .sorted(Comparator.comparing(Quote::getAuthor))
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

    public void addLink(final String sourceWikiPageId, final String targetWikiPageId) {
        linksFromPage.put(sourceWikiPageId, targetWikiPageId);
        linksToPage.put(targetWikiPageId, sourceWikiPageId);
    }

    public void addParsedPage(final String wikiPageId, ParsedWikiPage parsedWikiPage) {
        this.allPages.add(wikiPageId);
        if (parsedWikiPage.getSource().getParentId() != null) {
            parentPage.put(parsedWikiPage.getSource().getId(), parsedWikiPage.getSource().getParentId());
        }
        parsedWikiPages.put(wikiPageId, parsedWikiPage);
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

    public void addToc(final String wikiPageId, Toc toc) {
        this.pageToc.put(wikiPageId, toc);
    }

    public ParsedWikiPage getPage(String wikiPageId) {
        return parsedWikiPages.get(wikiPageId);
    }
}
