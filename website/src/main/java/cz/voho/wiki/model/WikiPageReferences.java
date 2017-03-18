package cz.voho.wiki.model;

import java.util.LinkedList;
import java.util.List;

public class WikiPageReferences {
    private List<WikiPageReference> items = new LinkedList<>();

    public List<WikiPageReference> getItems() {
        return items;
    }

    public void setItems(final List<WikiPageReference> items) {
        this.items = items;
    }
}
