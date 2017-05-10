package cz.voho.wiki.parser;

import cz.voho.common.exception.InitializationException;
import cz.voho.common.utility.ReplacePatternCallback;
import cz.voho.wiki.model.ParsedWikiPage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Data source: http://unicode.org/Public/emoji/5.0/emoji-data.txt
 */
public class EmojiPreprocessor implements Preprocessor {
    private static final Set<String> EMOJIS = new LinkedHashSet<>(3000);

    static {
        fillEmojiCache();
    }

    private static void fillEmojiCache() {
        // http://unicode.org/Public/emoji/5.0/emoji-data.txt

        try (BufferedReader br = new BufferedReader(new InputStreamReader(EmojiPreprocessor.class.getResourceAsStream("/emoji-data.v4.txt"), StandardCharsets.UTF_8))) {
            br
                    .lines()
                    .filter(EmojiPreprocessor::isDataLine)
                    .map(EmojiPreprocessor::splitLine)
                    .forEach(EmojiPreprocessor::processDataLine);
        } catch (IOException e) {
            throw new InitializationException("Cannot read emojis.", e);
        }
    }

    private static void processDataLine(String[] strings) {
        String range = strings[0].trim();

        if (range.contains("..")) {
            // e.g. 1F191..1F19A
            String[] bounds = range.split(Pattern.quote(".."), 2);
            int from = Integer.parseInt(bounds[0], 16);
            int to = Integer.parseInt(bounds[1], 16);
            for (int i = from; i <= to; i++) {
                addEmojiCode(i);
            }
        } else {
            // e.g. 2B55
            int num = Integer.parseInt(range, 16);
            addEmojiCode(num);
        }
    }

    private static void addEmojiCode(int num) {
        EMOJIS.add(String.valueOf(num));
    }

    private static String[] splitLine(String line) {
        return line.split(Pattern.quote(";"));
    }

    private static boolean isDataLine(String line) {
        return !line.startsWith("#") && !line.trim().isEmpty();
    }

    @Override
    public void preprocessSource(final ParsedWikiPage context) {
        final ReplacePatternCallback rp = new ReplacePatternCallback(Pattern.compile("%EMOJI%", Pattern.MULTILINE));
        final String sourceFixed = rp.replace(context.getSource().getMarkdownSource(), matchResult -> {
            StringBuilder sb = new StringBuilder(1024);
            //EmojiManager.data().stream().map(Emoji::getHexHtmlShort).forEach(sb::append);
//            com.vdurmont.emoji.EmojiManager.getAll().stream().map(a -> a.getHtmlHexadecimal()).forEach(sb::append);
            EMOJIS.forEach(a -> {
                sb.append("&#").append(a).append(";");
            });
            return sb.toString();
        });
        context.getSource().setMarkdownSource(sourceFixed);
    }
}
