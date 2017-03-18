## Komprese ZIP

Ve standardní knihovně jazyka Java se vyskytují i nástroje pro práci s [komprimovanými](wiki/kompresni-algoritmus) [vstupně-výstupními proudy](wiki/java-io-stream).
Jednotlivé záznamy (soubory) v archivu jsou reprezentovány třídou *javadoc:java.util.zip.ZipEntry* a proudy třídami *javadoc:java.util.zip.ZipInputStream* a *javadoc:java.util.zip.ZipOutputStream*.

### Komprese

```java
public class ZipStreamCompression {
    public void compressFiles(Iterable<Path> filesToCompress, Path outputZipFile) throws IOException {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(outputZipFile))) {
            for (Path fileToCompress : filesToCompress) {
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
}
```

### Dekomprese

```java
public class ZipStreamCompression {
    public void decompressFiles(Path inputZipFile, Path outputDirectory) throws IOException {
        try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(inputZipFile))) {
            while (true) {
                // reads the next ZIP file entry and positions the stream at the beginning of the entry data
                ZipEntry nextZipEntry = zipInputStream.getNextEntry();

                if (nextZipEntry == null) {
                    // no more entries in the ZIP file
                    break;
                }

                // create newly extracted file
                Path extractedFile = outputDirectory.resolve(nextZipEntry.getName());
                // extract file
                Files.copy(zipInputStream, extractedFile);
                // closes the current ZIP entry and positions the stream for reading the next entry
                zipInputStream.closeEntry();
            }
        }
    }
}
```

## Reference

- https://github.com/voho/examples/blob/master/zip/src/main/java/zip/ZipStreamCompression.java