package cz.voho.wiki.parser;

import cz.voho.common.utility.ReplacePatternCallback;
import cz.voho.wiki.model.ParsedWikiPage;

import java.util.regex.Pattern;

public class QuotePreprocessor implements Preprocessor {
    @Override
    public void preprocessSource(ParsedWikiPage context) {
        final ReplacePatternCallback rp = new ReplacePatternCallback(Pattern.compile("^>(.+) \\*(.+)\\*$", Pattern.MULTILINE));
        String sourceUpdated = rp.replace(context.getSource().getMarkdownSource(), matchResult -> {
            final String quote = matchResult.group(1);
            final String author = matchResult.group(2);
            context.addQuote(quote, author);
            return "> " + quote + "<br />*" + author + "*";
        });
        context.getSource().setMarkdownSource(sourceUpdated);
    }
}
