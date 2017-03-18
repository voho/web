package cz.voho.common.utility;

import cz.voho.wiki.page.source.WikiPageSourceRepository;

import java.util.Locale;
import java.util.regex.Pattern;

public class WikiLinkUtility {
    public static String[] splitWikiParts(String wikiParts) {
        return wikiParts.split(stripWikiPrefix(stripSlashes(Pattern.quote("/"))));
    }

    public static String lastWikiPart(final String[] wikiParts) {
        if (wikiParts.length > 0) {
            return wikiParts[wikiParts.length - 1];
        } else {
            return "";
        }
    }

    public static String resolveWikiPageId(String value) {
        value = value.toLowerCase(Locale.ROOT);
        value = stripWikiPrefix(stripSlashes(value));
        String[] valueParts = splitWikiParts(value);
        value = copyValidChars(valueParts[valueParts.length - 1]);
        if (value.isEmpty()) {
            value = WikiPageSourceRepository.INDEX_PAGE_ID;
        }
        if (value.endsWith(".md")) {
            return value.substring(0, value.length() - 3);
        }
        return value;
    }

    public static String stripWikiResourcePrefixSuffix(String result) {
        if (result.startsWith("website/src/main/resources/")) {
            result = result.substring("website/src/main/resources/".length());
        }

        if (result.endsWith(".md")) {
            result = result.substring(0, result.length() - ".md".length());
        }

        return result;
    }

    public static String stripSlashes(String value) {
        while (value.startsWith("/")) {
            value = value.substring(1);
        }
        while (value.endsWith("/")) {
            value = value.substring(0, value.length() - 1);
        }
        return value;
    }

    public static String stripWikiPrefix(String value) {
        if (value.startsWith("wiki/")) {
            return value.substring(5);
        } else if (value.equals("wiki")) {
            return "";
        } else {
            return value;
        }
    }

    private static String copyValidChars(final String pathInfo) {
        if (pathInfo == null) {
            return "";
        }

        final StringBuilder sb = new StringBuilder(pathInfo.length());

        for (int i = 0; i < pathInfo.length(); i++) {
            final char c = pathInfo.charAt(i);

            if (isValidChar(c)) {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    public static boolean isValidWikiPageSource(String filename) {
        return filename.contains("wiki/") && filename.endsWith(".md");
    }

    private static boolean isValidChar(final char c) {
        return Character.isLowerCase(c) || Character.isDigit(c) || c == '-';
    }
}
