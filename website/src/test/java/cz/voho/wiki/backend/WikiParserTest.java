package cz.voho.wiki.backend;

import com.google.common.io.Resources;
import cz.voho.wiki.model.ParsedWikiPage;
import cz.voho.wiki.model.WikiPageSource;
import cz.voho.wiki.parser.CustomWikiParser;
import cz.voho.wiki.parser.WikiParser;
import cz.voho.wiki.repository.image.WikiImageRepository;
import org.junit.Test;
import org.mockito.Mockito;

import java.nio.charset.StandardCharsets;

public class WikiParserTest {
    private WikiParser toTest = new CustomWikiParser(Mockito.mock(WikiImageRepository.class));

    @Test
    public void parse() throws Exception {
        final String sample = Resources.toString(getClass().getResource("/markdown/sample.md"), StandardCharsets.UTF_8);
        final WikiPageSource src = new WikiPageSource();
        src.setMarkdownSource(sample);
        final ParsedWikiPage page = toTest.parse(src);
        System.out.println(page.getHtml());
    }
}