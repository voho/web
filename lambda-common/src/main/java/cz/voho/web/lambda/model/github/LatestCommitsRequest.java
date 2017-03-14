package cz.voho.web.lambda.model.github;

public class LatestCommitsRequest {
    // e.g. website/src/main/resources/wiki/404.md
    // e.g. website/src/main/resources/wiki/
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
