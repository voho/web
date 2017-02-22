package cz.voho.servlet;

import com.google.common.net.UrlEscapers;
import cz.voho.enrich.*;
import cz.voho.utility.Constants;
import cz.voho.utility.WikiLinkUtility;
import cz.voho.wiki.WikiBackend;
import cz.voho.wiki.model.ParsedWikiPage;
import cz.voho.wiki.model.WikiPageReference;
import cz.voho.wiki.model.WikiPageReferences;
import cz.voho.wiki.page.source.WikiPageSourceRepository;
import freemarker.template.SimpleHash;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class WikiPageServlet extends AbstractMenuPageServlet {
    private final WikiBackend wikiBackend = WikiBackend.SINGLETON;

    @Override
    protected String requestUrlToTemplatePath(final HttpServletRequest request) throws ServletException {
        return "wiki.ftl";
    }

    @Override
    protected void updateModel(final HttpServletRequest request, final SimpleHash model, final MetaDataRoot metaDataRoot) {
        super.updateModel(request, model, metaDataRoot);

        final ParsedWikiPage parsedWikiPage = wikiBackend.load(resolvePageName(request));
        final String externalUrl = wikiBackend.getExternalWikiPageLink(parsedWikiPage.getSource().getId());

        model.put("active_wiki_page_id", parsedWikiPage.getSource().getId());
        model.put("active_wiki_page_parent_id", parsedWikiPage.getSource().getParentId());
        model.put("active_wiki_page_external_url", externalUrl);
        model.put("active_wiki_page_github_url", parsedWikiPage.getSource().getGithubUrl());
        model.put("active_wiki_page_github_raw_url", parsedWikiPage.getSource().getGithubRawUrl());
        model.put("active_wiki_page_title", parsedWikiPage.getTitle());
        model.put("active_wiki_page_content", parsedWikiPage.getHtml());
        model.put("active_wiki_page_cover", parsedWikiPage.isCover());
        model.put("active_wiki_page_origin", parsedWikiPage.getSource().getOrigin());
        model.put("active_wiki_page_toc", wikiBackend.getCurrentContext().getNonTrivialToc(parsedWikiPage.getSource().getId()));
        model.put("active_wiki_page_report_issue", createReportWikiIssueLink(parsedWikiPage));
        model.put("active_wiki_page_outgoing_links", wikiBackend.getLinksFromHere(parsedWikiPage.getSource().getId()));
        model.put("active_wiki_page_incoming_links", wikiBackend.getLinksToHere(parsedWikiPage.getSource().getId()));

        final WikiPageReferences subPages = wikiBackend.getSubPages(parsedWikiPage.getSource().getId());
        final WikiPageReferences breadCrumbs = wikiBackend.getBreadCrumbs(parsedWikiPage.getSource().getId());
        WikiPageReferences siblingPages = null;

        if (parsedWikiPage.getSource().getParentId() != null) {
            siblingPages = wikiBackend.getSubPages(parsedWikiPage.getSource().getParentId());
        }

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
                    // if all subpages are leaves, we can just display them as siblings
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

    private String createReportWikiIssueLink(final ParsedWikiPage parsedWikiPage) {
        final String title = String.format("%s (chyba na Wiki)", parsedWikiPage.getSource().getId());
        final String body = "";
        return String.format(
                "https://github.com/voho/web/issues/new?title=%s&body=%s",
                UrlEscapers.urlFragmentEscaper().escape(title),
                UrlEscapers.urlFragmentEscaper().escape(body)
        );
    }

    private String resolvePageName(final HttpServletRequest request) {
        String path = WikiLinkUtility.copyValidChars(request.getPathInfo());
        if (path.startsWith("wiki")) {
            path = path.substring("wiki".length());
        }
        path = WikiLinkUtility.stripSlashes(path);
        if (path.isEmpty()) {
            path = WikiPageSourceRepository.INDEX_PAGE_ID;
        }
        path = path.toLowerCase(Locale.ROOT);
        return path;
    }
}
