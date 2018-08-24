package cz.voho.servlet;

import cz.voho.common.exception.ContentNotFoundException;
import cz.voho.common.model.enrich.Article;
import cz.voho.common.model.enrich.BreadcrumbList;
import cz.voho.common.model.enrich.Item;
import cz.voho.common.model.enrich.ListItem;
import cz.voho.common.model.enrich.MetaDataRoot;
import cz.voho.common.utility.WebsiteConstants;
import cz.voho.common.utility.WikiLinkUtility;
import cz.voho.facade.Backend;
import cz.voho.facade.WikiBackend;
import cz.voho.wiki.model.ParsedWikiPage;
import cz.voho.wiki.model.WikiPageReference;
import cz.voho.wiki.model.WikiPageReferences;
import freemarker.template.SimpleHash;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.atomic.AtomicInteger;

import static cz.voho.wiki.repository.page.WikiPageSourceRepository.MISSING_PAGE_ID;

// TODO improve code
public class WikiPageServlet extends AbstractPageServlet {
    private final WikiBackend wikiBackend = Backend.SINGLETON.getWiki();

    @Override
    protected String requestUrlToTemplatePath(final HttpServletRequest request) throws ServletException {
        return "wiki.ftl";
    }

    @Override
    protected void updateModel(final HttpServletRequest request, final SimpleHash model, final MetaDataRoot metaDataRoot) {
        super.updateModel(request, model, metaDataRoot);

        final ParsedWikiPage parsedWikiPage = loadPageWithFallback(WikiLinkUtility.resolveWikiPageId(request.getRequestURI()));

        model.put("active_wiki_page_id", parsedWikiPage.getSource().getId());
        model.put("active_wiki_page_parent_id", parsedWikiPage.getSource().getParentId());
        model.put("active_wiki_page_external_url", wikiBackend.getExternalWikiPageLink(parsedWikiPage.getSource().getId()));
        model.put("active_wiki_page_github_url", parsedWikiPage.getSource().getGithubUrl());
        model.put("active_wiki_page_history_url", parsedWikiPage.getSource().getGithubHistoryUrl());
        model.put("active_wiki_page_github_raw_url", parsedWikiPage.getSource().getGithubRawUrl());
        model.put("active_wiki_page_title", parsedWikiPage.getTitle());
        model.put("active_wiki_page_content", parsedWikiPage.getHtml());
        model.put("active_wiki_page_cover", parsedWikiPage.isCover());
        model.put("active_wiki_page_todo", parsedWikiPage.isTodo());
        model.put("active_wiki_page_toc", wikiBackend.getNonTrivialToc(parsedWikiPage.getSource().getId()));
        model.put("active_wiki_page_report_issue", parsedWikiPage.getSource().getGithubIssueUrl());
        model.put("active_wiki_page_outgoing_links", wikiBackend.getLinksFromHere(parsedWikiPage.getSource().getId()));
        model.put("active_wiki_page_incoming_links", wikiBackend.getLinksToHere(parsedWikiPage.getSource().getId()));
        model.put("indexSubPages", wikiBackend.getWikiIndexSubPages());

        WikiPageReferences siblingPages = null;

        if (parsedWikiPage.getSource().getParentId() != null) {
            siblingPages = wikiBackend.getSubPages(parsedWikiPage.getSource().getParentId());
        }

        final WikiPageReferences subPages = wikiBackend.getSubPages(parsedWikiPage.getSource().getId());
        updateModelWithSubAndSiblingPages(model, metaDataRoot, parsedWikiPage, subPages, siblingPages);

        final WikiPageReferences breadCrumbs = wikiBackend.getBreadCrumbs(parsedWikiPage.getSource().getId());
        updateModelWithBreadCrumbs(model, metaDataRoot, breadCrumbs);
    }

    private ParsedWikiPage loadPageWithFallback(final String wikiPageId) {
        final ParsedWikiPage parsedWikiPage = wikiBackend.load(wikiPageId);

        if (parsedWikiPage != null) {
            return parsedWikiPage;
        }

        if (wikiPageId.equals(MISSING_PAGE_ID)) {
            throw new ContentNotFoundException("No error page found: " + wikiPageId);
        } else {
            return loadPageWithFallback(MISSING_PAGE_ID);
        }
    }

    private void updateModelWithSubAndSiblingPages(final SimpleHash model,
                                                   final MetaDataRoot metaDataRoot,
                                                   final ParsedWikiPage parsedWikiPage,
                                                   final WikiPageReferences subPages,
                                                   WikiPageReferences siblingPages
    ) {
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
                            article.setAuthor(WebsiteConstants.NAME_WITH_ALIAS);
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

    private void updateModelWithBreadCrumbs(final SimpleHash model, final MetaDataRoot metaDataRoot, final WikiPageReferences breadCrumbs) {
        if (breadCrumbs != null && breadCrumbs.getItems() != null) {
            if (!breadCrumbs.getItems().isEmpty()) {
                model.put("breadCrumbs", breadCrumbs);

                final AtomicInteger i = new AtomicInteger(1);

                final ListItem[] breadcrumbListElements = breadCrumbs.getItems()
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

                final BreadcrumbList breadcrumbList = new BreadcrumbList();
                breadcrumbList.setItemListElement(breadcrumbListElements);
                metaDataRoot.setBreadcrumbs(breadcrumbList);
            }
        }
    }
}
