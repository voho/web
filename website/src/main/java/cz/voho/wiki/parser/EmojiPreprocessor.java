package cz.voho.wiki.parser;

import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import cz.voho.common.utility.ReplacePatternCallback;
import cz.voho.wiki.model.ParsedWikiPage;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

/**
 * Replaces :xxx: with corresponding emoji.
 * For rendering, the image is used, e.g. for 0x1f17f = https://cdn.rawgit.com/twitter/twemoji/gh-pages/16x16/1f17f.png
 */
public class EmojiPreprocessor implements Preprocessor {
    private static final Gson GSON = new GsonBuilder().create();
    private static JsonObject smileys;

    static {
        try {
            final URL mapResource = EmojiPreprocessor.class.getResource("/emojimap.json");
            String map = Resources.toString(mapResource, StandardCharsets.UTF_8);
            smileys = new JsonParser().parse(map).getAsJsonObject();
        } catch (IOException e) {
            // ignore
        }
    }

    private static String getEmojiByName(String name) {
        return smileys == null ? "" : smileys.get(name) == null ? "" : smileys.get(name).getAsString();
    }

    @Override
    public void preprocessSource(final ParsedWikiPage context) {
        final ReplacePatternCallback rp = new ReplacePatternCallback(Pattern.compile(":([\\-\\+\\_a-z0-9]+):", Pattern.MULTILINE));
        final String sourceFixed = rp.replace(context.getSource().getMarkdownSource(), matchResult -> {
            final String code = matchResult.group(1);
            final String smiley = getEmojiByName(code);
            if (smiley != null) {
                return smiley;
            } else {
                return code;
            }
            //  return String.format("<img src='https://cdn.rawgit.com/twitter/twemoji/gh-pages/16x16/%s.png' alt='%s' />", code, code);
        });
        context.getSource().setMarkdownSource(sourceFixed);
    }
}
