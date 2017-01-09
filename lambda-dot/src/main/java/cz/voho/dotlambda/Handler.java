package cz.voho.dotlambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.google.common.io.ByteStreams;
import cz.voho.web.lambda.model.generate.GenerateImageRequest;
import cz.voho.web.lambda.model.generate.GenerateImageResponse;
import cz.voho.web.lambda.utility.generate.ExecutableBinaryFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import static cz.voho.web.lambda.model.generate.GenerateImageFormat.FORMAT_PNG;
import static cz.voho.web.lambda.model.generate.GenerateImageFormat.FORMAT_SVG;

/**
 * The main AWS Lambda handler.
 */
public class Handler implements RequestHandler<GenerateImageRequest, GenerateImageResponse> {
    private static final Set<String> SUPPORTED_FORMATS = Sets.newHashSet(FORMAT_PNG, FORMAT_SVG);
    private static final ExecutableBinaryFile DOT_BINARY = new ExecutableBinaryFile("dot_static");

    public GenerateImageResponse handleRequest(final GenerateImageRequest request, final Context context) {
        validateRequest(request);

        try {
            final String format = request.getFormat();
            final String binaryPathOption = DOT_BINARY.ensureExecutablePath().toString();
            final String formatOption = "-T" + format;

            final Process process = new ProcessBuilder()
                    .command(binaryPathOption, formatOption)
                    .start();

            writeInput(process, request);

            if (process.waitFor() == 0) {
                final GenerateImageResponse response = new GenerateImageResponse();

                response.setFormat(format);

                if (FORMAT_SVG.equalsIgnoreCase(format)) {
                    // generate SVG

                    readTextOutput(process, response);
                } else if (FORMAT_PNG.equalsIgnoreCase(format)) {
                    // generate PNG

                    readBinaryOutput(process, response);
                } else {
                    throw new IllegalStateException("Unsupported format.");
                }

                return response;
            } else {
                throw new IllegalStateException("Invalid exit value: " + process.exitValue() + " / Source = " + request.getSource());
            }
        } catch (Exception e) {
            throw new RuntimeException("Internal error / Source = " + request.getSource(), e);
        }
    }

    private void readBinaryOutput(final Process process, final GenerateImageResponse response) throws IOException {
        try (
                InputStream inputStream = process.getInputStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream()
        ) {
            ByteStreams.copy(inputStream, outputStream);
            response.setBinaryData(outputStream.toByteArray());
        }
    }

    private void readTextOutput(final Process process, final GenerateImageResponse response) throws IOException {
        try (
                InputStream inputStream = process.getInputStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream()
        ) {
            ByteStreams.copy(inputStream, outputStream);
            response.setTextData(new String(outputStream.toByteArray(), StandardCharsets.UTF_8));
        }
    }

    private void writeInput(final Process process, final GenerateImageRequest request) throws IOException {
        final byte[] requestSourceBytes = request.getSource().getBytes(StandardCharsets.UTF_8);

        try (
                ByteArrayInputStream inputStream = new ByteArrayInputStream(requestSourceBytes);
                OutputStream outputStream = process.getOutputStream()
        ) {
            ByteStreams.copy(inputStream, outputStream);
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
