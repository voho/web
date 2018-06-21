package cz.voho.facade;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import cz.voho.common.utility.ExecutorProvider;
import cz.voho.common.utility.WikiLinkUtility;
import cz.voho.external.Configuration;
import cz.voho.external.GitHub;
import cz.voho.external.Instagram;
import cz.voho.external.SoundCloud;
import cz.voho.wiki.model.WikiPageCommitGroup;
import org.apache.http.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class RecentBackend {
    private static final Logger LOG = LoggerFactory.getLogger(RecentBackend.class);
    private static final int RECENT_PHOTO_COUNT = 6;
    private static final int RECENT_SONGS_COUNT = 4;
    private static final int RECENT_WIKI_UPDATES_COUNT = 10;
    private static final int UPDATE_INTERVAL_SECONDS = 3600;
    private static final String WIKI_COMMIT_PATH = "website/src/main/resources/wiki/";

    private final ScheduledExecutorService scheduledExecutorService;
    private final GitHub gitHub;
    private final Instagram instagram;
    private final SoundCloud soundCloud;
    private final AtomicReference<List<Instagram.InstagramImage>> recentPhotosCache;
    private final AtomicReference<List<SoundCloud.SoundCloudSong>> recentSongsCache;
    private final AtomicReference<List<GitHub.GitHubCommitMeta>> recentCommitsCache;

    RecentBackend(final HttpClient httpClient, final Configuration configuration) {
        this.scheduledExecutorService = ExecutorProvider.INSTAGRAM_UPDATER_EXECUTOR;
        this.gitHub = new GitHub(httpClient, configuration);
        this.instagram = new Instagram(httpClient, configuration);
        this.soundCloud = new SoundCloud();
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
        LOG.info("Updating recent wiki changes cache.");

        try {
            final List<GitHub.GitHubCommitMeta> response = gitHub.getLatestCommits(WIKI_COMMIT_PATH);
            recentCommitsCache.set(response);
        } catch (IOException e) {
            LOG.warn("Could not upgrade recent wiki changes cache.", e);
        }
    }

    private void updateRecentSongs() {
        LOG.info("Updating SoundCloud cache.");

        try {
            final List<SoundCloud.SoundCloudSong> response = soundCloud.getLatestSongs(RECENT_SONGS_COUNT, "606af6", "606af6");
            recentSongsCache.set(response);
        } catch (IOException e) {
            LOG.warn("Could not upgrade SoundCloud cache.", e);
        }
    }

    private void updateRecentPhotos() {
        LOG.info("Updating Instagram cache.");

        try {
            final List<Instagram.InstagramImage> response = instagram.getLatestPhotos(RECENT_PHOTO_COUNT);
            recentPhotosCache.set(response);
        } catch (IOException e) {
            LOG.warn("Could not upgrade Instagram cache.", e);
        }
    }

    public List<Instagram.InstagramImage> getRecentPhotos() {
        return Optional.ofNullable(recentPhotosCache.get()).orElse(Collections.emptyList());
    }

    public List<SoundCloud.SoundCloudSong> getRecentTracks() {
        return Optional.ofNullable(recentSongsCache.get()).orElse(Collections.emptyList());
    }

    public List<WikiPageCommitGroup> getRecentWikiChanges() {
        final List<GitHub.GitHubCommitMeta> latestValue = Optional.ofNullable(recentCommitsCache.get()).orElse(Collections.emptyList());

        final SetMultimap<String, GitHub.GitHubCommitMeta> filesToSortedCommits = HashMultimap.create();

        for (final GitHub.GitHubCommitMeta commit : latestValue) {
            for (final String filename : commit.getFilenames()) {
                if (WikiLinkUtility.isValidWikiPageSource(filename)) {
                    filesToSortedCommits.put(filename, commit);
                }
            }
        }

        final List<WikiPageCommitGroup> groups = new LinkedList<>();

        filesToSortedCommits.asMap().entrySet().forEach(entry -> {
            final WikiPageCommitGroup group = new WikiPageCommitGroup();
            group.setFilename(entry.getKey());
            final List<GitHub.GitHubCommitMeta> commits = new ArrayList<>(entry.getValue());
            Collections.sort(commits, (o1, o2) -> {
                final LocalDateTime o1d = LocalDateTime.parse(o1.getIsoTime(), DateTimeFormatter.ISO_DATE_TIME);
                final LocalDateTime o2d = LocalDateTime.parse(o2.getIsoTime(), DateTimeFormatter.ISO_DATE_TIME);
                return o1d.compareTo(o2d);
            });
            final GitHub.GitHubCommitMeta newest = commits.get(commits.size() - 1);
            group.setLatestDate(newest.getIsoTime());
            group.setLatestCommitSha(newest.getSha());
            group.setLatestCommitMessage(newest.getMessage());
            groups.add(group);
        });

        Collections.sort(groups, Comparator.comparing(WikiPageCommitGroup::getLatestDate).reversed());

        return groups.subList(0, RECENT_WIKI_UPDATES_COUNT);
    }
}
