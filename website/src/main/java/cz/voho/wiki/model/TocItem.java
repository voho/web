package cz.voho.wiki.model;

import com.google.common.collect.Lists;

import java.util.List;

public class TocItem {
    private String id;
    private String title;
    private int level;
    private List<TocItem> children = Lists.newArrayList();

    public TocItem(String id, String title, int level) {
        this.id = id;
        this.title = title;
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void addChildItem(TocItem item) {
        children.add(item);
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<TocItem> getChildren() {
        return children;
    }

    public int size() {
        return 1 + children.stream().mapToInt(TocItem::size).sum();
    }
}
