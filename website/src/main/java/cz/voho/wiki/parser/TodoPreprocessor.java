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
            return "<img src='/assets/images/todo.png' alt='TODO' />";
        });
        context.getSource().setMarkdownSource(sourceFixed);
    }
}
