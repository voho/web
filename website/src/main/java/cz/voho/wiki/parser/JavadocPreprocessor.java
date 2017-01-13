package cz.voho.wiki.parser;

import com.google.common.net.UrlEscapers;
import cz.voho.utility.ReplacePatternCallback;
import cz.voho.wiki.model.WikiContext;
import cz.voho.wiki.model.WikiPageSource;

import java.util.regex.Pattern;

public class JavadocPreprocessor implements Preprocessor {
    @Override
    public String preprocessSource(final WikiContext context, final WikiPageSource wikiPageSource, final String source) {
        final ReplacePatternCallback rp = new ReplacePatternCallback(Pattern.compile("\\*javadoc:(.+?)\\*"));
        return rp.replace(source, matchResult -> {
            final String className = matchResult.group(1);
            final String classNameJavadocUrl = String.format("http://grepcode.com/search?query=%s", UrlEscapers.urlFragmentEscaper().escape(className));
            return String.format("*[%s](%s)*", className, classNameJavadocUrl);
        });
    }
}
