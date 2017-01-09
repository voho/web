package cz.voho.web.lambda.utility.generate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.PosixFilePermission;
import java.util.EnumSet;

public class ExecutableBinaryFile {
    private final String binaryBaseName;

    public ExecutableBinaryFile(final String binaryBaseName) {
        this.binaryBaseName = binaryBaseName;
    }

    public Path ensureExecutablePath() throws IOException {
        Path finalPath = getBasicBinaryPath();

        if (!Files.exists(finalPath)) {
            throw new IllegalStateException("Default binary does not exists: " + finalPath.toAbsolutePath());
        }

        if (!Files.isExecutable(finalPath)) {
            // WORKAROUND
            // ==========

            // http://stackoverflow.com/questions/36063411/aws-lambda-permission-denied-when-trying-to-use-ffmpeg

            final Path workaroundPath = getWorkaroundPath();
            Files.copy(finalPath, workaroundPath, StandardCopyOption.REPLACE_EXISTING);

            if (!Files.isExecutable(workaroundPath)) {
                makeFileExecutable(workaroundPath);
            }

            finalPath = workaroundPath;
        }

        if (!Files.isExecutable(finalPath)) {
            throw new IllegalStateException("Could not make the binary executable: " + finalPath.toAbsolutePath());
        }

        return finalPath.toAbsolutePath();
    }

    private Path getWorkaroundPath() {
        return Paths.get("/", "tmp", binaryBaseName);
    }

    private void makeFileExecutable(final Path path) throws IOException {
        Files.setPosixFilePermissions(
                path,
                EnumSet.of(
                        PosixFilePermission.OWNER_READ,
                        PosixFilePermission.OWNER_WRITE,
                        PosixFilePermission.OWNER_EXECUTE
                )
        );
    }

    private Path getBasicBinaryPath() {
        String envTaskRoot = System.getenv("LAMBDA_TASK_ROOT");

        if (envTaskRoot == null) {
            // fallback
            envTaskRoot = "src/main/resources";
        }

        return Paths.get(envTaskRoot, binaryBaseName);
    }
}
