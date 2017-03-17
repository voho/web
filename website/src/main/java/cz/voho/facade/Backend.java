package cz.voho.facade;

import cz.voho.common.utility.LambdaClient;

public class Backend {
    public static final Backend SINGLETON = new Backend();

    private final RecentBackend recentBackend = new RecentBackend(new LambdaClient());
    private final WikiBackend wikiBackend = new WikiBackend();

    public RecentBackend getRecentBackend() {
        return recentBackend;
    }

    public WikiBackend getWikiBackend() {
        return wikiBackend;
    }
}
