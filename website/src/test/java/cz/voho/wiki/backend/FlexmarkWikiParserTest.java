package cz.voho.wiki.backend;

import com.google.common.io.Resources;
import cz.voho.wiki.model.ParsedWikiPage;
import cz.voho.wiki.model.WikiPageSource;
import cz.voho.wiki.parser.*;
import cz.voho.wiki.repository.image.WikiImageRepository;
import org.junit.Test;
import org.mockito.Mockito;

import java.nio.charset.StandardCharsets;

public class FlexmarkWikiParserTest {
    private FlexmarkWikiParser toTest = new FlexmarkWikiParser(
            new CodePreprocessor(Mockito.mock(WikiImageRepository.class)),
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
        src.setMarkdownSource(sample);
        final ParsedWikiPage page = toTest.parse(src);
        System.out.println(page.getHtml());
    }
}