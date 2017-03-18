package cz.voho.wiki.repository.source;

import com.google.common.collect.ImmutableSet;
import com.google.common.io.Resources;
import com.google.common.net.UrlEscapers;
import com.google.common.reflect.ClassPath;
import cz.voho.common.exception.ContentNotFoundException;
import cz.voho.common.exception.InitializationException;
import cz.voho.common.utility.WikiLinkUtility;
import cz.voho.wiki.model.WikiPageSource;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;

public class DefaultWikiPageSourceRepository implements WikiPageSourceRepository {
    private final Map<String, WikiPageSource> wikiPageIdToResourceName;

    public DefaultWikiPageSourceRepository() {
        this.wikiPageIdToResourceName = new TreeMap<>();

        try {
            ClassPath
                    .from(Thread.currentThread().getContextClassLoader())
                    .getResources()
                    .stream()
                    .map(ClassPath.ResourceInfo::getResourceName)
                    .filter(WikiLinkUtility::isValidWikiPageSource)
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

    private String extractId(final String resourceName) {
        return WikiLinkUtility.resolveWikiPageId(resourceName);
    }

    private String extractParentId(final String resourceName) {
        final String[] exploded = WikiLinkUtility.splitWikiParts(resourceName);
        if (exploded.length > 1) {
            return exploded[exploded.length - 2];
        }
        return null;
    }

    private String loadSource(final String resourceName) {
        final URL resource = Resources.getResource(resourceName);

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
}