package cz.voho.wiki.page.source;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.io.Resources;
import com.google.common.net.UrlEscapers;
import com.google.common.reflect.ClassPath;
import cz.voho.common.exception.ContentNotFoundException;
import cz.voho.common.exception.InitializationException;
import cz.voho.common.utility.WikiLinkUtility;
import cz.voho.wiki.model.WikiPageSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;

public class DefaultWikiPageSourceRepository implements WikiPageSourceRepository {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultWikiPageSourceRepository.class);

    private final Map<String, WikiPageSource> wikiPageIdToResourceName;

    public DefaultWikiPageSourceRepository() {
        this.wikiPageIdToResourceName = new TreeMap<>();

        try {
            ClassPath
                    .from(Thread.currentThread().getContextClassLoader())
                    .getResources()
                    .stream()
                    .map(ClassPath.ResourceInfo::getResourceName)
                    .filter(this::isNameOfWikiResource)
                    .map(WikiLinkUtility::stripSlashes)
                    .map(WikiLinkUtility::stripWikiPrefix)
                    .map(this::load)
                    .forEach(wikiPageSource -> wikiPageIdToResourceName.put(wikiPageSource.getId(), wikiPageSource));
        } catch (IOException e) {
            throw new InitializationException("Error while loading wiki page sources.", e);
        }
    }

    @Override
    public ImmutableSet<String> getWikiPageIds() {
        return ImmutableSet.copyOf(wikiPageIdToResourceName.keySet());
    }

    @Override
    public WikiPageSource getWikiPageSourceById(final String wikiPageId) {
        return wikiPageIdToResourceName.get(wikiPageId); // TODO
    }

    private WikiPageSource load(String resourceName) {
        WikiPageSource page = new WikiPageSource();
        page.setId(extractId(resourceName));
        page.setParentId(extractParentId(resourceName));
        page.setSource(loadSource(resourceName) + "\r\n\r\n");
        page.setGithubUrl(getRepositoryPath(resourceName));
        page.setGithubRawUrl(getRawRepositoryPath(resourceName));
        page.setGithubIssueUrl(getIssueRepositoryPath(resourceName));
        return page;
    }

    private boolean isNameOfWikiResource(final String resourceName) {
        return resourceName.matches("wiki/(.+)\\.md");
    }

    private String extractId(final String resourceName) {
        final String[] exploded = explodeResourceName(resourceName);
        Preconditions.checkState(exploded.length > 0);
        return exploded[exploded.length - 1];
    }

    private String extractParentId(final String resourceName) {
        final String[] exploded = explodeResourceName(resourceName);
        if (exploded.length > 1) {
            return exploded[exploded.length - 2];
        }
        return null;
    }

    private String loadSource(final String resourceName) {
        LOG.info("Loading from local resource: {}", resourceName);
        final URL resource = Resources.getResource("wiki/" + resourceName);

        try {
            return Resources.toString(resource, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new ContentNotFoundException("Wiki page not found: " + resourceName);
        }
    }

    private String getRepositoryPath(final String resourceName) {
        return String.format("https://github.com/voho/web/blob/master/website/src/main/resources/wiki/%s", resourceName);
    }

    private String getRawRepositoryPath(final String resourceName) {
        return String.format("https://gitcdn.link/repo/voho/web/master/website/src/main/resources/wiki/%s", resourceName);
    }

    private String getIssueRepositoryPath(String resourceName) {
        return String.format(
                "https://github.com/voho/web/issues/new?title=%s&body=%s",
                UrlEscapers.urlFragmentEscaper().escape(String.format("%s (chyba na Wiki)", resourceName)),
                UrlEscapers.urlFragmentEscaper().escape("")
        );
    }

    private String[] explodeResourceName(final String resourceName) {
        final String[] result = WikiLinkUtility.splitWikiParts(resourceName);

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
