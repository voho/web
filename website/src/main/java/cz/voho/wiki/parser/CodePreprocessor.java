package cz.voho.wiki.parser;

import com.google.common.collect.Sets;
import com.vladsch.flexmark.ast.FencedCodeBlock;
import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.PrefixedSubSequence;
import cz.voho.wiki.image.WikiImageCacheWarmUp;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.BitSet;
import java.util.Set;

public class CodePreprocessor implements Preprocessor {

    public static void main(String[] args) {
        String v = "hello world";
        String p = "llo";
        System.out.println(find(v.toCharArray(), p.toCharArray()));
    }

    private static int bitapx(char[] text, char[] pattern) {
        int m = pattern.length;
        BitSet patternDiffRev = new BitSet(m + 1);
        // patternDiffRev[0] = 1
        // patternDiffRev[1..m] = 0
        patternDiffRev.set(0);
        for (int iText = 0; iText < text.length; iText++) {
            for (int iPattern = m; iPattern >= 1; iPattern--) {
                // bitový posuv + AND
                boolean diffBitPrev = patternDiffRev.get(iPattern - 1);
                boolean diffBitCurrent = text[iText] == pattern[iPattern - 1];
                patternDiffRev.set(iPattern, diffBitPrev && diffBitCurrent);
            }
            if (patternDiffRev.get(m)) {
                // nalezeno
                return iText - m + 1;
            }
        }
        // nenalezeno
        return -1;
    }

    /**
     * Bitap search
     **/
    private static int bitap_search(char[] text, char[] pattern) {
        int m = pattern.length;
        if (m == 0 || m > 63) {
            throw new IllegalArgumentException("Pattern has to be 1-63 characters long.");
        }
        long pattern_mask[] = new long[Character.MAX_VALUE + 1];
        // = 1...1110
        long patternDiffRev = ~1;
        // = 0...010...0
        long lastPatternBit = 1L << m;
        // inicializace bitových masek masky pro všechny znaky
        for (int iChar = 0; iChar <= Character.MAX_VALUE; ++iChar)
            pattern_mask[iChar] = ~0;
        for (int iPattern = 0; iPattern < m; ++iPattern) {
            // = 0...010...0
            char patternChar = pattern[iPattern];
            pattern_mask[patternChar] &= ~(1L << iPattern);
        }
        for (int iText = 0; iText < text.length; ++iText) {
            /** Update the bit array **/
            patternDiffRev |= pattern_mask[text[iText]];
            // bitový posuv vlevo o jeden bit
            patternDiffRev <<= 1;
            if ((patternDiffRev & lastPatternBit) == 0) {
                // nalezeno
                return iText - m + 1;
            }
        }
        // nenalezeno
        return -1;
    }

    public static int find(char[] text, char[] pattern) {
        // 0...0001
        BitSet patternDiffRev = new BitSet(pattern.length + 1);
        patternDiffRev.set(0);

        for (int iText = 0; iText < text.length; iText++) {
            // pro všechny znaky textu:
            for (int iPattern = pattern.length; iPattern >= 1; iPattern--) {
                // pro všechny znaky vyhledávaného řetězce zprava doleva:
                int iPatternNextChar = iPattern - 1;
                boolean nextCharDiff = patternDiffRev.get(iPatternNextChar);
                boolean currentCharDiff = text[iText] == pattern[iPatternNextChar];
                patternDiffRev.set(iPattern, nextCharDiff && currentCharDiff);
            }
            if (patternDiffRev.get(pattern.length)) {
                // nalezeno
                return iText - pattern.length + 1;
            }
        }
        // nenalezeno
        return -1;
    }


    private static final String DOT_GRAPH = "dot:graph";
    private static final String DOT_DIGRAPH = "dot:digraph";
    private static final String UML_CLASS = "uml:class";
    private static final String UML_ACTIVITY = "uml:activity";
    private static final String UML_SEQUENCE = "uml:seq";

    private static final String DOT_PREFIX = "bgcolor=transparent;dpi=70;node[color=silver,style=filled,fillcolor=white];";

    private final String UML_PREFIX = "@startuml\n\n" +
            "skinparam style strictuml\n" +
            "skinparam noteFontSize 12\n" +
            "skinparam noteBackgroundColor #f6f6f6\n" +
            "skinparam noteFontColor #666666\n" +
            "skinparam noteBorderColor #CCCCCC\n" +
            "skinparam backgroundColor transparent\n" +
            "skinparam classBackgroundColor #FFFA96\n" +
            "skinparam classBorderColor #999999\n" +
            "skinparam classFontSize 16\n" +
            "skinparam classFontStyle bold\n" +
            "skinparam classAttributeFontSize 12\n" +
            "skinparam activityFontSize 16\n" +
            "skinparam classStereotypeFontSize 12\n" +
            "skinparam classArrowColor #000000\n" +
            "skinparam sequenceDividerFontStyle plain\n" +
            "skinparam sequenceDividerBackgroundColor #f6f6f6\n" +
            "skinparam sequenceDividerFontColor #666666\n" +
            "skinparam sequenceActorFontStyle bold\n" +
            "skinparam sequenceParticipantFontStyle bold\n" +
            "skinparam sequenceArrowColor #333333\n" +
            "skinparam sequenceParticipantBackgroundColor #FFFA96\n" +
            "skinparam sequenceParticipantBorderColor #999999\n" +
            "skinparam sequenceLifeLineBorderColor #999999\n";

    private final String UML_SUFFIX = "\n\n@enduml";

    private WikiImageCacheWarmUp wikiImageCacheWarmUp;

    public CodePreprocessor(final WikiImageCacheWarmUp wikiImageCacheWarmUp) {
        this.wikiImageCacheWarmUp = wikiImageCacheWarmUp;
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        return Sets.newHashSet(new NodeRenderingHandler<>(FencedCodeBlock.class, new CustomNodeRenderer<FencedCodeBlock>() {
            @Override
            public void render(final FencedCodeBlock node, final NodeRendererContext nodeRendererContext, final HtmlWriter html) {
                final String codeLang = node.getInfo().toString();
                final String codeSource = node.getContentChars().toString();

                if (codeLang.equalsIgnoreCase(DOT_GRAPH)) {
                    dotGraph(html, codeSource);
                } else if (codeLang.equalsIgnoreCase(DOT_DIGRAPH)) {
                    dotDigraph(html, codeSource);
                } else if (codeLang.equalsIgnoreCase(UML_CLASS)) {
                    umlClass(html, codeSource);
                } else if (codeLang.equalsIgnoreCase(UML_ACTIVITY)) {
                    umlActivity(html, codeSource);
                } else if (codeLang.equalsIgnoreCase(UML_SEQUENCE)) {
                    umlSequence(html, codeSource);
                } else {
                    sourceCode(node, html);
                }
            }
        }));
    }

    private void sourceCode(final FencedCodeBlock node, final HtmlWriter html) {
        String lang = "nohighlight";
        final BasedSequence info = node.getInfo();
        if (info.isNotNull() && !info.isBlank()) {
            lang = info.unescape();
        }
        html.line();
        html.raw(PrefixedSubSequence.of(String.format("<pre><code class=\"hljs %s\">", lang)));
        html.openPre();
        html.text(node.getContentChars());
        html.closePre();
        html.raw("</code></pre>");
        html.line();
    }

    private void umlSequence(final HtmlWriter html, final String codeSource) {
        final String codeSourceFixed = UML_PREFIX + codeSource + UML_SUFFIX;
        wikiImageCacheWarmUp.warmUpCachePlantUmlSvg(codeSourceFixed);
        final String sourceEncoded = encodeForUrl(codeSourceFixed);
        html.raw(String.format("<div class='figure uml'><img src='/generate/svg/uml?data=%s' alt='UML (sekvenční diagram)' /></div>", sourceEncoded));
    }

    private void umlActivity(final HtmlWriter html, final String codeSource) {
        final String codeSourceFixed = UML_PREFIX + codeSource + UML_SUFFIX;
        wikiImageCacheWarmUp.warmUpCachePlantUmlSvg(codeSourceFixed);
        final String sourceEncoded = encodeForUrl(codeSourceFixed);
        html.raw(String.format("<div class='figure uml'><img src='/generate/svg/uml?data=%s' alt='UML (diagram aktivit)' /></div>", sourceEncoded));
    }

    private void umlClass(final HtmlWriter html, final String codeSource) {
        final String codeSourceFixed = UML_PREFIX + codeSource + UML_SUFFIX;
        wikiImageCacheWarmUp.warmUpCachePlantUmlSvg(codeSourceFixed);
        final String sourceEncoded = encodeForUrl(codeSourceFixed);
        html.raw(String.format("<div class='figure uml'><img src='/generate/svg/uml?data=%s' alt='UML (diagram tříd)' /></div>", sourceEncoded));
    }

    private void dotDigraph(final HtmlWriter html, final String codeSource) {
        final String codeSourceFixed = "digraph {" + DOT_PREFIX + codeSource + "}";
        wikiImageCacheWarmUp.warmUpCacheDotSvg(codeSourceFixed);
        final String sourceEncoded = encodeForUrl(codeSourceFixed);
        html.raw(String.format("<div class='figure graph'><img src='/generate/svg/graph?data=%s' alt='diagram' /></div>", sourceEncoded));
    }

    private void dotGraph(final HtmlWriter html, final String codeSource) {
        final String codeSourceFixed = "graph {" + DOT_PREFIX + codeSource + "}";
        wikiImageCacheWarmUp.warmUpCacheDotSvg(codeSourceFixed);
        final String sourceEncoded = encodeForUrl(codeSourceFixed);
        html.raw(String.format("<div class='figure graph'><img src='/generate/svg/graph?data=%s' alt='diagram' /></div>", sourceEncoded));
    }

    private String encodeForUrl(final String source) {
        return Base64.getUrlEncoder().encodeToString(source.getBytes(StandardCharsets.UTF_8));
    }
}
