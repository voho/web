package cz.voho.wiki.parser;

import com.google.common.base.Joiner;
import com.google.common.net.UrlEscapers;
import cz.voho.common.utility.ReplacePatternCallback;
import cz.voho.wiki.model.WikiPageSource;
import cz.voho.wiki.repository.parsed.WikiParsingContext;

import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class JavadocPreprocessor implements Preprocessor {
    @Override
    public String preprocessSource(final WikiParsingContext context, final WikiPageSource wikiPageSource, final String source) {
        final ReplacePatternCallback rp = new ReplacePatternCallback(Pattern.compile("\\*javadoc:(.+?)\\*"));
        return rp.replace(source, matchResult -> {
            final String className = processClassName(matchResult);
            final String classNameJavadocUrl = String.format("http://grepcode.com/search?query=%s", UrlEscapers.urlFragmentEscaper().escape(className));
            return String.format("[%s](%s)", className, classNameJavadocUrl);
        });
    }

    private String processClassName(MatchResult matchResult) {
        String result = matchResult.group(1);
        String[] path = result.split(Pattern.quote("."));

        if (path.length > 0) {
            path[path.length - 1] = String.format("**%s**", path[path.length - 1]);
            result = Joiner.on(".").join(path);
        }

        return result;
    }
}
