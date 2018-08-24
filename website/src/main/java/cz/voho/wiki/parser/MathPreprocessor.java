package cz.voho.wiki.parser;

import cz.voho.common.utility.ReplacePatternCallback;
import cz.voho.wiki.model.ParsedWikiPage;

import java.util.regex.Pattern;

public class MathPreprocessor implements Preprocessor {
    @Override
    public void preprocessSource(final ParsedWikiPage context) {
        final ReplacePatternCallback rp = new ReplacePatternCallback(Pattern.compile("€€(.+?)€€", Pattern.DOTALL));
        final String sourceUpdated = rp.replace(context.getSource().getMarkdownSource(), matchResult -> {
            final String mathJaxSource = matchResult.group(1);
            return "<div class='figure math'>£" + mathJaxSource + "£</div>";
        });
        context.getSource().setMarkdownSource(sourceUpdated);
    }
}
