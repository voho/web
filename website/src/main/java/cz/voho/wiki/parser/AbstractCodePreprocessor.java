package cz.voho.wiki.parser;

import com.google.common.collect.Sets;
import com.vladsch.flexmark.ast.FencedCodeBlock;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.Locale;
import java.util.Set;

abstract class AbstractCodePreprocessor implements Preprocessor {
    @Override
    public final Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        return Sets.newHashSet(new NodeRenderingHandler<>(FencedCodeBlock.class, (node, nodeRendererContext, html) -> {
            final String codeLang = resolveLanguage(node);
            final String codeSource = resolveCode(node);

            handle(html, codeLang, codeSource);
        }));
    }

    protected abstract void handle(HtmlWriter html, String codeLang, String codeSource);

    private String resolveCode(final FencedCodeBlock node) {
        return node.getContentChars().unescape().trim();
    }

    private String resolveLanguage(final FencedCodeBlock node) {
        final BasedSequence info = node.getInfo();
        if (info.isNotNull() && !info.isBlank()) {
            return info.unescape().toLowerCase(Locale.ROOT).trim();
        }
        return "nohighlight";
    }
}
