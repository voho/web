package cz.voho.wiki.parser;

import com.google.common.collect.Sets;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.options.MutableDataHolder;
import cz.voho.wiki.model.ParsedWikiPage;

import java.util.Set;

public interface Preprocessor extends NodeRenderer, HtmlRenderer.HtmlRendererExtension {
    default void preprocessSource(ParsedWikiPage context) {
        // NOP
    }

    default void preprocessNodes(ParsedWikiPage context, Node root) {
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

    @Override
    default void rendererOptions(MutableDataHolder mutableDataHolder) {
        // NOP
    }
}
