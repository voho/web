package cz.voho.soundlambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cz.voho.web.lambda.model.sound.GetRecentSongsRequest;
import cz.voho.web.lambda.model.sound.GetRecentSongsResponse;
import cz.voho.web.lambda.model.sound.Song;
import cz.voho.web.lambda.model.sound.Songs;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The main AWS Lambda handler.
 */
public class Handler implements RequestHandler<GetRecentSongsRequest, GetRecentSongsResponse> {
    private static final int MINIMAL_GET_TIMEOUT_MS = 3000;
    private static final String SONG_HEADER_SELECTOR = "article.audible[itemprop=track]";
    private static final String SONG_LINK_SELECTOR = "h2 a[itemprop=url]";
    private static final String AUTHOR_URL = "https://soundcloud.com/voho/tracks";
    private static final String SOUNDCLOUD_WIDGET_URL_FORMAT = "https://w.soundcloud.com/player/?url=%s%s";
    private static final String SOUNDCLOUD_SONG_URL_FORMAT = "https://soundcloud.com%s";
    private static final String DEFAULT_LIGHT_COLOR = "9bf1ff";
    private static final String DEFAULT_DARK_COLOR = "242943";

    @Override
    public GetRecentSongsResponse handleRequest(final GetRecentSongsRequest request, final Context context) {
        try {
            final List<Song> listOfSongs = Lists.newLinkedList();
            final int getTimeoutMs = Math.max(MINIMAL_GET_TIMEOUT_MS, context.getRemainingTimeInMillis() - MINIMAL_GET_TIMEOUT_MS);
            final Document document = Jsoup.parse(new URL(AUTHOR_URL), getTimeoutMs);
            final Elements songs = document.body().select(SONG_HEADER_SELECTOR);

            for (final Element song : songs) {
                if (listOfSongs.size() >= request.getCount()) {
                    break;
                }

                final Element link = song.select(SONG_LINK_SELECTOR).first();

                if (link != null) {
                    listOfSongs.add(toSong(link, coalesce(request.getLightColor(), DEFAULT_LIGHT_COLOR), coalesce(request.getDarkColor(), DEFAULT_DARK_COLOR)));
                }
            }

            return toResponse(listOfSongs);
        } catch (final Exception e) {
            log(context, "Internal error: %s", e.toString());
            throw new RuntimeException(e);
        }
    }

    private GetRecentSongsResponse toResponse(final List<Song> listOfSongs) {
        final Songs songsModel = new Songs();
        songsModel.setSongs(listOfSongs);
        final GetRecentSongsResponse response = new GetRecentSongsResponse();
        response.setSongs(songsModel);
        return response;
    }

    private Song toSong(final Element link, final String lightColor, final String darkColor) {
        final String href = link.attr("href");

        final Song songModel = new Song();
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

    private void log(final Context context, final String messageFormat, final Object... arguments) {
        if (context != null) {
            context.getLogger().log(String.format(messageFormat, arguments));
        }
    }
}
