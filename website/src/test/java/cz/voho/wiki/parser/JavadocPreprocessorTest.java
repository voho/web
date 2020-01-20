package cz.voho.wiki.parser;

import cz.voho.wiki.model.ParsedWikiPage;
import cz.voho.wiki.model.WikiPageSource;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JavadocPreprocessorTest {
    private final JavadocPreprocessor toTest = new JavadocPreprocessor();

    @Test
    public void noMatch() {
        final String source = "Hello, there is no link here.";
        final String actual = parseSource(source);
        final String expected = source;
        assertEquals(expected, actual);
    }

    @Test
    public void singleUnmatch() {
        final String source = "*Hello*, there is no *link* here.";
        final String actual = parseSource(source);
        final String expected = source;
        assertEquals(expected, actual);
    }

    @Test
    public void singleMatch() {
        final String source = "Here is the link: *javadoc:org.slf4j.Logger* and that is it!";
        final String actual = parseSource(source);
        final String expected = "Here is the link: [org.slf4j.**Logger**](https://github.com/search?l=Java&q=org.slf4j.Logger&type=Code) and that is it!";
        assertEquals(expected, actual);
    }

    @Test
    public void multiMatch() {
        final String source = "Here is the *link*: *javadoc:org.slf4j.Logger* and that is it! Maybe one more: *javadoc:org.slf4j.Logger*";
        final String actual = parseSource(source);
        final String expected = "Here is the *link*: [org.slf4j.**Logger**](https://github.com/search?l=Java&q=org.slf4j.Logger&type=Code) and that is it! Maybe one more: [org.slf4j.**Logger**](https://github.com/search?l=Java&q=org.slf4j.Logger&type=Code)";
        assertEquals(expected, actual);
    }

    @Test
    public void mixedMatch() {
        final String source = "Here is the *link*: *javadoc:org.slf4j.Logger* and that is it!";
        final String actual = parseSource(source);
        final String expected = "Here is the *link*: [org.slf4j.**Logger**](https://github.com/search?l=Java&q=org.slf4j.Logger&type=Code) and that is it!";
        assertEquals(expected, actual);
    }

    private String parseSource(final String source) {
        final ParsedWikiPage page = new ParsedWikiPage();
        page.setSource(new WikiPageSource());
        page.getSource().setMarkdownSource(source);
        toTest.preprocessSource(page);
        return page.getSource().getMarkdownSource();
    }
}
