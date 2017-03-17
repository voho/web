package cz.voho.facade;

import cz.voho.common.utility.LambdaClient;

public class Backend {
    public static final Backend SINGLETON = new Backend();

    private final RecentWorkBackend recentWorkBackend = new RecentWorkBackend(new LambdaClient());
    private final WikiBackend wikiBackend = new WikiBackend();

    public RecentWorkBackend getRecentWorkBackend() {
        return recentWorkBackend;
    }

    public WikiBackend getWikiBackend() {
        return wikiBackend;
    }
}
