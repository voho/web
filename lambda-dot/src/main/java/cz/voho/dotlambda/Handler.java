package cz.voho.dotlambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cz.voho.web.lambda.model.GenerateImageRequest;
import cz.voho.web.lambda.utility.ExecutableBinaryFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import static cz.voho.web.lambda.model.GenerateImageFormat.FORMAT_PNG;
import static cz.voho.web.lambda.model.GenerateImageFormat.FORMAT_SVG;

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

        final String format = request.getFormat();
        final String binaryPathOption = DOT_BINARY.ensureExecutablePath().toString();
        final String formatOption = "-T" + format;

        final Process process = new ProcessBuilder()
                .command(binaryPathOption, formatOption)
                .start();

        writeInput(process, request);

        final int processExitCode;

        try {
            processExitCode = process.waitFor();
        } catch (InterruptedException e) {
            throw new IOException("Process interrupted.", e);
        }

        if (processExitCode != 0) {
            throw new IOException("Invalid exit value: " + process.exitValue() + " / Source = " + request.getSource());
        }

        if (FORMAT_SVG.equalsIgnoreCase(format)) {
            readTextOutput(process, outputStream);
        } else if (FORMAT_PNG.equalsIgnoreCase(format)) {
            readBinaryOutput(process, outputStream);
        } else {
            throw new IllegalStateException("Unsupported format.");
        }
    }

    private void readBinaryOutput(final Process process, final OutputStream outputStream) throws IOException {
        try (InputStream inputStream = process.getInputStream()) {
            ByteStreams.copy(inputStream, outputStream);
        }
    }

    private void readTextOutput(final Process process, final OutputStream outputStream) throws IOException {
        try (InputStream inputStream = process.getInputStream()) {
            ByteStreams.copy(inputStream, outputStream);
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
