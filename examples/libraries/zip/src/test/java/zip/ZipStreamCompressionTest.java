package zip;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ZipStreamCompressionTest {
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private final ZipStreamCompression toTest = new ZipStreamCompression();

    @Test
    public void testEndToEnd() throws IOException {
        final List<Path> filesToCompress = new LinkedList<>();
        filesToCompress.add(createTemporaryFile("file_1.txt", "Contents of file 1"));
        filesToCompress.add(createTemporaryFile("file_2.txt", "Contents of file 2"));
        filesToCompress.add(createTemporaryFile("file_3.txt", "Contents of file 3"));

        final Path result = temporaryFolder.newFile("result.zip").toPath();

        // COMPRESS
        toTest.compressFiles(filesToCompress, result);

        assertTrue(Files.exists(result));

        final Path extractedDirectory = temporaryFolder.newFolder("extracted").toPath();

        // DECOMPRESS
        toTest.decompressFiles(result, extractedDirectory);

        assertTrue(Files.exists(extractedDirectory));
        assertTrue(Files.isDirectory(extractedDirectory));

        final Path[] extractedFiles = Files.list(extractedDirectory).sorted().toArray(Path[]::new);

        assertEquals(filesToCompress.size(), extractedFiles.length);

        for (int i = 0; i < filesToCompress.size(); i++) {
            final Path originalFile = filesToCompress.get(i);
            final Path actualFile = extractedFiles[i];

            // same name
            assertEquals(originalFile.getFileName(), actualFile.getFileName());
            // same size
            assertEquals(Files.size(originalFile), Files.size(actualFile));
            // same contents
            assertArrayEquals(Files.readAllBytes(originalFile), Files.readAllBytes(actualFile));
        }
    }

    private Path createTemporaryFile(final String fileName, final String fileContents) throws IOException {
        final Path path = temporaryFolder.newFile(fileName).toPath();
        Files.write(path, fileContents.getBytes(StandardCharsets.UTF_8));
        return path;
    }
}