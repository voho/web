package cz.voho.common.utility;

import com.google.common.net.UrlEscapers;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ApiUtility {
    private static final Gson PRETTY_GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Gson UGLY_GSON = new GsonBuilder().create();

    public static String toPrettyJson(final Object object) {
        return PRETTY_GSON.toJson(object);
    }

    public static String toUglyJson(final Object object) {
        return UGLY_GSON.toJson(object);
    }

    public static <T> T fromJson(final String json, final Class<T> type) {
        return UGLY_GSON.fromJson(json, type);
    }

    public static String escapeUrlFragment(final String url) {
        return UrlEscapers.urlFragmentEscaper().escape(url);
    }
}
