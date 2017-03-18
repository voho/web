package cz.voho.web.lambda.model.github;

import java.util.List;

public class GetRecentCommitsResponse {
    private List<CommitMeta> commits;

    public List<CommitMeta> getCommits() {
        return commits;
    }

    public void setCommits(List<CommitMeta> commits) {
        this.commits = commits;
    }
}
