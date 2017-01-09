package cz.voho.umlambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import cz.voho.web.lambda.model.generate.GenerateImageFormat;
import cz.voho.web.lambda.model.generate.GenerateImageRequest;
import cz.voho.web.lambda.model.generate.GenerateImageResponse;
import cz.voho.web.lambda.utility.generate.ExecutableBinaryFile;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Set;

import static cz.voho.web.lambda.model.generate.GenerateImageFormat.FORMAT_PNG;
import static cz.voho.web.lambda.model.generate.GenerateImageFormat.FORMAT_SVG;

/**
 * The main AWS Lambda handler.
 */
public class Handler implements RequestHandler<GenerateImageRequest, GenerateImageResponse> {
    private static final Set<String> SUPPORTED_FORMATS = Sets.newHashSet(GenerateImageFormat.FORMAT_PNG, FORMAT_SVG);
    private static final ExecutableBinaryFile DOT_BINARY = new ExecutableBinaryFile("dot_static");

    public GenerateImageResponse handleRequest(final GenerateImageRequest request, final Context context) {
        validateRequest(request);

        try {
            final String format = request.getFormat();
            final Path binaryPath = DOT_BINARY.ensureExecutablePath();
            System.setProperty("GRAPHVIZ_DOT", binaryPath.toString());

            final SourceStringReader reader = new SourceStringReader(request.getSource());

            try (final ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                final GenerateImageResponse response = new GenerateImageResponse();

                response.setFormat(format);

                if (FORMAT_SVG.equalsIgnoreCase(format)) {
                    // generate SVG

                    reader.generateImage(outputStream, new FileFormatOption(FileFormat.SVG));
                    final byte[] outputAsBytes = outputStream.toByteArray();
                    final String outputAsString = new String(outputAsBytes, StandardCharsets.UTF_8);
                    response.setTextData(outputAsString);
                } else if (FORMAT_PNG.equalsIgnoreCase(format)) {
                    // generate PNG

                    reader.generateImage(outputStream, new FileFormatOption(FileFormat.PNG));
                    final byte[] outputAsBytes = outputStream.toByteArray();
                    response.setBinaryData(outputAsBytes);
                } else {
                    throw new IllegalStateException("Unsupported format.");
                }

                return response;
            }
        } catch (Exception e) {
            throw new RuntimeException("Internal error / Source = " + request.getSource(), e);
        }
    }

    private void validateRequest(final GenerateImageRequest request) {
        validateFormat(request.getFormat());
        validateSource(request.getSource());
    }

    private void validateSource(final String source) {
        if (Strings.isNullOrEmpty(source)) {
            throw new IllegalArgumentException("No source specified.");
        }
    }

    private void validateFormat(final String format) {
        if (Strings.isNullOrEmpty(format)) {
            throw new IllegalArgumentException("Format is missing.");
        }

        if (!SUPPORTED_FORMATS.contains(format)) {
            throw new IllegalArgumentException("Unsupported format. Try using one of these: " + SUPPORTED_FORMATS);
        }
    }
}
