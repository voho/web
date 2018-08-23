package cz.voho.wiki.parser;

import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.util.sequence.PrefixedSubSequence;

public class InlineSourceCodePreprocessor extends AbstractCodePreprocessor {
    private static final String ANY_SPECIAL_PREFIX = ":";

    @Override
    protected void handle(final HtmlWriter html, final String codeLang, final String codeSource) {
        if (!codeLang.contains(ANY_SPECIAL_PREFIX)) {
            html.line();
            html.raw(PrefixedSubSequence.of(String.format("<pre><code class=\"hljs %s\">", codeLang)));
            html.openPre();
            html.text(codeSource);
            html.closePre();
            html.raw("</code></pre>");
            html.line();
        }
    }
}
