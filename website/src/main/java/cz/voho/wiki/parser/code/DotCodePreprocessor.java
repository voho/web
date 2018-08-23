package cz.voho.wiki.parser.code;

import com.vladsch.flexmark.html.HtmlWriter;
import cz.voho.wiki.repository.image.WikiImageRepository;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class DotCodePreprocessor implements CodeProcessor {
    private static final String DOT_GRAPH = "dot:graph";
    private static final String DOT_DIGRAPH = "dot:digraph";

    private static final String DOT_PREFIX = "bgcolor=transparent;dpi=70;" +
            "node[fontname=\"Arial\",color=silver,style=filled,fillcolor=white];" +
            "graph[fontname=\"Arial\"];" +
            "edge[fontname=\"Arial\"];";

    private final WikiImageRepository wikiImageCacheWarmUp;

    public DotCodePreprocessor(final WikiImageRepository wikiImageCacheWarmUp) {
        this.wikiImageCacheWarmUp = wikiImageCacheWarmUp;
    }

    @Override
    public boolean handle(final HtmlWriter html, final String codeLang, final String codeSource) {
        if (codeLang.equalsIgnoreCase(DOT_GRAPH)) {
            dotGraph(html, codeSource);
            return true;
        } else if (codeLang.equalsIgnoreCase(DOT_DIGRAPH)) {
            dotDigraph(html, codeSource);
            return true;
        } else {
            return false;
        }
    }

    private void dotGraph(final HtmlWriter html, final String codeSource) {
        final String codeSourceFixed = "graph {" + DOT_PREFIX + codeSource + "}";
        wikiImageCacheWarmUp.warmUpCacheDotSvg(codeSourceFixed);
        final String sourceEncoded = encodeForUrl(codeSourceFixed);
        dot(html, sourceEncoded);
    }

    private void dotDigraph(final HtmlWriter html, final String codeSource) {
        final String codeSourceFixed = "digraph {" + DOT_PREFIX + codeSource + "}";
        wikiImageCacheWarmUp.warmUpCacheDotSvg(codeSourceFixed);
        final String sourceEncoded = encodeForUrl(codeSourceFixed);
        dot(html, sourceEncoded);
    }

    private void dot(final HtmlWriter html, final String sourceEncoded) {
        html.raw(String.format("<div class='figure graph'><img src='/generate/svg/graph?data=%s' alt='diagram' /></div>", sourceEncoded));
    }

    private String encodeForUrl(final String source) {
        return Base64.getUrlEncoder().encodeToString(source.getBytes(StandardCharsets.UTF_8));
    }
}
