package cz.voho.common.utility;

import cz.voho.wiki.repository.page.WikiPageSourceRepository;

import java.util.Locale;
import java.util.regex.Pattern;

public class WikiLinkUtility {
    public static String[] splitWikiParts(String wikiParts) {
        return wikiParts.split(stripWikiPrefixSuffix(stripSlashes(Pattern.quote("/"))));
    }

    public static String resolveWikiPageId(String value) {
        value = value.toLowerCase(Locale.ROOT);
        value = stripWikiPrefixSuffix(stripSlashes(value));
        String[] valueParts = splitWikiParts(value);
        String lastPart = lastWikiPart(valueParts);
        value = copyValidChars(lastPart);
        if (value.isEmpty()) {
            value = WikiPageSourceRepository.INDEX_PAGE_ID;
        }
        if (value.endsWith(".md")) {
            value = value.substring(0, value.length() - ".md".length());
        }
        return value;
    }

    public static String resolveParentWikiPageId(String resourceName) {
        final String[] exploded = WikiLinkUtility.splitWikiParts(resourceName);
        if (exploded.length > 1) {
            return exploded[exploded.length - 2];
        }
        return null;
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

    public static String stripWikiPrefixSuffix(String value) {
        if (value.startsWith("website/src/main/resources/")) {
            value = value.substring("website/src/main/resources/".length());
        }
        if (value.endsWith(".md")) {
            value = value.substring(0, value.length() - ".md".length());
        }
        if (value.startsWith("wiki/")) {
            value = value.substring(5);
        }
        if (value.equals("wiki")) {
            value = "";
        }
        return value;
    }

    public static boolean isValidWikiPageSource(String filename) {
        return filename.contains("wiki/") && filename.endsWith(".md");
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

    private static String lastWikiPart(final String[] wikiParts) {
        if (wikiParts.length > 0) {
            return wikiParts[wikiParts.length - 1];
        } else {
            return "";
        }
    }

    private static boolean isValidChar(final char c) {
        return Character.isLowerCase(c) || Character.isDigit(c) || c == '-';
    }
}
