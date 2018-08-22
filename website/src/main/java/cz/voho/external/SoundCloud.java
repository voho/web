package cz.voho.external;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SoundCloud {
    private static final int MINIMAL_GET_TIMEOUT_MS = 5000;
    private static final String SONG_HEADER_SELECTOR = "article.audible[itemprop=track]";
    private static final String SONG_LINK_SELECTOR = "h2 a[itemprop=url]";
    private static final String AUTHOR_URL = "https://soundcloud.com/voho/tracks";
    private static final String SOUNDCLOUD_WIDGET_URL_FORMAT = "https://w.soundcloud.com/player/?url=%s%s";
    private static final String SOUNDCLOUD_SONG_URL_FORMAT = "https://soundcloud.com%s";
    private static final String DEFAULT_LIGHT_COLOR = "9bf1ff";
    private static final String DEFAULT_DARK_COLOR = "242943";

    public List<SoundCloudSong> getLatestSongs(int count, String lightColor, String darkColor) throws IOException {
        final List<SoundCloudSong> listOfSongs = Lists.newLinkedList();
        final int getTimeoutMs = Math.max(MINIMAL_GET_TIMEOUT_MS, MINIMAL_GET_TIMEOUT_MS);
        final Document document = Jsoup.parse(new URL(AUTHOR_URL), getTimeoutMs);
        final Elements songs = document.body().select(SONG_HEADER_SELECTOR);

        for (final Element song : songs) {
            if (listOfSongs.size() >= count) {
                break;
            }

            final Element link = song.select(SONG_LINK_SELECTOR).first();

            if (link != null) {
                listOfSongs.add(toSong(link, coalesce(lightColor, DEFAULT_LIGHT_COLOR), coalesce(darkColor, DEFAULT_DARK_COLOR)));
            }
        }

        return listOfSongs;
    }

    private SoundCloudSong toSong(final Element link, final String lightColor, final String darkColor) {
        final String href = link.attr("href");

        final SoundCloudSong songModel = new SoundCloudSong();
        songModel.setTitle(link.text());
        songModel.setUrl(generateSongUrl(href));
        songModel.setWidgetUrl(generateWidgetUrl(href, lightColor, darkColor));
        return songModel;
    }

    private String generateSongUrl(final String href) {
        return String.format(SOUNDCLOUD_SONG_URL_FORMAT, href);
    }

    private String generateWidgetUrl(final String href, final String lightColor, final String darkColor) {
        final Map<String, String> params = Maps.newHashMap();
        params.put("color", lightColor); // bright blue
        params.put("theme_color", darkColor); // dark blue
        params.put("show_playcount", "false");
        params.put("show_comments", "false");
        params.put("show_bpm", "false");
        params.put("enable_api", "false");
        params.put("show_artwork", "false");
        params.put("hide_related", "true");
        params.put("buying", "true"); // capitalist
        params.put("sharing", "false"); // not a communist
        params.put("download", "false"); // not a pirate party
        final String paramsStr = params.entrySet().stream().map(e -> String.format("%s=%s", e.getKey(), e.getValue())).collect(Collectors.joining("&", "&", ""));
        return String.format(SOUNDCLOUD_WIDGET_URL_FORMAT, generateSongUrl(href), paramsStr);
    }

    @SafeVarargs
    private static <T> T coalesce(final T... values) {
        if (values != null) {
            for (final T value : values) {
                if (value != null) {
                    return value;
                }
            }
        }

        return null;
    }

    public static class SoundCloudSong {
        private String title;
        private String url;
        private String widgetUrl;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getWidgetUrl() {
            return widgetUrl;
        }

        public void setWidgetUrl(String widgetUrl) {
            this.widgetUrl = widgetUrl;
        }
    }
}
