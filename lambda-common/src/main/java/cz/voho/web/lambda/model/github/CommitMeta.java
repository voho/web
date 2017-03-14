package cz.voho.web.lambda.model.github;

import java.util.List;

public class CommitMeta {
    // read. LocalDateTime.parse(isoTime, DateTimeFormatter.ISO_DATE_TIME);
    private String isoTime;
    private String sha;
    private String message;
    private List<String> filenames;

    public List<String> getFilenames() {
        return filenames;
    }

    public void setFilenames(List<String> filenames) {
        this.filenames = filenames;
    }

    public String getIsoTime() {
        return isoTime;
    }

    public void setIsoTime(String isoTime) {
        this.isoTime = isoTime;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
