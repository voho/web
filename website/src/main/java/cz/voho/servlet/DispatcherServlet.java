package cz.voho.servlet;

import cz.voho.utility.Constants;
import cz.voho.utility.WikiLinkUtility;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.regex.Pattern;

public class DispatcherServlet extends HttpServlet {
    private static final int HTTP_CODE_SEE_OTHER = 303;
    private static final int HTTP_CODE_MOVED_PERMANENTLY = 301;

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        if (isStatic(request)) {
            forwardToAnotherServlet(request, response, "default");
        } else if (isGenerate(request)) {
            forwardToAnotherServlet(request, response, "generate");
        } else if (isWiki(request)) {
            if (!processWikiRedirect(request, response)) {
                forwardToAnotherServlet(request, response, "wiki");
            }
        } else if (isIndex(request)) {
            forwardToAnotherServlet(request, response, "index");
        } else if (isMeta(request)) {
            forwardToAnotherServlet(request, response, "meta");
        } else if (isSitemap(request)) {
            forwardToAnotherServlet(request, response, "sitemap");
        } else {
            if (!processLegacyRedirect(request, response)) {
                super.doGet(request, response);
            }
        }
    }

    private boolean processLegacyRedirect(final HttpServletRequest request, final HttpServletResponse response) {
        final String requestUri = request.getRequestURI();

        if (!requestUri.endsWith("/")) {
            return redirect(response, HTTP_CODE_MOVED_PERMANENTLY, requestUri + "/");
        }

        final String[] parts = WikiLinkUtility.stripSlashes(requestUri).split(Pattern.quote("/"));
        final String firstPart = parts[0];

        if (firstPart.equals("cv") || firstPart.equals("zivotopis")) {
            return redirect(response, HTTP_CODE_SEE_OTHER, Constants.PROFILE_LINKED_IN);
        } else if (firstPart.equals("hudba")) {
            return redirect(response, HTTP_CODE_SEE_OTHER, Constants.PROFILE_SOUNDCLOUD);
        } else if (firstPart.equals("blog") || firstPart.startsWith("blog/")) {
            return redirect(response, HTTP_CODE_SEE_OTHER, Constants.PROFILE_TWITTER);
        } else {
            return false;
        }
    }

    private boolean processWikiRedirect(final HttpServletRequest request, final HttpServletResponse response) {
        final String requestUri = request.getRequestURI();

        if (!requestUri.endsWith("/")) {
            return redirect(response, HTTP_CODE_MOVED_PERMANENTLY, requestUri + "/");
        }

        final String[] parts = WikiLinkUtility.resolveWikiPageId(requestUri).split(Pattern.quote("/"));

        if (parts.length > 1) {
            final String lastWikiPageId = parts[parts.length - 1];
            final String correctWikiPageUrl = String.format("/wiki/%s/", lastWikiPageId);
            return redirect(response, HTTP_CODE_MOVED_PERMANENTLY, correctWikiPageUrl);
        }

        return false;
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

    private void forwardToAnotherServlet(final HttpServletRequest request, final HttpServletResponse response, final String servletName) throws ServletException, IOException {
        final RequestDispatcher rd = getServletContext().getNamedDispatcher(servletName);
        rd.forward(request, response);
    }

    private boolean isIndex(final HttpServletRequest request) {
        final String ext = getPath(request);

        return ext.equals("");
    }

    private boolean isWiki(final HttpServletRequest request) {
        final String ext = getPath(request);

        return ext.equals("wiki") || ext.startsWith("wiki/");
    }

    private boolean isMeta(final HttpServletRequest request) {
        final String ext = getPath(request);

        return ext.equals("meta");
    }

    private boolean isSitemap(final HttpServletRequest request) {
        final String ext = getPath(request);

        return ext.equals("sitemap");
    }

    private boolean isGenerate(final HttpServletRequest request) {
        final String ext = getPath(request);

        return ext.startsWith("generate/");
    }

    private boolean isStatic(final HttpServletRequest request) {
        final String ext = getPath(request);

        if (ext.startsWith("assets/")) {
            return true;
        }

        return false;
    }

    private String getPath(final HttpServletRequest request) {
        String url = request.getRequestURI();
        url = WikiLinkUtility.stripSlashes(url);
        return url;
    }
}
