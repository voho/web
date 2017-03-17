package cz.voho.wiki.page.source;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.io.Resources;
import com.google.common.reflect.ClassPath;
import cz.voho.common.exception.ContentNotFoundException;
import cz.voho.common.exception.InitializationException;
import cz.voho.common.utility.WikiLinkUtility;
import cz.voho.wiki.model.WikiContext;
import cz.voho.wiki.model.WikiPageSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.regex.Pattern;

public class DefaultWikiPageSourceRepository implements WikiPageSourceRepository {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultWikiPageSourceRepository.class);

    private final Cache<String, WikiPageSource> cache;
    private final WikiContext wikiContext;

    public DefaultWikiPageSourceRepository(final WikiContext wikiContext) {
        cache = CacheBuilder
                .newBuilder()
                .build();

        this.wikiContext = wikiContext;

        try {
            ClassPath
                    .from(Thread.currentThread().getContextClassLoader())
                    .getResources()
                    .stream()
                    .map(ClassPath.ResourceInfo::getResourceName)
                    .filter(this::isNameOfWikiResource)
                    .map(WikiLinkUtility::stripSlashesAndWikiPrefix)
                    .forEach(this::precacheResource);
        } catch (IOException e) {
            throw new InitializationException("Error while loading wiki page sources.", e);
        } finally {
            LOG.info("Loaded pages: {}", cache.size());
        }
    }

    @Override
    public WikiPageSource getWikiPageSourceById(final String wikiPageId) {
        final WikiPageSource value = cache.getIfPresent(wikiPageId);

        if (value == null) {
            throw new ContentNotFoundException(wikiPageId);
        }

        return value;
    }

    private void precacheResource(final String resourceName) {
        LOG.info("Processing resource: {}", resourceName);

        try {
            final WikiPageSource page = new WikiPageSource();
            page.setId(extractPath(resourceName));
            page.setParentId(extractParent(resourceName));
            page.setSource(loadSource(resourceName) + "\r\n\r\n");
            page.setGithubUrl(getRepositoryPath(resourceName));
            page.setGithubRawUrl(getRawRepositoryPath(resourceName));
            page.setOrigin(resolveOrigin(resourceName));
            page.setUpdated(getLastUpdated(resourceName));
            cache.put(page.getId(), page);
            wikiContext.addPage(page.getId());
            if (page.getParentId() != null) {
                wikiContext.setParentPage(page.getId(), page.getParentId());
            }
            LOG.info("Cache updated with node: {}", page);
        } catch (Exception e) {
            LOG.error("Cannot process resource: " + resourceName);
        }
    }

    private boolean isNameOfWikiResource(final String resourceName) {
        return resourceName.matches("wiki/(.+)\\.md");
    }

    private String resolveOrigin(final String resourceName) {
        return "LOCAL " + resourceName + " @ " + LocalDateTime.now().toString();
    }

    private String getRepositoryPath(final String resourceName) {
        return String.format("https://github.com/voho/web/blob/master/website/src/main/resources/wiki/%s", resourceName);
    }

    private String getRawRepositoryPath(final String resourceName) {
        return String.format("https://gitcdn.link/repo/voho/web/master/website/src/main/resources/wiki/%s", resourceName);
    }

    private String extractPath(final String resourceName) {
        final String[] exploded = explode(resourceName);
        return exploded[exploded.length - 1];
    }

    private String extractParent(final String resourceName) {
        final String[] exploded = explode(resourceName);
        if (exploded.length > 1) {
            return exploded[exploded.length - 2];
        }
        return null;
    }

    private String loadSource(final String resourceName) throws IOException {
        LOG.info("Loading from local resource: {}", resourceName);
        final URL resource = Resources.getResource("wiki/" + resourceName);
        return Resources.toString(resource, StandardCharsets.UTF_8);
    }

    private LocalDateTime getLastUpdated(final String resourceName) {
        try {
            final URL resource = Resources.getResource("wiki/" + resourceName);
            final Path path = Paths.get(resource.toURI());
            final FileTime time = Files.getLastModifiedTime(path);
            return time.toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime();
        } catch (Exception e) {
            LOG.warn("Unable to get file modification time.", e);
            return LocalDateTime.MIN;
        }
    }

    private String[] explode(final String resourceName) {
        final String[] result = resourceName.split(Pattern.quote("/"));

        if (result.length > 0) {
            final String lastResult = result[result.length - 1];
            final int split = lastResult.lastIndexOf('.');
            if (split != -1) {
                result[result.length - 1] = lastResult.substring(0, split);
            }
        }

        return result;
    }
}
