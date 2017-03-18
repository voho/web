package cz.voho.wiki.parser;

import cz.voho.wiki.model.ParsedWikiPage;
import cz.voho.wiki.model.WikiPageSource;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JavadocPreprocessorTest {
    private JavadocPreprocessor toTest = new JavadocPreprocessor();

    @Test
    public void noMatch() {
        String source = "Hello, there is no link here.";
        String actual = parseSource(source);
        String expected = source;
        assertEquals(expected, actual);
    }

    @Test
    public void singleUnmatch() {
        String source = "*Hello*, there is no *link* here.";
        String actual = parseSource(source);
        String expected = source;
        assertEquals(expected, actual);
    }

    @Test
    public void singleMatch() {
        String source = "Here is the link: *javadoc:org.slf4j.Logger* and that is it!";
        String actual = parseSource(source);
        String expected = "Here is the link: [org.slf4j.**Logger**](http://grepcode.com/search?query=org.slf4j.Logger) and that is it!";
        assertEquals(expected, actual);
    }

    @Test
    public void multiMatch() {
        String source = "Here is the *link*: *javadoc:org.slf4j.Logger* and that is it! Maybe one more: *javadoc:org.slf4j.Logger*";
        String actual = parseSource(source);
        String expected = "Here is the *link*: [org.slf4j.**Logger**](http://grepcode.com/search?query=org.slf4j.Logger) and that is it! Maybe one more: [org.slf4j.**Logger**](http://grepcode.com/search?query=org.slf4j.Logger)";
        assertEquals(expected, actual);
    }

    @Test
    public void mixedMatch() {
        String source = "Here is the *link*: *javadoc:org.slf4j.Logger* and that is it!";
        String actual = parseSource(source);
        String expected = "Here is the *link*: [org.slf4j.**Logger**](http://grepcode.com/search?query=org.slf4j.Logger) and that is it!";
        assertEquals(expected, actual);
    }

    private String parseSource(String source) {
        ParsedWikiPage page = new ParsedWikiPage();
        page.setSource(new WikiPageSource());
        page.getSource().setMarkdownSource(source);
        toTest.preprocessSource(page);
        return page.getSource().getMarkdownSource();
    }
}