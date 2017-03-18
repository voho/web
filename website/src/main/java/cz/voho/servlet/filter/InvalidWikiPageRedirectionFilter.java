package cz.voho.servlet.filter;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import com.google.common.net.UrlEscapers;
import cz.voho.common.utility.Constants;
import cz.voho.common.utility.WikiLinkUtility;
import cz.voho.facade.Backend;
import cz.voho.facade.WikiBackend;
import org.apache.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

public class InvalidWikiPageRedirectionFilter extends AbstractRedirectingFilter {
    private final WikiBackend wikiBackend = Backend.SINGLETON.getWikiBackend();

    @Override
    protected boolean doCustomFilter(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
        String originalQuery = httpServletRequest.getRequestURI();
        String wikiPageId = WikiLinkUtility.resolveWikiPageId(originalQuery);

        if (wikiBackend.getCurrentContext().exists(wikiPageId)) {
            return true;
        }

        wikiPageId = resolveWikiPageId(wikiPageId);

        if (wikiPageId == null) {
            String splitQuery = Joiner.on(" ").join(WikiLinkUtility.splitWikiParts(originalQuery));
            String searchQuery = UrlEscapers.urlFragmentEscaper().escape(splitQuery);
            redirect(httpServletResponse, HttpStatus.SC_MOVED_TEMPORARILY, String.format(Constants.GOOGLE_SITE_SEARCH, searchQuery));
            return false;
        }

        redirect(httpServletResponse, HttpStatus.SC_MOVED_PERMANENTLY, "/wiki/" + wikiPageId + "/");
        return false;
    }

    private String resolveWikiPageId(final String invalidWikiPageId) {
        final Set<String> candidates = Sets.newTreeSet();

        for (final String wikiPageId : wikiBackend.getWikiPageIds()) {
            final boolean containsAsPrefix = wikiPageId.contains(invalidWikiPageId + "-") || invalidWikiPageId.contains(wikiPageId + "-");
            final boolean containsAsSuffix = wikiPageId.contains("-" + invalidWikiPageId) || invalidWikiPageId.contains("-" + wikiPageId);

            if (containsAsPrefix || containsAsSuffix) {
                candidates.add(wikiPageId);
            }
        }

        if (candidates.size() == 1) {
            return candidates.iterator().next();
        }

        return null;
    }
}
