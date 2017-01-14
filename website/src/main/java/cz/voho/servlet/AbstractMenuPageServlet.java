package cz.voho.servlet;

import cz.voho.enrich.MetaDataRoot;
import cz.voho.wiki.WikiBackend;
import cz.voho.wiki.page.source.WikiPageSourceRepository;
import freemarker.template.SimpleHash;

import javax.servlet.http.HttpServletRequest;

public abstract class AbstractMenuPageServlet extends AbstractPageServlet {
    private final WikiBackend wikiBackend = WikiBackend.SINGLETON;

    @Override
    protected void updateModel(final HttpServletRequest request, final SimpleHash model, final MetaDataRoot metaDataRoot) {
        super.updateModel(request, model, metaDataRoot);

        model.put("indexSubPages", wikiBackend.getSubPages(WikiPageSourceRepository.INDEX_PAGE_ID));
    }
}
