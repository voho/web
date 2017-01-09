package cz.voho.servlet;

import com.google.common.html.HtmlEscapers;
import cz.voho.utility.WikiLinkUtility;
import cz.voho.wiki.WikiBackend;
import cz.voho.wiki.model.ParsedWikiPage;
import cz.voho.wiki.model.WikiPageReferences;
import cz.voho.wiki.page.source.WikiPageSourceRepository;
import freemarker.template.SimpleHash;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class WikiPageServlet extends AbstractMenuPageServlet {
    private final WikiBackend wikiBackend = WikiBackend.SINGLETON;

    @Override
    protected String requestUrlToTemplatePath(final HttpServletRequest request) throws ServletException {
        return "wiki.ftl";
    }

    @Override
    protected void updateModel(final HttpServletRequest request, final SimpleHash model) {
        super.updateModel(request, model);

        final ParsedWikiPage parsedWikiPage = wikiBackend.load(resolvePageName(request));
        model.put("active_wiki_page_id", parsedWikiPage.getSource().getId());
        model.put("active_wiki_page_parent_id", parsedWikiPage.getSource().getParentId());
        model.put("active_wiki_page_external_url", "http://voho.eu/wiki/" + parsedWikiPage.getSource().getId());
        model.put("active_wiki_page_github_url", parsedWikiPage.getSource().getGithubUrl());
        model.put("active_wiki_page_github_raw_url", parsedWikiPage.getSource().getGithubRawUrl());
        model.put("active_wiki_page_title", parsedWikiPage.getTitle());
        model.put("active_wiki_page_content", parsedWikiPage.getHtml());
        model.put("active_wiki_page_cover", parsedWikiPage.isCover());
        model.put("active_wiki_page_origin", parsedWikiPage.getSource().getOrigin());
        model.put("active_wiki_page_report_issue", createReportWikiIssueLink(parsedWikiPage));
        model.put("debug_image_cache_size_items", wikiBackend.getImageCacheSizeInItems());
        model.put("debug_image_cache_size_bytes", wikiBackend.getImageCacheSizeInBytes());

        final WikiPageReferences subPages = wikiBackend.getSubPages(parsedWikiPage.getSource().getId());
        final WikiPageReferences breadCrumbs = wikiBackend.getBreadCrumbs(parsedWikiPage.getSource().getId());
        WikiPageReferences siblingPages = null;

        if (parsedWikiPage.getSource().getParentId() != null) {
            siblingPages = wikiBackend.getSubPages(parsedWikiPage.getSource().getParentId());
        }

        if (subPages != null && subPages.getItems() != null) {
            if (!subPages.getItems().isEmpty()) {
                model.put("subPages", subPages);
            }
        }

        if (siblingPages != null && siblingPages.getItems() != null) {
            siblingPages.getItems().removeIf(a -> a.getId().equals(parsedWikiPage.getSource().getId()));
            if (!siblingPages.getItems().isEmpty()) {
                model.put("siblingPages", siblingPages);
            }
        }

        if (breadCrumbs != null && breadCrumbs.getItems() != null) {
            if (!breadCrumbs.getItems().isEmpty()) {
                model.put("breadCrumbs", breadCrumbs);
            }
        }
    }

    private String createReportWikiIssueLink(final ParsedWikiPage parsedWikiPage) {
        final String title = String.format("%s (Wiki)", parsedWikiPage.getSource().getId());
        final String body = "";
        return String.format("https://github.com/voho/web/issues/new?title=%s&body=%s",
                HtmlEscapers.htmlEscaper().escape(title),
                HtmlEscapers.htmlEscaper().escape(body));
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
