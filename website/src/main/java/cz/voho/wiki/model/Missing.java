package cz.voho.wiki.model;

public class Missing {
    private String sourceWikiPageId;
    private String missingWikiPageId;

    public String getSourceWikiPageId() {
        return sourceWikiPageId;
    }

    public void setSourceWikiPageId(String sourceWikiPageId) {
        this.sourceWikiPageId = sourceWikiPageId;
    }

    public String getMissingWikiPageId() {
        return missingWikiPageId;
    }

    public void setMissingWikiPageId(String missingWikiPageId) {
        this.missingWikiPageId = missingWikiPageId;
    }
}
