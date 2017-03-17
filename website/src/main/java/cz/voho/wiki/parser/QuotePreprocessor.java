package cz.voho.wiki.parser;

import cz.voho.common.utility.ReplacePatternCallback;
import cz.voho.wiki.page.parsed.WikiContext;
import cz.voho.wiki.model.WikiPageSource;

import java.util.regex.Pattern;

public class QuotePreprocessor implements Preprocessor {
    @Override
    public String preprocessSource(WikiContext context, WikiPageSource wikiPageSource, String source) {
        final ReplacePatternCallback rp = new ReplacePatternCallback(Pattern.compile("^>(.+) \\*(.+)\\*$", Pattern.MULTILINE));
        return rp.replace(source, matchResult -> {
            final String quote = matchResult.group(1);
            final String author = matchResult.group(2);
            context.addQuote(quote, author);
            return "> " + quote + "<br />*" + author + "*";
        });
    }
}
