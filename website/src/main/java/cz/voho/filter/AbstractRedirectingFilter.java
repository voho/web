package cz.voho.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractRedirectingFilter implements Filter {
    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse) {
            final HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            final HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

            if (!doCustomFilter(httpServletRequest, httpServletResponse)) {
                // end chain once FALSE is returned
                return;
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    protected abstract boolean doCustomFilter(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        // NOP
    }

    @Override
    public void destroy() {
        // NOP
    }

    protected boolean redirect(final HttpServletResponse response, final int statusCode, final String newUrl) {
        try {
            response.setStatus(statusCode);
            response.sendRedirect(newUrl);
            return true;
        } catch (IOException e) {
            throw new IllegalStateException("Cannot redirect to: " + newUrl, e);
        }
    }
}
