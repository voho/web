package cz.voho.wiki.model;

import java.time.LocalDateTime;

public class WikiPageSource {
    private String id;
    private String parentId;
    private String source;
    private String githubRawUrl;
    private String githubUrl;
    private String origin;
    private LocalDateTime updated;

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }

    public String getGithubRawUrl() {
        return githubRawUrl;
    }

    public void setGithubRawUrl(String githubRawUrl) {
        this.githubRawUrl = githubRawUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(final String parentId) {
        this.parentId = parentId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(final String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return String.format("Source for wiki page %s (parent = %s)", id, parentId);
    }
}
