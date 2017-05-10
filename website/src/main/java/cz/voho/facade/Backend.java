package cz.voho.facade;

import cz.voho.common.utility.LambdaClient;
import cz.voho.wiki.parser.CodePreprocessor;
import cz.voho.wiki.parser.EmojiPreprocessor;
import cz.voho.wiki.parser.FlexmarkWikiParser;
import cz.voho.wiki.parser.ImagePreprocessor;
import cz.voho.wiki.parser.JavadocPreprocessor;
import cz.voho.wiki.parser.MathPreprocessor;
import cz.voho.wiki.parser.QuotePreprocessor;
import cz.voho.wiki.parser.TocPreprocessor;
import cz.voho.wiki.parser.TodoPreprocessor;
import cz.voho.wiki.parser.WikiLinkPreprocessor;
import cz.voho.wiki.parser.WikiParser;
import cz.voho.wiki.repository.image.CachingWikiImageRepository;
import cz.voho.wiki.repository.image.LambdaWikiImageRepository;
import cz.voho.wiki.repository.image.WikiImageRepository;
import cz.voho.wiki.repository.page.DefaultParsedWikiPageRepository;
import cz.voho.wiki.repository.page.DefaultWikiPageSourceRepository;
import cz.voho.wiki.repository.page.ParsedWikiPageRepository;
import cz.voho.wiki.repository.page.WikiPageSourceRepository;

public class Backend {
    public static final Backend SINGLETON = new Backend();

    private final LambdaClient lambdaClient = new LambdaClient();
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
            new TocPreprocessor(),
            new EmojiPreprocessor()
    );
    private final WikiPageSourceRepository wikiPageSourceRepository = new DefaultWikiPageSourceRepository();
    private final ParsedWikiPageRepository parsedWikiPageRepository = new DefaultParsedWikiPageRepository(wikiPageSourceRepository, wikiParser);
    private final WikiBackend wikiBackend = new WikiBackend(wikiPageSourceRepository, parsedWikiPageRepository, wikiImageRepository);
    private final RecentBackend recentBackend = new RecentBackend(lambdaClient);

    public RecentBackend getRecentBackend() {
        return recentBackend;
    }

    public WikiBackend getWikiBackend() {
        return wikiBackend;
    }
}
