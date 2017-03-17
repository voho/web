package cz.voho.servlet.filter;

import org.apache.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TrailingSlashAppendingFilter extends AbstractRedirectingFilter {
    @Override
    protected boolean doCustomFilter(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
        String uri = httpServletRequest.getRequestURI();

        final boolean isAsset = uri.contains("assets/");
        final boolean isGeneratedAsset = uri.contains("generate/");
        final boolean isSlashTerminated = uri.endsWith("/");

        if (!(isAsset || isGeneratedAsset) && !isSlashTerminated) {
            redirect(httpServletResponse, HttpStatus.SC_MOVED_PERMANENTLY, uri + "/");
            return false;
        }

        return true;
    }
}
