package cz.voho.wiki.parser;

import cz.voho.common.utility.ReplacePatternCallback;
import cz.voho.wiki.page.parsed.WikiParsingContext;
import cz.voho.wiki.model.WikiPageSource;

import java.util.regex.Pattern;

public class TodoPreprocessor implements Preprocessor {
    @Override
    public String preprocessSource(WikiParsingContext context, WikiPageSource wikiPageSource, String source) {
        final ReplacePatternCallback rp = new ReplacePatternCallback(Pattern.compile("!TODO!", Pattern.MULTILINE));
        return rp.replace(source, matchResult -> {
            context.addTodo(wikiPageSource.getId());
            return "<span class='todo'>TODO</span>";
        });
    }
}
