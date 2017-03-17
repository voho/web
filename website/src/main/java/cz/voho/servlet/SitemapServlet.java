package cz.voho.servlet;

import com.google.common.collect.Sets;
import cz.voho.common.utility.Constants;
import cz.voho.facade.Backend;
import cz.voho.facade.WikiBackend;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

public class SitemapServlet extends HttpServlet {
    private final WikiBackend wikiBackend = Backend.SINGLETON.getWikiBackend();

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");

        final Set<String> urls = Sets.newTreeSet();

        urls.add("meta");
        urls.add("");

        wikiBackend.getWikiPageIds().forEach(wikiPageId -> {
            urls.add("wiki/" + wikiPageId + "/");
        });

        try (PrintWriter writer = resp.getWriter()) {
            urls.forEach(relativeUrl -> {
                writer.println(Constants.WEBSITE_URL_WITH_SLASH + relativeUrl);
            });
        }
    }
}
