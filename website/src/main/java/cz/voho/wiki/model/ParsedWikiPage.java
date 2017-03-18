package cz.voho.wiki.model;

import java.util.LinkedList;
import java.util.List;

public class ParsedWikiPage {
    private WikiPageSource source;
    private String title;
    private String html;
    private boolean cover;
    private boolean todo;
    private List<String> linkedPages = new LinkedList<>();
    private Toc toc;
    private List<Quote> quotes = new LinkedList<>();

    public boolean isCover() {
        return cover;
    }

    public void setCover(boolean cover) {
        this.cover = cover;
    }

    public WikiPageSource getSource() {
        return source;
    }

    public void setSource(final WikiPageSource source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(final String html) {
        this.html = html;
    }

    public List<String> getLinkedPages() {
        return linkedPages;
    }

    public void setLinkedPages(List<String> linkedPages) {
        this.linkedPages = linkedPages;
    }

    public Toc getToc() {
        return toc;
    }

    public void setToc(Toc toc) {
        this.toc = toc;
    }

    public boolean isTodo() {
        return todo;
    }

    public void setTodo(boolean todo) {
        this.todo = todo;
    }

    public List<Quote> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<Quote> quotes) {
        this.quotes = quotes;
    }

    @Override
    public String toString() {
        return String.format("Parsed wiki page %s (parent = %s)", source.getId(), source.getParentId());
    }

    public void addQuote(String quote, String author) {
        Quote q = new Quote();
        q.setAuthor(author);
        q.setText(quote);
        this.quotes.add(q);
    }

    public void addLinkTo(String targetId) {
        this.linkedPages.add(targetId);
    }
}
