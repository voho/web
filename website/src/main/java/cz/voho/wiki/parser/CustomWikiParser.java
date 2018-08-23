package cz.voho.wiki.parser;

import cz.voho.wiki.parser.code.DotCodePreprocessor;
import cz.voho.wiki.parser.code.IncludeSourceCodePreprocessor;
import cz.voho.wiki.parser.code.InlineSourceCodePreprocessor;
import cz.voho.wiki.parser.code.RunkitCodePreprocessor;
import cz.voho.wiki.parser.code.UmlCodePreprocessor;
import cz.voho.wiki.repository.image.WikiImageRepository;

public class CustomWikiParser extends FlexmarkWikiParser {
    public CustomWikiParser(final WikiImageRepository wikiImageRepository) {
        super(
                new CodePreprocessor(
                        new DotCodePreprocessor(wikiImageRepository),
                        new IncludeSourceCodePreprocessor(),
                        new InlineSourceCodePreprocessor(),
                        new RunkitCodePreprocessor(),
                        new UmlCodePreprocessor()
                ),
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
