package cz.voho.wiki.model;

public class Toc extends TocItem {
    public Toc(String id, String title, int level) {
        super(id, title, level);
    }

    public boolean isTrivial() {
        return size() < 3;
    }
}
