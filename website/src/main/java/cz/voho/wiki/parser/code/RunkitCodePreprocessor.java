package cz.voho.wiki.parser.code;

import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.util.sequence.PrefixedSubSequence;

import java.util.UUID;

public class RunkitCodePreprocessor implements CodeProcessor {
    private static final String RUNKIT_PREFIX = "runkit:";

    @Override
    public boolean handle(final HtmlWriter html, final String codeLang, final String codeSource) {
        // only JS is supported at the moment

        if (codeLang.startsWith(RUNKIT_PREFIX)) {
            final String langWithoutPrefix = codeLang.substring(RUNKIT_PREFIX.length());
            final String randomId = String.format("runkit-%s-%s", langWithoutPrefix, UUID.randomUUID().toString());
            html.line();
            html.raw(PrefixedSubSequence.of(String.format("<div id=\"%s\">", randomId)));
            html.openPre();
            html.text(codeSource);
            html.closePre();
            html.raw("</div>");
            html.line();
            html.raw(String.format("<script src=\"https://embed.runkit.com\" data-element-id=\"%s\"></script>", randomId));
            html.line();
            return true;
        }

        return false;
    }
}
