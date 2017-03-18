package cz.voho.wiki.parser;

import cz.voho.common.utility.ReplacePatternCallback;
import cz.voho.wiki.model.ParsedWikiPage;

import java.util.regex.Pattern;

public class TodoPreprocessor implements Preprocessor {
    @Override
    public void preprocessSource(ParsedWikiPage context) {
        final ReplacePatternCallback rp = new ReplacePatternCallback(Pattern.compile("!TODO!", Pattern.MULTILINE));
        String sourceFixed = rp.replace(context.getSource().getMarkdownSource(), matchResult -> {
            context.setTodo(true);
            return "<span class='todo'>TODO</span>";
        });
        context.getSource().setMarkdownSource(sourceFixed);
    }
}
