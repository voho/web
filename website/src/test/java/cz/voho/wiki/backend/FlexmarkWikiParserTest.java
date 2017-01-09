package cz.voho.wiki.backend;

import com.google.common.io.Resources;
import cz.voho.wiki.image.WikiImageCacheWarmUp;
import cz.voho.wiki.model.ParsedWikiPage;
import cz.voho.wiki.model.WikiContext;
import cz.voho.wiki.model.WikiPageSource;
import cz.voho.wiki.parser.CodePreprocessor;
import cz.voho.wiki.parser.FlexmarkWikiParser;
import cz.voho.wiki.parser.ImagePreprocessor;
import cz.voho.wiki.parser.MathPreprocessor;
import cz.voho.wiki.parser.QuotePreprocessor;
import cz.voho.wiki.parser.WikiLinkPreprocessor;
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
            new MathPreprocessor(),
            new QuotePreprocessor(),
            new WikiLinkPreprocessor(),
            new ImagePreprocessor()
    );

    @Test
    public void parse() throws Exception {
        final String sample = Resources.toString(getClass().getResource("/markdown/sample.md"), StandardCharsets.UTF_8);
//        System.out.println(page.getTitle());
    }

    @Test
    public void testGraph() throws Exception {
        final String sample = Resources.toString(getClass().getResource("/markdown/graphs.md"), StandardCharsets.UTF_8);
        final WikiPageSource src = new WikiPageSource();
        src.setSource(sample);
        final ParsedWikiPage page = toTest.parse(new WikiContext(), src);
        System.out.println(page.getHtml());
    }

    @Test
    public void testTables() throws Exception {
        final String sample = Resources.toString(getClass().getResource("/markdown/tables.md"), StandardCharsets.UTF_8);
        final ParsedWikiPage page = new ParsedWikiPage();
        System.out.println(page.getTitle());
    }

    @Test
    public void testLinks() throws Exception {
        final String sample = Resources.toString(getClass().getResource("/markdown/links.md"), StandardCharsets.UTF_8);
        final WikiPageSource src = new WikiPageSource();
        src.setSource(sample);
        final ParsedWikiPage page = toTest.parse(new WikiContext(), src);
        System.out.println(page.getHtml());
    }

    @Test
    public void testQuotes() throws Exception {
        final String sample = Resources.toString(getClass().getResource("/markdown/quotes.md"), StandardCharsets.UTF_8);
        final WikiPageSource src = new WikiPageSource();
        src.setSource(sample);
        final ParsedWikiPage page = toTest.parse(new WikiContext(), src);
        System.out.println(page.getHtml());
    }

    @Test
    public void testFloatingImages() throws Exception {
        final String sample = Resources.toString(getClass().getResource("/markdown/images.md"), StandardCharsets.UTF_8);
        final WikiPageSource src = new WikiPageSource();
        src.setSource(sample);
        final ParsedWikiPage page = toTest.parse(new WikiContext(), src);
        System.out.println(page.getHtml());
    }
}