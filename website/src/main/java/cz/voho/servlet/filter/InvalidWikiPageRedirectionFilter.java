package cz.voho.servlet.filter;

import cz.voho.common.utility.WikiLinkUtility;
import cz.voho.facade.Backend;
import cz.voho.facade.WikiBackend;
import org.apache.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InvalidWikiPageRedirectionFilter extends AbstractRedirectingFilter {
    private final WikiBackend wikiBackend = Backend.SINGLETON.getWikiBackend();

    @Override
    protected boolean doCustomFilter(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
        final String wikiPageId = WikiLinkUtility.resolveWikiPageId(httpServletRequest.getRequestURI());
        final String fixedWikiPageId = wikiBackend.resolveWikiPageId(wikiPageId);

        if (!wikiPageId.equals(fixedWikiPageId)) {
            redirect(httpServletResponse, HttpStatus.SC_MOVED_PERMANENTLY, "/wiki/" + fixedWikiPageId + "/");
            return false;
        }

        return true;
    }
}
