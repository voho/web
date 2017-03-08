package cz.voho.filter;

import org.apache.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TrailingSlashAppendingFilter extends AbstractRedirectingFilter {
    @Override
    protected boolean doCustomFilter(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
        final boolean isAsset = httpServletRequest.getRequestURI().contains("assets/");
        final boolean isGeneratedAsset = httpServletRequest.getRequestURI().contains("generate/");
        final boolean slashTerminated = httpServletRequest.getRequestURI().endsWith("/");

        if (!(isAsset || isGeneratedAsset) && !slashTerminated) {
            redirect(httpServletResponse, HttpStatus.SC_MOVED_PERMANENTLY, httpServletRequest.getRequestURI() + "/");
            return false;
        }

        return true;
    }
}
