package cz.voho.external;

import cz.voho.common.utility.ApiUtility;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SoundCloud {
    private static final String WIDGET_URL_FORMAT = "https://w.soundcloud.com/player/" +
            "?url=%s" +
            "&color=1499ff" +
            "&auto_play=false" +
            "&buying=true&liking=false" +
            "&download=false" +
            "&sharing=false" +
            "&show_artwork=true" +
            "&show_comments=false" +
            "&show_playcount=false" +
            "&show_user=false" +
            "&hide_related=true" +
            "&visual=false" +
            "&start_track=0";

    public static void main(final String[] args) throws IOException {
        final List<SoundCloudSong> songs = new SoundCloud().getLatestSongs(10);
        System.out.println(songs);
    }

    public List<SoundCloudSong> getLatestSongs(final int limit) throws IOException {
        final Document rssParsed = Jsoup.parse(new URL("http://feeds.soundcloud.com/users/soundcloud:users:764886559/sounds.rss"), 10000);
        final Elements songLinks = rssParsed.select("item link");
        final List<SoundCloudSong> result = new ArrayList<>(limit);
        songLinks.stream().limit(limit).forEach(songLink -> result.add(toSong(songLink)));
        return result;
    }

    private SoundCloudSong toSong(final Element songLink) {
        final String songUrl = songLink.text();
        final SoundCloudSong song = new SoundCloudSong();
        song.setWidgetUrl(String.format(WIDGET_URL_FORMAT, ApiUtility.escapeUrlFragment(songUrl)));
        return song;
    }

    public static class SoundCloudSong {
        private String widgetUrl;

        public String getWidgetUrl() {
            return widgetUrl;
        }

        public void setWidgetUrl(final String widgetUrl) {
            this.widgetUrl = widgetUrl;
        }
    }
}
