package cz.voho.wiki.parser;

import com.google.common.collect.Sets;
import com.google.common.io.CharStreams;
import com.vladsch.flexmark.ast.FencedCodeBlock;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.PrefixedSubSequence;
import cz.voho.wiki.repository.image.WikiImageRepository;
import net.sourceforge.plantuml.code.Transcoder;
import net.sourceforge.plantuml.code.TranscoderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class CodePreprocessor implements Preprocessor {
    private static final Logger LOG = LoggerFactory.getLogger(CodePreprocessor.class);

    private static final String DOT_GRAPH = "dot:graph";
    private static final String DOT_DIGRAPH = "dot:digraph";
    private static final String UML_CLASS = "uml:class";
    private static final String UML_ACTIVITY = "uml:activity";
    private static final String UML_SEQUENCE = "uml:seq";
    private static final String RUNKIT_JS = "runkit:js";
    private static final String INCLUDE_PREFIX = "include:";

    private static final String GITHUB_PREFIX = "https://github.com/voho/web/blob/master/";
    private static final String CODECOV_PREFIX = "https://codecov.io/gh/voho/web/src/master/";
    private static final String TRAVIS_URL = "https://travis-ci.org/voho/web.svg?branch=master";

    private static final boolean PLANT_TEXT_ENABLED = true;
    private static final Transcoder PLANT_TEXT_TRANSCODER = TranscoderUtil.getDefaultTranscoder();

    private static final String DOT_PREFIX = "bgcolor=transparent;dpi=70;" +
            "node[fontname=\"Arial\",color=silver,style=filled,fillcolor=white];" +
            "graph[fontname=\"Arial\"];" +
            "edge[fontname=\"Arial\"];";

    private final String UML_PREFIX = "@startuml\n\n" +
            "skinparam defaultFontName Arial\n" +
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

    private final WikiImageRepository wikiImageCacheWarmUp;

    public CodePreprocessor(final WikiImageRepository wikiImageCacheWarmUp) {
        this.wikiImageCacheWarmUp = wikiImageCacheWarmUp;
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        return Sets.newHashSet(new NodeRenderingHandler<>(FencedCodeBlock.class, (node, nodeRendererContext, html) -> {
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
            } else if (codeLang.equalsIgnoreCase(RUNKIT_JS)) {
                runkitJs(node, html);
            } else {
                sourceCode(node, html);
            }
        }));
    }

    private void runkitJs(final FencedCodeBlock node, final HtmlWriter html) {
        final String randomId = String.format("runkit-js-%s", UUID.randomUUID().toString());
        html.line();
        html.raw(PrefixedSubSequence.of(String.format("<div id=\"%s\">", randomId)));
        html.openPre();
        html.text(node.getContentChars());
        html.closePre();
        html.raw("</div>");
        html.line();
        html.raw(String.format("<script src=\"https://embed.runkit.com\" data-element-id=\"%s\"></script>", randomId));
        html.line();
    }

    private void sourceCode(final FencedCodeBlock node, final HtmlWriter html) {
        final String lang = sourceCodeLang(node);

        if (lang.startsWith(INCLUDE_PREFIX)) {
            // contents needs to be loaded from ZIP first
            final String langWithoutPrefix = lang.substring(INCLUDE_PREFIX.length());
            final String sourcePath = node.getContentChars().toString().trim();
            try {
                final Optional<ZipEntryResult> zipEntry = findZipEntry(sourcePath);

                if (zipEntry.isPresent()) {
                    sourceCodeUsingString(html, zipEntry.get().zipEntryName, langWithoutPrefix, zipEntry.get().zipEntryContents);
                } else {
                    sourceCodeUsingString(html, "Nenalezeno: " + sourcePath, "nohighlight", "N/A");
                }
            } catch (Exception e) {
                LOG.error("Error while loading contents from ZIP.", e);
            }
        } else {
            // inline contents
            final String source = node.getContentChars().toString();
            sourceCodeUsingString(html, null, lang, source);
        }
    }

    private Optional<ZipEntryResult> findZipEntry(final String sourcePath) throws IOException {
        final Path path = Paths.get("/tmp/examples.zip");

        if (Files.exists(path)) {
            try (final ZipFile zipFile = new ZipFile(path.toFile())) {
                return zipFile
                        .stream()
                        .filter(f -> f.getName().endsWith(sourcePath))
                        .map(ZipEntry.class::cast)
                        .findFirst()
                        .map(f -> {
                            final ZipEntryResult result = new ZipEntryResult();
                            result.zipEntryName = f.getName();
                            result.zipEntryContents = extractZipEntry(zipFile, f);
                            return result;
                        });
            } catch (Exception e) {
                final String error = String.format("ERROR: Cannot load the file: %s", path.toAbsolutePath());
                throw new IOException(error);
            }
        } else {
            final String error = String.format("ERROR: ZIP file with source not found: %s", path.toAbsolutePath());
            throw new IOException(error);
        }
    }

    private String extractZipEntry(final ZipFile zipFile, final ZipEntry zipEntry) {
        try (final InputStreamReader reader = new InputStreamReader(zipFile.getInputStream(zipEntry), StandardCharsets.UTF_8)) {
            return CharStreams.toString(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sourceCodeUsingString(final HtmlWriter html, final String sourcePath, final String lang, final String source) {
        // https://github.com/voho/web/blob/master/examples/lz77/src/main/java/LZ77Codeword.java
        // ________________________________________^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

        // https://codecov.io/gh/voho/web/src/master/examples/random/src/main/java/random/ReservoirSamplingSingleItem.java
        // __________________________________________^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

        if (sourcePath != null) {
            final String githubUrl = GITHUB_PREFIX + sourcePath;
            final String codecovUrl = CODECOV_PREFIX + sourcePath;

            html.line();
            html.raw("<p class='code-included-disclaimer'>");

            html.raw("<span class='fa fa-github'></span> <a href='").text(githubUrl).raw("' onclick='return !window.open(this.href);'>Zdrojový kód (GitHub)</a>");
            html.raw(" ");
            html.raw("<span class='fa fa-check'></span> <a href='").text(codecovUrl).raw("' onclick='return !window.open(this.href);'>Pokrytí testy (CodeCov)</a>");
            html.raw(" ");
            html.raw("<img src='" + TRAVIS_URL + "' alt='' />");

            html.raw("</p>");
            html.line();
        }

        html.line();
        html.raw(PrefixedSubSequence.of(String.format("<pre><code class=\"hljs %s\">", lang)));
        html.openPre();
        html.text(source);
        html.closePre();
        html.raw("</code></pre>");
        html.line();
    }

    private String sourceCodeLang(final FencedCodeBlock node) {
        String lang = "nohighlight";
        final BasedSequence info = node.getInfo();
        if (info.isNotNull() && !info.isBlank()) {
            lang = info.unescape().trim();
        }
        return lang;
    }

    private void umlSequence(final HtmlWriter html, final String codeSource) {
        final String codeSourceFixed = UML_PREFIX + codeSource + UML_SUFFIX;
        final String imageUrl = getUmlImageUrl(codeSourceFixed);
        html.raw(String.format("<div class='figure uml'><img src='%s' alt='UML (sekvenční diagram)' /></div>", imageUrl));
    }

    private void umlActivity(final HtmlWriter html, final String codeSource) {
        final String codeSourceFixed = UML_PREFIX + codeSource + UML_SUFFIX;
        final String imageUrl = getUmlImageUrl(codeSourceFixed);
        html.raw(String.format("<div class='figure uml'><img src='%s' alt='UML (diagram aktivit)' /></div>", imageUrl));
    }

    private void umlClass(final HtmlWriter html, final String codeSource) {
        final String codeSourceFixed = UML_PREFIX + codeSource + UML_SUFFIX;
        final String imageUrl = getUmlImageUrl(codeSourceFixed);
        html.raw(String.format("<div class='figure uml'><img src='%s' alt='UML (diagram tříd)' /></div>", imageUrl));
    }

    private String getUmlImageUrl(final String codeSourceFixed) {
        if (PLANT_TEXT_ENABLED) {
            try {
                return String.format("https://www.planttext.com/plantuml/svg/%s", PLANT_TEXT_TRANSCODER.encode(codeSourceFixed));
            } catch (IOException e) {
                // ignore the exception and just do it the old way
            }
        }

        wikiImageCacheWarmUp.warmUpCachePlantUmlSvg(codeSourceFixed);
        final String sourceEncoded = encodeForUrl(codeSourceFixed);
        return String.format("/generate/svg/uml?data=%s", sourceEncoded);
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

    private static class ZipEntryResult {
        String zipEntryName;
        String zipEntryContents;
    }
}
