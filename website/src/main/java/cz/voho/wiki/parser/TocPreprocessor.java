package cz.voho.wiki.parser;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.vladsch.flexmark.ast.Heading;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import cz.voho.wiki.model.Toc;
import cz.voho.wiki.model.TocItem;
import cz.voho.wiki.page.parsed.WikiContext;
import cz.voho.wiki.model.WikiPageSource;

import java.util.Map;
import java.util.Set;

public class TocPreprocessor implements Preprocessor {
    private final Map<Node, Map<String, Integer>> counters = Maps.newHashMap();

    @Override
    public void preprocessNodes(WikiContext context, WikiPageSource source, Node root) {
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

        context.addToc(source.getId(), toc);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        return Sets.newHashSet(new NodeRenderingHandler<>(Heading.class, new CustomNodeRenderer<Heading>() {
            @Override
            public void render(Heading node, NodeRendererContext context, HtmlWriter html) {
                if (node.getAnchorRefId() != null) {
                    html.attr("id", node.getAnchorRefId());
                }

                html.srcPos(node.getText()).withAttr().tagLine("h" + node.getLevel(), new Runnable() {
                    @Override
                    public void run() {
                        context.renderChildren(node);
                    }
                });
            }
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
