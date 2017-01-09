package cz.voho.photo;

import cz.voho.utility.ExecutorProvider;
import cz.voho.utility.LambdaClient;
import cz.voho.web.lambda.model.photo.GetRecentPhotosRequest;
import cz.voho.web.lambda.model.photo.GetRecentPhotosResponse;
import cz.voho.web.lambda.model.photo.Image;
import cz.voho.web.lambda.model.sound.GetRecentSongsRequest;
import cz.voho.web.lambda.model.sound.GetRecentSongsResponse;
import cz.voho.web.lambda.model.sound.Song;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class RecentWorkBackend {
    public static final RecentWorkBackend SINGLETON = new RecentWorkBackend(new LambdaClient());

    private static final Logger LOG = LoggerFactory.getLogger(RecentWorkBackend.class);
    private static final int RECENT_PHOTO_COUNT = 6;
    private static final int RECENT_SONGS_COUNT = 4;
    private static final int UPDATE_INTERVAL_SECONDS = 300;

    private final ScheduledExecutorService scheduledExecutorService;
    private final AtomicReference<GetRecentPhotosResponse> photosCache;
    private final AtomicReference<GetRecentSongsResponse> songsCache;

    private RecentWorkBackend(final LambdaClient lambdaClient) {
        this.scheduledExecutorService = ExecutorProvider.INSTAGRAM_UPDATER_EXECUTOR;
        this.photosCache = new AtomicReference<>(null);
        this.songsCache = new AtomicReference<>(null);

        this.scheduledExecutorService.scheduleWithFixedDelay(
                () -> updateCache(lambdaClient),
                0,
                UPDATE_INTERVAL_SECONDS,
                TimeUnit.SECONDS
        );
    }

    private void updateCache(final LambdaClient lambdaClient) {
        updateRecentPhotos(lambdaClient);
        updateRecentSongs(lambdaClient);
    }

    private void updateRecentSongs(final LambdaClient lambdaClient) {
        final GetRecentSongsRequest request = new GetRecentSongsRequest();
        request.setCount(RECENT_SONGS_COUNT);

        final GetRecentSongsResponse response = lambdaClient.callSoundcloudLambda(request);

        if (response != null && response.getSongs() != null && response.getSongs().getSongs() != null) {
            LOG.info("Updating SoundCloud cache.");
            songsCache.set(response);
        } else {
            LOG.warn("Could not upgrade SoundCloud cache.");
        }
    }

    private void updateRecentPhotos(final LambdaClient lambdaClient) {
        final GetRecentPhotosRequest request = new GetRecentPhotosRequest();
        request.setCount(RECENT_PHOTO_COUNT);

        final GetRecentPhotosResponse response = lambdaClient.callInstagramLambda(request);

        if (response != null && response.getRecentItems() != null && response.getRecentItems().getData() != null) {
            LOG.info("Updating Instagram cache.");
            photosCache.set(response);
        } else {
            LOG.warn("Could not upgrade Instagram cache.");
        }
    }

    public List<Song> getRecentTracks() {
        if (songsCache.get() == null) {
            return Collections.emptyList();
        }
        return songsCache.get().getSongs().getSongs();
    }

    public List<Image> getRecentImages() {
        if (photosCache.get() == null) {
            return Collections.emptyList();
        }
        return photosCache.get().getRecentItems().getData();
    }
}
