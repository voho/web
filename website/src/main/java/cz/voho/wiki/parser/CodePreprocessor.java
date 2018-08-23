package cz.voho.wiki.parser;

import com.google.common.collect.Sets;
import com.vladsch.flexmark.ast.FencedCodeBlock;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import cz.voho.wiki.parser.code.CodeProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class CodePreprocessor implements Preprocessor {
    private static final Logger LOG = LoggerFactory.getLogger(CodePreprocessor.class);

    private final List<CodeProcessor> processors;

    public CodePreprocessor(final CodeProcessor... processors) {
        this.processors = Arrays.asList(processors);
    }

    @Override
    public final Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        return Sets.newHashSet(new NodeRenderingHandler<>(FencedCodeBlock.class, (node, nodeRendererContext, html) -> {
            final String codeLang = resolveLanguage(node);
            final String codeSource = resolveCode(node);

            for (final CodeProcessor processor : processors) {
                if (processor.handle(html, codeLang, codeSource)) {
                    LOG.debug("Handling {} code: {}", codeLang, getClass().getSimpleName());
                    return;
                }
            }

            LOG.warn("Code {} stays unhandled.", codeLang);
        }));
    }

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
