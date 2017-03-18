package cz.voho.wiki.parser;

import com.vladsch.flexmark.util.html.Escaping;
import cz.voho.common.utility.ReplacePatternCallback;
import cz.voho.wiki.model.ParsedWikiPage;

import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class ImagePreprocessor implements Preprocessor {
    @Override
    public void preprocessSource(ParsedWikiPage context) {
        preprocessFloatingImage(context, "left");
        preprocessFloatingImage(context, "right");
        preprocessImageFigure(context);
    }

    private void preprocessFloatingImage(ParsedWikiPage context, String direction) {
        final ReplacePatternCallback callback = getFloatingImagePattern(direction);
        String sourceUpdated = callback.replace(context.getSource().getMarkdownSource(), matchResult -> getReplacement(direction, matchResult));
        context.getSource().setMarkdownSource(sourceUpdated);
    }

    private void preprocessImageFigure(ParsedWikiPage context) {
        final ReplacePatternCallback rp = new ReplacePatternCallback(Pattern.compile("^!\\[(.+)\\]\\((.+)\\)$", Pattern.MULTILINE));
        String sourceUpdated = rp.replace(context.getSource().getMarkdownSource(), matchResult -> {
            final String alt = Escaping.escapeHtml(matchResult.group(1), true);
            final String src = Escaping.escapeHtml(resolveImageSrc(matchResult.group(2)), true);
            return String.format("<div class='figure picture'><img src='%s' alt='%s' /><p>%s</p></div>", src, alt, alt);
        });
        context.getSource().setMarkdownSource(sourceUpdated);
    }

    private ReplacePatternCallback getFloatingImagePattern(String direction) {
        return new ReplacePatternCallback(Pattern.compile("^!\\[(.+)\\]\\((.+)\\)\\{\\." + direction + "\\}$", Pattern.MULTILINE));
    }

    private String getReplacement(String cssClass, MatchResult matchResult) {
        final String alt = Escaping.escapeHtml(matchResult.group(1), true);
        final String src = Escaping.escapeHtml(resolveImageSrc(matchResult.group(2)), true);
        return String.format("<span class='image " + cssClass + "'><img src='%s' alt='%s' /></span>", src, alt);
    }

    private String resolveImageSrc(String src) {
        if (src.startsWith("http://") || src.startsWith("https://")) {
            return src;
        } else {
            return "/assets/images/" + src;
        }
    }
}
