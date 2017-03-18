package cz.voho.wiki.parser;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.vladsch.flexmark.ast.Heading;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import cz.voho.wiki.model.ParsedWikiPage;
import cz.voho.wiki.model.Toc;
import cz.voho.wiki.model.TocItem;

import java.util.Map;
import java.util.Set;

public class TocPreprocessor implements Preprocessor {
    private final Map<Node, Map<String, Integer>> counters = Maps.newHashMap();

    @Override
    public void preprocessNodes(ParsedWikiPage context, Node root) {
        Toc toc = new Toc("toc", "Obsah", 0);
        TocItem[] lastByLevel = new TocItem[10];
        lastByLevel[0] = toc;

        for (Node child : root.getChildren()) {
            if (child instanceof Heading) {
                String title = ((Heading) child).getText().toString();
                String id = generateIdFromTitle(title, root);
                int level = ((Heading) child).getLevel() - 2;
                if (level > 0) {
                    TocItem item = new TocItem(id, title, level);
                    ((Heading) child).setAnchorRefId(item.getId());
                    lastByLevel[level - 1].addChildItem(item);
                    lastByLevel[level] = item;
                }
            }
        }

        context.setToc(toc);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        return Sets.newHashSet(new NodeRenderingHandler<>(Heading.class, (node, context, html) -> {
            if (node.getAnchorRefId() != null) {
                html.attr("id", node.getAnchorRefId());
            }

            html.srcPos(node.getText()).withAttr().tagLine("h" + node.getLevel(), () -> context.renderChildren(node));
        }));
    }

    private String generateIdFromTitle(String title, Node root) {
        counters.putIfAbsent(root, Maps.newHashMap());
        String result = title.toLowerCase().replaceAll("\\s+", "-").replace("\"", "");
        counters.get(root).putIfAbsent(result, 1);
        int current = counters.get(root).get(result);
        counters.get(root).put(result, current + 1);

        if (current > 1) {
            result = result + "-" + current;
        }

        return result;
    }
}
