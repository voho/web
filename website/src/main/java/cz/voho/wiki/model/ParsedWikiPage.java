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

    public boolean isCover() {
        return cover;
    }

    public void markAsCover() {
        this.cover = true;
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

    public Toc getToc() {
        return toc;
    }

    public void setToc(final Toc toc) {
        this.toc = toc;
    }

    public boolean isTodo() {
        return todo;
    }

    public void markAsTodo() {
        this.todo = true;
    }

    public void addLinkTo(final String targetId) {
        this.linkedPages.add(targetId);
    }
}
