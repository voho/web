package cz.voho.wiki.model;

public class WikiPageReference {
    private String id;
    private String title;
    private WikiPageReferences children;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", id, title);
    }

    public WikiPageReferences getChildren() {
        return children;
    }

    public void setChildren(WikiPageReferences children) {
        this.children = children;
    }

    public int getChildrenCount() {
        if (children != null && children.getItems() != null) {
            return children.getItems().size();
        }

        return 0;
    }
}
