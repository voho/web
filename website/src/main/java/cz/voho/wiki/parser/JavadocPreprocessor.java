package cz.voho.wiki.parser;

import com.google.common.base.Joiner;
import com.google.common.net.UrlEscapers;
import cz.voho.common.utility.ReplacePatternCallback;
import cz.voho.wiki.model.ParsedWikiPage;

import java.util.regex.Pattern;

public class JavadocPreprocessor implements Preprocessor {
    @Override
    public void preprocessSource(final ParsedWikiPage context) {
        final ReplacePatternCallback rp = new ReplacePatternCallback(Pattern.compile("\\*javadoc:(.+?)\\*"));
        String sourceUpdated = rp.replace(context.getSource().getMarkdownSource(), matchResult -> {
            String className = matchResult.group(1);
            final String classNameFormatted = processClassName(className);
            final String classNameJavadocUrl = String.format("http://grepcode.com/search?query=%s", UrlEscapers.urlFragmentEscaper().escape(className));
            return String.format("[%s](%s)", classNameFormatted, classNameJavadocUrl);
        });
        context.getSource().setMarkdownSource(sourceUpdated);
    }

    private String processClassName(String className) {
        String[] path = className.split(Pattern.quote("."));

        if (path.length > 0) {
            path[path.length - 1] = String.format("**%s**", path[path.length - 1]);
            className = Joiner.on(".").join(path);
        }

        return className;
    }
}
