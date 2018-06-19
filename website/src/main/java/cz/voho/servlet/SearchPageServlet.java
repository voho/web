package cz.voho.servlet;

import com.google.common.base.Strings;
import cz.voho.common.model.enrich.MetaDataRoot;
import cz.voho.common.utility.WikiLinkUtility;
import cz.voho.facade.Backend;
import cz.voho.facade.WikiBackend;
import cz.voho.wiki.model.WikiPageSearchResult;
import freemarker.template.SimpleHash;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

// TODO improve code
public class SearchPageServlet extends AbstractPageServlet {
    private final WikiBackend wikiBackend = Backend.SINGLETON.getWikiBackend();

    @Override
    protected String requestUrlToTemplatePath(final HttpServletRequest request) throws ServletException {
        return "search.ftl";
    }

    @Override
    protected void updateModel(final HttpServletRequest request, final SimpleHash model, final MetaDataRoot metaDataRoot) {
        super.updateModel(request, model, metaDataRoot);

        String q = resolveQuery(request);

        model.put("query", q);

        if (!Strings.isNullOrEmpty(q)) {
            try {
                List<WikiPageSearchResult> results = wikiBackend.search(q, 20);
                model.put("results", results);
            } catch (Exception e) {
                model.put("error", e.getMessage());
            }
        }
    }

    private String resolveQuery(HttpServletRequest request) {
        String query = WikiLinkUtility.stripSlashes(request.getRequestURI());

        if (query.equalsIgnoreCase("search")) {
            return Strings.emptyToNull(request.getParameter("query"));
        } else if (query.startsWith("search/")) {
            return Strings.emptyToNull(query.substring("search/".length()));
        }

        return null;
    }
}
