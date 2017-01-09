package cz.voho.wiki.parser;

import com.vladsch.flexmark.ast.Link;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.NodeVisitor;
import com.vladsch.flexmark.ast.VisitHandler;
import com.vladsch.flexmark.ast.Visitor;
import com.vladsch.flexmark.util.sequence.StringSequence;
import cz.voho.utility.WikiLinkUtility;
import cz.voho.wiki.model.WikiContext;
import cz.voho.wiki.model.WikiPageSource;

public class WikiLinkPreprocessor implements Preprocessor {
    @Override
    public void preprocessNodes(WikiContext context, WikiPageSource source, Node root) {
        String sourceId = source.getId();

        NodeVisitor visitor = new NodeVisitor(new VisitHandler<>(Link.class, new Visitor<Link>() {
            @Override
            public void visit(Link link) {
                String linkUrl = link.getUrl().toString();

                if (linkUrl.startsWith("wiki/")) {
                    String targetId = WikiLinkUtility.resolveWikiPageId(linkUrl);
                    link.setUrl(new StringSequence("/wiki/" + targetId + "/"));
                    context.addLink(sourceId, targetId);
                }
            }
        }));

        visitor.visit(root);
    }
}
