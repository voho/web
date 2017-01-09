package cz.voho.wiki.model;

public class ParsedWikiPage {
    private WikiPageSource source;
    private String title;
    private String html;
    private boolean cover;

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

    @Override
    public String toString() {
        return String.format("Parsed wiki page %s (parent = %s)", source.getId(), source.getParentId());
    }
}
