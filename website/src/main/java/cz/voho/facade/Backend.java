package cz.voho.facade;

import cz.voho.common.utility.LambdaClient;
import cz.voho.wiki.parser.*;
import cz.voho.wiki.repository.image.CachingWikiImageRepository;
import cz.voho.wiki.repository.image.LambdaWikiImageRepository;
import cz.voho.wiki.repository.image.WikiImageRepository;
import cz.voho.wiki.repository.page.*;

public class Backend {
    public static final Backend SINGLETON = new Backend();

    private final LambdaClient lambdaClient = new LambdaClient();
    private final RecentBackend recentBackend = new RecentBackend(lambdaClient);
    private final WikiPageSourceRepository wikiPageSourceRepository = new DefaultWikiPageSourceRepository();
    private final LambdaWikiImageRepository wikiImageRepositoryDelegate = new LambdaWikiImageRepository(lambdaClient);
    private final WikiImageRepository wikiImageRepository = new CachingWikiImageRepository(wikiImageRepositoryDelegate);
    private final WikiParser wikiParser = new FlexmarkWikiParser(
            new CodePreprocessor(wikiImageRepository),
            new QuotePreprocessor(),
            new MathPreprocessor(),
            new WikiLinkPreprocessor(),
            new TodoPreprocessor(),
            new ImagePreprocessor(),
            new JavadocPreprocessor(),
            new TocPreprocessor()
    );
    private final ParsedWikiPageRepository parsedWikiPageRepository = new DefaultParsedWikiPageRepository(wikiPageSourceRepository, wikiParser);
    private final WikiBackend wikiBackend = new WikiBackend(wikiPageSourceRepository, parsedWikiPageRepository, wikiImageRepository);

    public RecentBackend getRecentBackend() {
        return recentBackend;
    }

    public WikiBackend getWikiBackend() {
        return wikiBackend;
    }
}
