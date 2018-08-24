package cz.voho.wiki.parser;

import com.vladsch.flexmark.ast.Link;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.NodeVisitor;
import com.vladsch.flexmark.ast.VisitHandler;
import com.vladsch.flexmark.util.sequence.PrefixedSubSequence;
import cz.voho.common.utility.WikiLinkUtility;
import cz.voho.wiki.model.ParsedWikiPage;

public class WikiLinkPreprocessor implements Preprocessor {
    private static final String WIKI_URL_FORMAT = "/wiki/%s/";
    private static final String WIKI_LINK_PREFIX = "wiki/";

    @Override
    public void preprocessNodes(final ParsedWikiPage context, final Node root) {
        final NodeVisitor visitor = new NodeVisitor(new VisitHandler<>(Link.class, link -> {
            final String linkUrl = link.getUrl().toString();

            if (linkUrl.startsWith(WIKI_LINK_PREFIX)) {
                final String targetId = WikiLinkUtility.stripWikiPrefixSuffix(WikiLinkUtility.stripSlashes(linkUrl));
                link.setUrl(PrefixedSubSequence.of(String.format(WIKI_URL_FORMAT, targetId)));
                context.addLinkTo(targetId);
            }
        }));

        visitor.visit(root);
    }
}
