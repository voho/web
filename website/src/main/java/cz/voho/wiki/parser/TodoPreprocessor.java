package cz.voho.wiki.parser;

import cz.voho.common.utility.ReplacePatternCallback;
import cz.voho.wiki.model.ParsedWikiPage;

import java.util.regex.Pattern;

public class TodoPreprocessor implements Preprocessor {
    private static final String TODO_PLACEHOLDER = "!TODO!";
    private static final String TODO_IMAGE = "<img src='/assets/images/todo.png' alt='TODO' />";

    @Override
    public void preprocessSource(final ParsedWikiPage context) {
        final ReplacePatternCallback callback = new ReplacePatternCallback(Pattern.compile(TODO_PLACEHOLDER, Pattern.MULTILINE));

        final String sourceFixed = callback.replace(context.getSource().getMarkdownSource(), matchResult -> {
            context.setTodo(true);
            return TODO_IMAGE;
        });

        context.getSource().setMarkdownSource(sourceFixed);
    }
}
