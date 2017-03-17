package cz.voho.common.utility;

import cz.voho.wiki.page.source.WikiPageSourceRepository;

import java.util.Locale;
import java.util.regex.Pattern;

public class WikiLinkUtility {
    public static String resolveWikiPageId(String value) {
        value = value.toLowerCase(Locale.ROOT);
        value = stripSlashesAndWikiPrefix(value);
        String[] valueParts = value.split(Pattern.quote("/"), 50);
        value = copyValidChars(valueParts[valueParts.length - 1]);
        if (value.isEmpty()) {
            value = WikiPageSourceRepository.INDEX_PAGE_ID;
        }
        return value;
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

    public static String stripSlashesAndWikiPrefix(final String link) {
        String result = stripSlashes(link);

        if (result.startsWith("wiki/")) {
            result = result.substring(5);
        }

        return result;
    }

    public static String copyValidChars(final String pathInfo) {
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

    private static boolean isValidChar(final char c) {
        return Character.isLowerCase(c) || Character.isDigit(c) || c == '-';
    }
}
