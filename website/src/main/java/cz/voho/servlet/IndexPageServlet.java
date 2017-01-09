package cz.voho.servlet;

import cz.voho.photo.RecentWorkBackend;
import freemarker.template.SimpleHash;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class IndexPageServlet extends AbstractMenuPageServlet {
    private final RecentWorkBackend recentWorkBackend = RecentWorkBackend.SINGLETON;

    @Override
    protected void updateModel(HttpServletRequest request, SimpleHash model) {
        super.updateModel(request, model);

        model.put("instagram_photos", recentWorkBackend.getRecentImages());
        model.put("recent_tracks", recentWorkBackend.getRecentTracks());
    }

    @Override
    protected String requestUrlToTemplatePath(final HttpServletRequest request) throws ServletException {
        return "index.ftl";
    }
}
