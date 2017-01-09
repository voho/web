package cz.voho.wiki.parser;

import com.google.common.collect.Sets;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import cz.voho.wiki.model.WikiContext;
import cz.voho.wiki.model.WikiPageSource;

import java.util.Set;

public interface Preprocessor extends NodeRenderer, HtmlRenderer.HtmlRendererExtension {
    default String preprocessSource(WikiContext context, WikiPageSource wikiPageSource, String source) {
        return source;
    }

    default void preprocessNodes(WikiContext context, WikiPageSource source, Node root) {
        // NOP
    }

    @Override
    default Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        return Sets.newLinkedHashSet();
    }

    @Override
    default void extend(HtmlRenderer.Builder builder, String s) {
        builder.nodeRendererFactory(dataHolder -> Preprocessor.this);
    }
}
