package cz.voho.servlet;

import cz.voho.common.model.enrich.*;
import cz.voho.common.utility.Constants;
import cz.voho.common.utility.WikiLinkUtility;
import cz.voho.facade.Backend;
import cz.voho.facade.RecentBackend;
import cz.voho.facade.WikiBackend;
import cz.voho.wiki.model.ParsedWikiPage;
import cz.voho.wiki.model.WikiPageReference;
import cz.voho.wiki.model.WikiPageReferences;
import cz.voho.wiki.repository.source.WikiPageSourceRepository;
import freemarker.template.SimpleHash;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.atomic.AtomicInteger;

// TODO improve code
public class WikiPageServlet extends AbstractPageServlet {
    private final WikiBackend wikiBackend = Backend.SINGLETON.getWikiBackend();
    private final RecentBackend recentBackend = Backend.SINGLETON.getRecentBackend();

    @Override
    protected String requestUrlToTemplatePath(final HttpServletRequest request) throws ServletException {
        return "wiki.ftl";
    }

    @Override
    protected void updateModel(final HttpServletRequest request, final SimpleHash model, final MetaDataRoot metaDataRoot) {
        super.updateModel(request, model, metaDataRoot);

        final ParsedWikiPage parsedWikiPage = wikiBackend.load(WikiLinkUtility.resolveWikiPageId(request.getRequestURI()));

        model.put("active_wiki_page_id", parsedWikiPage.getSource().getId());
        model.put("active_wiki_page_parent_id", parsedWikiPage.getSource().getParentId());
        model.put("active_wiki_page_external_url", wikiBackend.getExternalWikiPageLink(parsedWikiPage.getSource().getId()));
        model.put("active_wiki_page_github_url", parsedWikiPage.getSource().getGithubUrl());
        model.put("active_wiki_page_github_raw_url", parsedWikiPage.getSource().getGithubRawUrl());
        model.put("active_wiki_page_title", parsedWikiPage.getTitle());
        model.put("active_wiki_page_content", parsedWikiPage.getHtml());
        model.put("active_wiki_page_cover", parsedWikiPage.isCover());
        model.put("active_wiki_page_toc", wikiBackend.getCurrentContext().getNonTrivialToc(parsedWikiPage.getSource().getId()));
        model.put("active_wiki_page_report_issue", parsedWikiPage.getSource().getGithubIssueUrl());
        model.put("active_wiki_page_outgoing_links", wikiBackend.getLinksFromHere(parsedWikiPage.getSource().getId()));
        model.put("active_wiki_page_incoming_links", wikiBackend.getLinksToHere(parsedWikiPage.getSource().getId()));
        model.put("indexSubPages", wikiBackend.getSubPages(WikiPageSourceRepository.INDEX_PAGE_ID));
        model.put("recentlyChangedPages", wikiBackend.enrichCommits(recentBackend.getRecentWikiChanges()));

        WikiPageReferences siblingPages = null;

        if (parsedWikiPage.getSource().getParentId() != null) {
            siblingPages = wikiBackend.getSubPages(parsedWikiPage.getSource().getParentId());
        }

        final WikiPageReferences subPages = wikiBackend.getSubPages(parsedWikiPage.getSource().getId());
        updateModelWithSubAndSiblingPages(model, metaDataRoot, parsedWikiPage, subPages, siblingPages);

        final WikiPageReferences breadCrumbs = wikiBackend.getBreadCrumbs(parsedWikiPage.getSource().getId());
        updateModelWithBreadCrumbs(model, metaDataRoot, breadCrumbs);
    }

    private void updateModelWithSubAndSiblingPages(SimpleHash model, MetaDataRoot metaDataRoot, ParsedWikiPage parsedWikiPage, WikiPageReferences subPages, WikiPageReferences siblingPages) {
        if (subPages != null && subPages.getItems() != null) {
            if (!subPages.getItems().isEmpty()) {
                model.put("subPages", subPages);

                metaDataRoot.setArticles(subPages
                        .getItems()
                        .stream()
                        .map(a -> {
                            Article article = new Article();
                            article.setUrl(wikiBackend.getExternalWikiPageLink(a.getId()));
                            article.setName(a.getTitle());
                            article.setAuthor(Constants.NAME_WITH_ALIAS);
                            return article;
                        })
                        .toArray(Article[]::new));

                if (subPages.getItems().stream().allMatch(WikiPageReference::isLeaf)) {
                    // if all sub-pages are leaves, we can just display them as siblings
                    siblingPages = subPages;
                    model.put("subPages", null);
                }
            }
        }

        if (siblingPages != null && siblingPages.getItems() != null) {
            if (!siblingPages.getItems().isEmpty()) {
                siblingPages.getItems().removeIf(a -> a.getId().equals(parsedWikiPage.getSource().getId()));

                model.put("siblingPages", siblingPages);
            }
        }
    }

    private void updateModelWithBreadCrumbs(SimpleHash model, MetaDataRoot metaDataRoot, WikiPageReferences breadCrumbs) {
        if (breadCrumbs != null && breadCrumbs.getItems() != null) {
            if (!breadCrumbs.getItems().isEmpty()) {
                model.put("breadCrumbs", breadCrumbs);

                AtomicInteger i = new AtomicInteger(1);

                ListItem[] breadcrumbListElements = breadCrumbs.getItems()
                        .stream()
                        .map(a -> {
                            Item item = new Item();
                            item.setName(a.getTitle());
                            item.setUrl(wikiBackend.getExternalWikiPageLink(a.getId()));
                            ListItem listItem = new ListItem();
                            listItem.setPosition(i.getAndIncrement());
                            listItem.setItem(item);
                            return listItem;
                        })
                        .toArray(ListItem[]::new);

                BreadcrumbList breadcrumbList = new BreadcrumbList();
                breadcrumbList.setItemListElement(breadcrumbListElements);
                metaDataRoot.setBreadcrumbs(breadcrumbList);
            }
        }
    }
}
