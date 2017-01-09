package cz.voho.servlet;

import cz.voho.wiki.WikiBackend;
import freemarker.template.SimpleHash;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class MetaPageServlet extends AbstractMenuPageServlet {
    private final WikiBackend wikiBackend = WikiBackend.SINGLETON;

    @Override
    protected String requestUrlToTemplatePath(final HttpServletRequest request) throws ServletException {
        return "meta.ftl";
    }

    @Override
    protected void updateModel(final HttpServletRequest request, final SimpleHash model) {
        super.updateModel(request, model);

        model.put("quotes", wikiBackend.getCurrentContext().getQuotes());
        model.put("todos", wikiBackend.getCurrentContext().getTodoPages());
        model.put("missing", wikiBackend.getCurrentContext().getMissingPages());
    }
}
