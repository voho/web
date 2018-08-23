package cz.voho.wiki.parser;

import cz.voho.wiki.repository.image.WikiImageRepository;

public class CustomWikiParser extends FlexmarkWikiParser {
    public CustomWikiParser(final WikiImageRepository wikiImageRepository) {
        super(
                new DotCodePreprocessor(wikiImageRepository),
                new UmlCodePreprocessor(),
                new RunkitCodePreprocessor(),
                new IncludeSourceCodePreprocessor(),
                new InlineSourceCodePreprocessor(),
                new QuotePreprocessor(),
                new MathPreprocessor(),
                new WikiLinkPreprocessor(),
                new TodoPreprocessor(),
                new ImagePreprocessor(),
                new JavadocPreprocessor(),
                new TocPreprocessor()
        );
    }
}
