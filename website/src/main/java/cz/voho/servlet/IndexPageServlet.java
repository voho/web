package cz.voho.servlet;

import cz.voho.enrich.MetaDataRoot;
import cz.voho.work.RecentWorkBackend;
import freemarker.template.SimpleHash;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class IndexPageServlet extends AbstractMenuPageServlet {
    private final RecentWorkBackend recentWorkBackend = RecentWorkBackend.SINGLETON;

    @Override
    protected void updateModel(HttpServletRequest request, SimpleHash model, final MetaDataRoot metaDataRoot) {
        super.updateModel(request, model, metaDataRoot);

        model.put("recent_photos", recentWorkBackend.getRecentPhotos());
        model.put("recent_tracks", recentWorkBackend.getRecentTracks());
    }

    @Override
    protected String requestUrlToTemplatePath(final HttpServletRequest request) throws ServletException {
        return "index.ftl";
    }
}
