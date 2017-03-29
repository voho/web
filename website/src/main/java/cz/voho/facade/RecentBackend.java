package cz.voho.facade;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import cz.voho.common.utility.ExecutorProvider;
import cz.voho.common.utility.LambdaClient;
import cz.voho.common.utility.WikiLinkUtility;
import cz.voho.web.lambda.model.github.CommitMeta;
import cz.voho.web.lambda.model.github.GetRecentCommitsRequest;
import cz.voho.web.lambda.model.github.GetRecentCommitsResponse;
import cz.voho.web.lambda.model.photo.GetRecentPhotosRequest;
import cz.voho.web.lambda.model.photo.GetRecentPhotosResponse;
import cz.voho.web.lambda.model.photo.Image;
import cz.voho.web.lambda.model.sound.GetRecentSongsRequest;
import cz.voho.web.lambda.model.sound.GetRecentSongsResponse;
import cz.voho.web.lambda.model.sound.Song;
import cz.voho.wiki.model.WikiPageCommitGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class RecentBackend {
    private static final Logger LOG = LoggerFactory.getLogger(RecentBackend.class);
    private static final int RECENT_PHOTO_COUNT = 6;
    private static final int RECENT_SONGS_COUNT = 4;
    private static final int RECENT_WIKI_UPDATES_COUNT = 10;
    private static final int UPDATE_INTERVAL_SECONDS = 300;
    private static final String WIKI_COMMIT_PATH = "website/src/main/resources/wiki/";

    private final ScheduledExecutorService scheduledExecutorService;
    private final LambdaClient lambdaClient;
    private final AtomicReference<GetRecentPhotosResponse> recentPhotosCache;
    private final AtomicReference<GetRecentSongsResponse> recentSongsCache;
    private final AtomicReference<GetRecentCommitsResponse> recentCommitsCache;

    RecentBackend(final LambdaClient lambdaClient) {
        this.scheduledExecutorService = ExecutorProvider.INSTAGRAM_UPDATER_EXECUTOR;
        this.lambdaClient = lambdaClient;
        this.recentPhotosCache = new AtomicReference<>(null);
        this.recentSongsCache = new AtomicReference<>(null);
        this.recentCommitsCache = new AtomicReference<>(null);

        this.scheduledExecutorService.scheduleWithFixedDelay(
                this::update,
                0,
                UPDATE_INTERVAL_SECONDS,
                TimeUnit.SECONDS
        );
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        scheduledExecutorService.shutdown();
    }

    private void update() {
        LOG.info("Updating...");
        updateRecentPhotos();
        updateRecentSongs();
        updateRecentWikiChanges();
        LOG.info("Updating complete.");
    }

    private void updateRecentWikiChanges() {
        final GetRecentCommitsRequest request = new GetRecentCommitsRequest();
        request.setPath(WIKI_COMMIT_PATH);

        final GetRecentCommitsResponse response = lambdaClient.callGitHubLambda(request);

        if (response != null && response.getCommits() != null && !response.getCommits().isEmpty()) {
            LOG.info("Updating recent wiki changes cache.");
            recentCommitsCache.set(response);
        } else {
            LOG.warn("Could not upgrade recent wiki changes cache.");
        }
    }

    private void updateRecentSongs() {
        final GetRecentSongsRequest request = new GetRecentSongsRequest();
        request.setCount(RECENT_SONGS_COUNT);
        request.setDarkColor("606af6");
        request.setLightColor("606af6");

        final GetRecentSongsResponse response = lambdaClient.callSoundcloudLambda(request);

        if (response != null && response.getSongs() != null && response.getSongs().getSongs() != null) {
            LOG.info("Updating SoundCloud cache.");
            recentSongsCache.set(response);
        } else {
            LOG.warn("Could not upgrade SoundCloud cache.");
        }
    }

    private void updateRecentPhotos() {
        final GetRecentPhotosRequest request = new GetRecentPhotosRequest();
        request.setCount(RECENT_PHOTO_COUNT);

        final GetRecentPhotosResponse response = lambdaClient.callInstagramLambda(request);

        if (response != null && response.getRecentItems() != null && response.getRecentItems().getData() != null) {
            LOG.info("Updating Instagram cache.");
            recentPhotosCache.set(response);
        } else {
            LOG.warn("Could not upgrade Instagram cache.");
        }
    }

    public List<Song> getRecentTracks() {
        final GetRecentSongsResponse latestValue = recentSongsCache.get();

        if (latestValue == null) {
            return Collections.emptyList();
        }

        return latestValue.getSongs().getSongs();
    }

    public List<Image> getRecentPhotos() {
        final GetRecentPhotosResponse latestValue = recentPhotosCache.get();

        if (latestValue == null) {
            return Collections.emptyList();
        }

        return latestValue.getRecentItems().getData();
    }

    public List<WikiPageCommitGroup> getRecentWikiChanges() {
        final GetRecentCommitsResponse latestValue = recentCommitsCache.get();

        if (latestValue == null) {
            return Collections.emptyList();
        }

        SetMultimap<String, CommitMeta> filesToSortedCommits = HashMultimap.create();

        for (CommitMeta commit : latestValue.getCommits()) {
            for (String filename : commit.getFilenames()) {
                if (WikiLinkUtility.isValidWikiPageSource(filename)) {
                    filesToSortedCommits.put(filename, commit);
                }
            }
        }

        List<WikiPageCommitGroup> groups = new LinkedList<>();

        filesToSortedCommits.asMap().entrySet().forEach(entry -> {
            WikiPageCommitGroup group = new WikiPageCommitGroup();
            group.setFilename(entry.getKey());
            List<CommitMeta> commits = new ArrayList<>(entry.getValue());
            Collections.sort(commits, (o1, o2) -> {
                LocalDateTime o1d = LocalDateTime.parse(o1.getIsoTime(), DateTimeFormatter.ISO_DATE_TIME);
                LocalDateTime o2d = LocalDateTime.parse(o2.getIsoTime(), DateTimeFormatter.ISO_DATE_TIME);
                return o1d.compareTo(o2d);
            });
            CommitMeta newest = commits.iterator().next();
            group.setLatestDate(newest.getIsoTime());
            group.setLatestCommitSha(newest.getSha());
            group.setLatestCommitMessage(newest.getMessage());
            groups.add(group);
        });

        Collections.sort(groups, Comparator.comparing(WikiPageCommitGroup::getLatestDate).reversed());

        return groups.subList(0, RECENT_WIKI_UPDATES_COUNT);
    }
}
