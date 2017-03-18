package cz.voho.wiki.parser;

import com.vladsch.flexmark.ast.Link;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.NodeVisitor;
import com.vladsch.flexmark.ast.VisitHandler;
import com.vladsch.flexmark.util.sequence.PrefixedSubSequence;
import cz.voho.common.utility.WikiLinkUtility;
import cz.voho.wiki.model.ParsedWikiPage;
import cz.voho.wiki.model.WikiPageSource;

public class WikiLinkPreprocessor implements Preprocessor {
    @Override
    public void preprocessNodes(ParsedWikiPage context, WikiPageSource source, Node root) {
        String sourceId = source.getId();

        if (sourceId != null) {
            // it can be NULL in tests only (TODO)

            NodeVisitor visitor = new NodeVisitor(new VisitHandler<>(Link.class, link -> {
                String linkUrl = link.getUrl().toString();

                if (linkUrl.startsWith("wiki/")) {
                    String targetId = WikiLinkUtility.stripWikiPrefixSuffix(WikiLinkUtility.stripSlashes(linkUrl));
                    link.setUrl(PrefixedSubSequence.of("/wiki/" + targetId + "/"));
                    context.addLinkTo(targetId);
                }
            }));

            visitor.visit(root);
        }
    }
}
