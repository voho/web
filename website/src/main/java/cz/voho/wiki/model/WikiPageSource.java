package cz.voho.wiki.model;

public class WikiPageSource {
    private String id;
    private String parentId;
    private String markdownSource;
    private String githubRawUrl;
    private String githubUrl;
    private String githubIssueUrl;
    private String githubHistoryUrl;

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

    public String getGithubIssueUrl() {
        return githubIssueUrl;
    }

    public void setGithubIssueUrl(String githubIssueUrl) {
        this.githubIssueUrl = githubIssueUrl;
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

    public String getMarkdownSource() {
        return markdownSource;
    }

    public void setMarkdownSource(final String markdownSource) {
        this.markdownSource = markdownSource;
    }

    public String getGithubHistoryUrl() {
        return githubHistoryUrl;
    }

    public void setGithubHistoryUrl(String githubHistoryUrl) {
        this.githubHistoryUrl = githubHistoryUrl;
    }
}
