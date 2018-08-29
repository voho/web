package zip;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipStreamCompression {
    public void compressFiles(final Iterable<Path> filesToCompress, final Path outputZipFile) throws IOException {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(outputZipFile))) {
            for (final Path fileToCompress : filesToCompress) {
                // create a new ZIP entry
                zipOutputStream.putNextEntry(new ZipEntry(fileToCompress.getFileName().toString()));
                // write file contents into ZIP stream
                Files.copy(fileToCompress, zipOutputStream);
                // close ZIP entry
                zipOutputStream.closeEntry();
            }

            // finalize ZIP file
            zipOutputStream.finish();
        }
    }

    public void decompressFiles(final Path inputZipFile, final Path outputDirectory) throws IOException {
        try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(inputZipFile))) {
            while (true) {
                // reads the next ZIP file entry and positions the stream at the beginning of the entry data
                final ZipEntry nextZipEntry = zipInputStream.getNextEntry();

                if (nextZipEntry == null) {
                    // no more entries in the ZIP file
                    break;
                }

                // create newly extracted file
                final Path extractedFile = outputDirectory.resolve(nextZipEntry.getName());
                // extract file
                Files.copy(zipInputStream, extractedFile);
                // closes the current ZIP entry and positions the stream for reading the next entry
                zipInputStream.closeEntry();
            }
        }
    }
}
