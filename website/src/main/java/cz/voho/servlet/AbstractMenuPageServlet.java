package cz.voho.servlet;

import cz.voho.common.model.enrich.MetaDataRoot;
import cz.voho.facade.Backend;
import cz.voho.facade.RecentWorkBackend;
import cz.voho.facade.WikiBackend;
import cz.voho.wiki.page.source.WikiPageSourceRepository;
import freemarker.template.SimpleHash;

import javax.servlet.http.HttpServletRequest;

public abstract class AbstractMenuPageServlet extends AbstractPageServlet {
    private final WikiBackend wikiBackend = Backend.SINGLETON.getWikiBackend();
    private final RecentWorkBackend recentWorkBackend = Backend.SINGLETON.getRecentWorkBackend();

    @Override
    protected void updateModel(final HttpServletRequest request, final SimpleHash model, final MetaDataRoot metaDataRoot) {
        super.updateModel(request, model, metaDataRoot);

        model.put("indexSubPages", wikiBackend.getSubPages(WikiPageSourceRepository.INDEX_PAGE_ID));
        model.put("recentlyChangedPages", wikiBackend.enrichCommits(recentWorkBackend.getRecentWikiChanges()));
    }
}
