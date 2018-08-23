package cz.voho.wiki.parser.code;

import com.vladsch.flexmark.html.HtmlWriter;

public interface CodeProcessor {
    boolean handle(final HtmlWriter html, final String codeLang, final String codeSource);
}
