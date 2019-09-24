package cz.voho.umlambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cz.voho.web.lambda.model.GenerateImageRequest;
import cz.voho.web.lambda.utility.ExecutableBinaryFile;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Set;

import static cz.voho.web.lambda.model.GenerateImageRequest.FORMAT_PNG;
import static cz.voho.web.lambda.model.GenerateImageRequest.FORMAT_SVG;

/**
 * The main AWS Lambda handler.
 */
public class Handler implements RequestStreamHandler {
    private static final Set<String> SUPPORTED_FORMATS = Sets.newHashSet(FORMAT_PNG, FORMAT_SVG);
    private static final ExecutableBinaryFile DOT_BINARY = new ExecutableBinaryFile("dot_static");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        GenerateImageRequest request = GSON.fromJson(new InputStreamReader(inputStream, StandardCharsets.UTF_8), GenerateImageRequest.class);
        validateRequest(request);
        final SourceStringReader reader = new SourceStringReader(request.getSource());

        final Path binaryPath = DOT_BINARY.ensureExecutablePath();
        System.setProperty("GRAPHVIZ_DOT", binaryPath.toString());

        switch (request.getFormat()) {
            case FORMAT_SVG:
                reader.generateImage(outputStream, new FileFormatOption(FileFormat.SVG));
                break;
            case FORMAT_PNG:
                reader.generateImage(outputStream, new FileFormatOption(FileFormat.PNG));
                break;
            default:
                throw new IllegalStateException("Unsupported format.");
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

    private void log(Context context, String messageFormat, Object... arguments) {
        if (context != null) {
            context.getLogger().log(String.format(messageFormat, arguments));
        }
    }
}
