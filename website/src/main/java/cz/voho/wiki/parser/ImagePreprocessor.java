package cz.voho.wiki.parser;

import com.vladsch.flexmark.util.html.Escaping;
import cz.voho.common.utility.ReplacePatternCallback;
import cz.voho.wiki.model.WikiContext;
import cz.voho.wiki.model.WikiPageSource;

import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class ImagePreprocessor implements Preprocessor {
    @Override
    public String preprocessSource(WikiContext context, WikiPageSource wikiPageSource, String source) {
        String result = source;
        result = preprocessFloatingImage(context, wikiPageSource, result, "left");
        result = preprocessFloatingImage(context, wikiPageSource, result, "right");
        result = preprocessImageFigure(context, wikiPageSource, result);
        return result;
    }

    private String preprocessImageFigure(WikiContext context, WikiPageSource wikiPageSource, String source) {
        final ReplacePatternCallback rp = new ReplacePatternCallback(Pattern.compile("^!\\[(.+)\\]\\((.+)\\)$", Pattern.MULTILINE));
        return rp.replace(source, matchResult -> {
            final String alt = Escaping.escapeHtml(matchResult.group(1), true);
            final String src = Escaping.escapeHtml(resolveImageSrc(matchResult.group(2)), true);
            return String.format("<div class='figure picture'><img src='%s' alt='%s' /><p>%s</p></div>", src, alt, alt);
        });
    }

    private String resolveImageSrc(String src) {
        if (src.startsWith("http://") || src.startsWith("https://")) {
            return src;
        } else {
            return "/assets/images/" + src;
        }
    }

    private String preprocessFloatingImage(WikiContext context, WikiPageSource wikiPageSource, String source, String direction) {
        final ReplacePatternCallback callback = getFloatingImagePattern(direction);
        return callback.replace(source, matchResult -> getReplacement(direction, matchResult));
    }

    private ReplacePatternCallback getFloatingImagePattern(String direction) {
        return new ReplacePatternCallback(Pattern.compile("^!\\[(.+)\\]\\((.+)\\)\\{\\." + direction + "\\}$", Pattern.MULTILINE));
    }

    private String getReplacement(String cssClass, MatchResult matchResult) {
        final String alt = Escaping.escapeHtml(matchResult.group(1), true);
        final String src = Escaping.escapeHtml(resolveImageSrc(matchResult.group(2)), true);
        return String.format("<span class='image " + cssClass + "'><img src='%s' alt='%s' /></span>", src, alt);
    }
}
