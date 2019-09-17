package cz.voho.facade;

import cz.voho.common.utility.ExecutorProvider;
import cz.voho.external.Configuration;
import cz.voho.external.Instagram;
import cz.voho.external.SoundCloud;
import cz.voho.external.Spotify;
import org.apache.http.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class RecentBackend {
    private static final Logger LOG = LoggerFactory.getLogger(RecentBackend.class);
    private static final int RECENT_PHOTO_COUNT = 6;
    private static final int RECENT_SONGS_COUNT = 6;
    private static final int UPDATE_INTERVAL_SECONDS = 3600;

    private final Instagram instagram;
    private final SoundCloud soundCloud;
    private final Spotify spotify;
    private final AtomicReference<List<Instagram.InstagramImage>> recentPhotosCache;
    private final AtomicReference<List<SoundCloud.SoundCloudSong>> recentSongsCache;

    RecentBackend(final HttpClient httpClient, final Configuration configuration) {
        this.instagram = new Instagram(httpClient, configuration);
        this.soundCloud = new SoundCloud();
        this.spotify = new Spotify(httpClient, configuration);
        this.recentPhotosCache = new AtomicReference<>(null);
        this.recentSongsCache = new AtomicReference<>(null);

        ExecutorProvider.RECENT_ITEMS_UPDATER_EXECUTOR.scheduleWithFixedDelay(
                this::update,
                0,
                UPDATE_INTERVAL_SECONDS,
                TimeUnit.SECONDS
        );
    }

    private void update() {
        LOG.info("Updating recent items...");
        updateRecentPhotos();
        updateRecentSongs();
        LOG.info("Updating recent items complete.");
    }

    private void updateRecentSongs() {
        LOG.info("Updating song cache.");

        try {
            //final List<SoundCloud.SoundCloudSong> response = soundCloud.getLatestSongs(RECENT_SONGS_COUNT, "606af6", "606af6");
            final List<SoundCloud.SoundCloudSong> response = spotify.getLatestSongs(RECENT_SONGS_COUNT);
            recentSongsCache.set(response);
        } catch (IOException e) {
            LOG.warn("Could not upgrade song cache.", e);
        }
    }

    private void updateRecentPhotos() {
        LOG.info("Updating photo cache.");

        try {
            final List<Instagram.InstagramImage> response = instagram.getLatestPhotos(RECENT_PHOTO_COUNT);
            recentPhotosCache.set(response);
        } catch (IOException e) {
            LOG.warn("Could not upgrade photo cache.", e);
        }
    }

    public List<Instagram.InstagramImage> getRecentPhotos() {
        return Optional.ofNullable(recentPhotosCache.get()).orElse(Collections.emptyList());
    }

    public List<SoundCloud.SoundCloudSong> getRecentTracks() {
        return Optional.ofNullable(recentSongsCache.get()).orElse(Collections.emptyList());
    }
}
