package cz.voho.wiki.parser.code;

import com.google.common.io.CharStreams;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.util.sequence.PrefixedSubSequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class IncludeSourceCodePreprocessor implements CodeProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(IncludeSourceCodePreprocessor.class);

    private static final String GITHUB_PREFIX = "https://github.com/voho/web/blob/master/";
    private static final String CODECOV_PREFIX = "https://codecov.io/gh/voho/web/src/master/";
    private static final String TRAVIS_ICON_URL = "https://travis-ci.org/voho/web.svg?branch=master";
    private static final String TRAVIS_BUILD_URL = "https://travis-ci.org/voho/web";

    private static final String INCLUDE_PREFIX = "include:";

    private static final List<Path> EXAMPLES_ZIP_LOOKUP_LOCATIONS = Arrays.asList(
            Paths.get("examples.zip"),
            Paths.get("ROOT/examples.zip"),
            Paths.get("webapps/ROOT/examples.zip"),
            Paths.get(System.getProperty("CATALINA_HOME", "."), "examples.zip"),
            Paths.get("/var/lib/tomcat8/webapps/ROOT/examples.zip"),
            Paths.get("../examples/target/examples.zip")
    );

    @Override
    public boolean handle(final HtmlWriter html, final String codeLang, final String codeSource) {
        if (codeLang.startsWith(INCLUDE_PREFIX)) {
            final String langWithoutPrefix = codeLang.substring(INCLUDE_PREFIX.length());

            try {
                final Optional<IncludeSourceCodePreprocessor.ZipEntryResult> zipEntry = findZipEntry(codeSource);

                if (zipEntry.isPresent()) {
                    sourceCodeUsingString(html, zipEntry.get().zipEntryName, langWithoutPrefix, zipEntry.get().zipEntryContents);
                } else {
                    throw new IllegalStateException("ZIP entry was not found: " + codeSource);
                }
            } catch (final Exception e) {
                throw new IllegalStateException("Error while loading contents from ZIP.", e);
            }

            return true;
        }

        return false;
    }

    private void sourceCodeUsingString(final HtmlWriter html, final String sourcePath, final String lang, final String source) {
        // https://github.com/voho/web/blob/master/examples/lz77/src/main/java/LZ77Codeword.java
        // ________________________________________^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

        // https://codecov.io/gh/voho/web/src/master/examples/random/src/main/java/random/ReservoirSamplingSingleItem.java
        // __________________________________________^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

        final String githubUrl = GITHUB_PREFIX + sourcePath;
        final String codecovUrl = CODECOV_PREFIX + sourcePath;

        html.line();
        html.raw(PrefixedSubSequence.of(String.format("<pre><code class=\"hljs %s\">", lang)));
        html.openPre();
        html.text(source);
        html.closePre();
        html.raw("</code></pre>");
        html.line();

        html.line();
        html.raw("<p class='code-included-disclaimer'>");

        html.raw("<a href='").text(githubUrl).raw("' onclick='return !window.open(this.href);'>Zdrojový kód</a>");
        html.raw(" ");
        html.raw("<a href='").text(codecovUrl).raw("' onclick='return !window.open(this.href);'>Pokrytí testy</a>");
        html.raw(" ");
        html.raw("<a href='" + TRAVIS_BUILD_URL + "' onclick='return !window.open(this.href);'><img src='" + TRAVIS_ICON_URL + "' alt='' /></a>");

        html.raw("</p>");
        html.line();
    }

    private Optional<IncludeSourceCodePreprocessor.ZipEntryResult> findZipEntry(final String sourcePath) throws IOException {
        final Path path = findZip();

        if (Files.exists(path)) {
            try (final ZipFile zipFile = new ZipFile(path.toFile())) {
                return zipFile
                        .stream()
                        .filter(f -> f.getName().endsWith(sourcePath))
                        .map(ZipEntry.class::cast)
                        .min(Comparator.comparing(ZipEntry::getName))
                        .map(f -> {
                            final IncludeSourceCodePreprocessor.ZipEntryResult result = new IncludeSourceCodePreprocessor.ZipEntryResult();
                            result.zipEntryName = f.getName();
                            result.zipEntryContents = extractZipEntry(zipFile, f);
                            return result;
                        });
            } catch (final Exception e) {
                final String error = String.format("ERROR: Cannot load the file: %s", path.toAbsolutePath());
                throw new IOException(error);
            }
        } else {
            final String error = String.format("ERROR: ZIP file with source not found: %s", path.toAbsolutePath());
            throw new IOException(error);
        }
    }

    private Path findZip() {
        for (final Path path : EXAMPLES_ZIP_LOOKUP_LOCATIONS) {
            LOG.info("Trying to locate ZIP file: {}", path.toAbsolutePath());

            if (Files.exists(path)) {
                LOG.info("ZIP file found: {}", path);
                return path;
            }
        }

        LOG.error("ZIP file was not found.");
        throw new IllegalStateException("ZIP file with examples was not found :(");
    }

    private String extractZipEntry(final ZipFile zipFile, final ZipEntry zipEntry) {
        try (final InputStreamReader reader = new InputStreamReader(zipFile.getInputStream(zipEntry), StandardCharsets.UTF_8)) {
            return CharStreams.toString(reader);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class ZipEntryResult {
        String zipEntryName;
        String zipEntryContents;
    }
}
