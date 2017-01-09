package cz.voho.servlet;

import cz.voho.utility.WikiLinkUtility;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DispatcherServlet extends HttpServlet {
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
        } else {
            super.doGet(request, response);
        }
    }

    private boolean processWikiRedirect(final HttpServletRequest request, final HttpServletResponse response) {
        final String requestUri = request.getRequestURI();
        // TODO add redirect on missing pages or something

        if (!requestUri.endsWith("/")) {
            try {
                response.setStatus(302);
                response.sendRedirect(requestUri + "/");
                return true;
            } catch (IOException e) {
                throw new IllegalStateException("Cannot redirect: " + requestUri, e);
            }
        }

        return false;
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
