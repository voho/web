package cz.voho.filter;

import org.apache.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TrailingSlashAppendingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // NOP
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            boolean isAsset = httpServletRequest.getRequestURI().contains("assets/");
            boolean isGeneratedAsset = httpServletRequest.getRequestURI().contains("generate/");
            boolean slashTerminated = httpServletRequest.getRequestURI().endsWith("/");

            if (!(isAsset || isGeneratedAsset) && !slashTerminated) {
                redirect(httpServletResponse, HttpStatus.SC_MOVED_PERMANENTLY, httpServletRequest.getRequestURI() + "/");
                return;
            }
        }
        // continue
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        // NOP
    }

    private boolean redirect(final HttpServletResponse response, final int statusCode, final String newUrl) {
        try {
            response.setStatus(statusCode);
            response.sendRedirect(newUrl);
            return true;
        } catch (IOException e) {
            throw new IllegalStateException("Cannot redirect to: " + newUrl, e);
        }
    }
}
