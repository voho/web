package cz.voho.filter;

import cz.voho.utility.WikiLinkUtility;
import cz.voho.wiki.WikiBackend;
import org.apache.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InvalidWikiPageRedirectionFilter extends AbstractRedirectingFilter {
    private final WikiBackend wikiBackend = WikiBackend.SINGLETON;

    @Override
    protected boolean doCustomFilter(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
        final String wikiPageId = WikiLinkUtility.stripSlashesAndWikiPrefix(httpServletRequest.getRequestURI());
        final String fixedWikiPageId = wikiBackend.resolveWikiPageId(wikiPageId);

        if (!wikiPageId.equals(fixedWikiPageId)) {
            redirect(httpServletResponse, HttpStatus.SC_MOVED_PERMANENTLY, "/wiki/" + fixedWikiPageId + "/");
            return false;
        }

        return true;
    }
}
