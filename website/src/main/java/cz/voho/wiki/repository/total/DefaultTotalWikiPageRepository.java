package cz.voho.wiki.repository.total;

import cz.voho.common.utility.LambdaClient;
import cz.voho.wiki.repository.image.CachingWikiImageRepository;
import cz.voho.wiki.repository.image.LambdaWikiImageRepository;
import cz.voho.wiki.repository.parsed.DefaultParsedWikiPageRepository;
import cz.voho.wiki.repository.parsed.ParsedWikiPageRepository;
import cz.voho.wiki.repository.parsed.WikiParsingContext;
import cz.voho.wiki.repository.source.DefaultWikiPageSourceRepository;
import cz.voho.wiki.repository.source.WikiPageSourceRepository;
import cz.voho.wiki.parser.*;

public class DefaultTotalWikiPageRepository implements TotalWikiPageRepository {
    private final WikiPageSourceRepository wikiPageSourceRepository;
    private final FlexmarkWikiParser wikiParser;

    public DefaultTotalWikiPageRepository() {
        wikiPageSourceRepository = new DefaultWikiPageSourceRepository();

        wikiParser = new FlexmarkWikiParser(
                new CodePreprocessor(new CachingWikiImageRepository(new LambdaWikiImageRepository(new LambdaClient()))),
                new QuotePreprocessor(),
                new MathPreprocessor(),
                new WikiLinkPreprocessor(),
                new TodoPreprocessor(),
                new ImagePreprocessor(),
                new JavadocPreprocessor(),
                new TocPreprocessor()
        );
    }

    @Override
    public WikiParsingContext createContext() {
        WikiParsingContext wikiParsingContext = new WikiParsingContext();

        ParsedWikiPageRepository parsedWikiPageRepository = new DefaultParsedWikiPageRepository(
                wikiParser,
                wikiPageSourceRepository,
                wikiParsingContext
        );

        parsedWikiPageRepository.getWikiPageIds().forEach(wikiPageId -> wikiParsingContext.addParsedPage(wikiPageId, parsedWikiPageRepository.getParsedWikiPageById(wikiPageId)));

        return wikiParsingContext;
    }
}
