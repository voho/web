package cz.voho.wiki.backend;

import com.google.common.io.Resources;
import cz.voho.wiki.image.WikiImageCacheWarmUp;
import cz.voho.wiki.model.ParsedWikiPage;
import cz.voho.wiki.page.parsed.WikiParsingContext;
import cz.voho.wiki.model.WikiPageSource;
import cz.voho.wiki.parser.*;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

/**
 * Created by vojta on 13.12.2016.
 */
public class FlexmarkWikiParserTest {
    private FlexmarkWikiParser toTest = new FlexmarkWikiParser(
            new CodePreprocessor(new WikiImageCacheWarmUp() {
                @Override
                public void warmUpCacheDotSvg(final String source) {
                    // NOP
                }

                @Override
                public void warmUpCachePlantUmlSvg(final String source) {
                    // NOP
                }
            }),
            new QuotePreprocessor(),
            new MathPreprocessor(),
            new WikiLinkPreprocessor(),
            new TodoPreprocessor(),
            new ImagePreprocessor(),
            new JavadocPreprocessor()
    );

    @Test
    public void parse() throws Exception {
        final String sample = Resources.toString(getClass().getResource("/markdown/sample.md"), StandardCharsets.UTF_8);
        final WikiPageSource src = new WikiPageSource();
        src.setSource(sample);
        final ParsedWikiPage page = toTest.parse(new WikiParsingContext(), src);
        System.out.println(page.getHtml());
    }
}