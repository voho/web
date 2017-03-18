package cz.voho.wiki.parser;

import cz.voho.common.utility.ReplacePatternCallback;
import cz.voho.wiki.model.ParsedWikiPage;
import cz.voho.wiki.model.WikiPageSource;

import java.util.regex.Pattern;

public class TodoPreprocessor implements Preprocessor {
    @Override
    public String preprocessSource(ParsedWikiPage context, WikiPageSource wikiPageSource, String source) {
        final ReplacePatternCallback rp = new ReplacePatternCallback(Pattern.compile("!TODO!", Pattern.MULTILINE));
        return rp.replace(source, matchResult -> {
            context.setTodo(true);
            return "<span class='todo'>TODO</span>";
        });
    }
}
