package cz.voho.wiki.model;

public class WikiPageCommitGroup {
    private String filename;
    private String latestDate;
    private String latestCommitSha;
    private String latestCommitMessage;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getLatestDate() {
        return latestDate;
    }

    public void setLatestDate(String latestDate) {
        this.latestDate = latestDate;
    }

    public String getLatestCommitSha() {
        return latestCommitSha;
    }

    public void setLatestCommitSha(String latestCommitSha) {
        this.latestCommitSha = latestCommitSha;
    }

    public String getLatestCommitMessage() {
        return latestCommitMessage;
    }

    public void setLatestCommitMessage(String latestCommitMessage) {
        this.latestCommitMessage = latestCommitMessage;
    }
}
