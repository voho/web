package cz.voho.servlet;

import cz.voho.wiki.WikiBackend;
import freemarker.template.SimpleHash;

import javax.servlet.http.HttpServletRequest;

public abstract class AbstractMenuPageServlet extends AbstractPageServlet {
    private final WikiBackend wikiBackend = WikiBackend.SINGLETON;

    @Override
    protected void updateModel(final HttpServletRequest request, final SimpleHash model) {
        super.updateModel(request, model);

        model.put("indexSubPages", wikiBackend.getSubPages("index"));
    }
}
