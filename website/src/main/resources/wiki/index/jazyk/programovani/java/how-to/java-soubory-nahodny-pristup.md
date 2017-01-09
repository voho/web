## Náhodný přístup k souborům

Jak v Javě číst a zapisovat binární soubory po blocích? To se může hodit například při stahování jednoho souboru po částech z více zdrojů. K tomuto účelu je vhodná třída *SeekableByteChannel*, kterou lze vytvořit tovární metodou *Files.newByteChannel*. Její nejdůležitější metody jsou:

- *position* = přesune kurzor na zadanou pozici v souboru (udává se v bajtech)
- *read* = naplní buffer bajty přečtenými ze souboru (od aktuální pozice až do rezervované délky bufferu)
- *write* = zapíše celý obsah bufferu do souboru (od aktuální pozice až do délky bufferu)

A tady je ukázka jednoduché služby pro náhodný zápis bloků (chunků) binárních dat, která by se dala využít třeba v nějakém jednoduchém torrent klientovi:

```java
public class BasicInputOutputService {
    private final Logger log = LoggerFactory.getLogger(getClass());
 
    public byte[] readBinaryChunk(final Path path, final long fileSize, final int chunkIndex, final int chunkSize) throws ErrorReadingChunkException {
        final long offset = calculateOffset(chunkIndex, chunkSize);
        final int chunkSizeAdjusted = adjustChunkSize(offset, chunkSize, fileSize);
        log.info("Reading bytes {} .. {} from {}...", offset, offset + chunkSizeAdjusted - 1, path);
 
        try (SeekableByteChannel channel = createReadableChannel(path)) {
            final ByteBuffer buffer = ByteBuffer.allocate(chunkSizeAdjusted);
            channel.position(offset);
            channel.read(buffer);
            return buffer.array();
        } catch (final IOException e) {
            log.error("Error while reading binary chunk.", e);
            throw new ErrorReadingChunkException(path, offset, chunkSizeAdjusted, e);
        }
    }
 
    public void writeBinaryChunk(final Path path, final long fileSize, final int chunkIndex, final int chunkSize, final byte[] data) throws ErrorWritingChunkException {
        final long offset = calculateOffset(chunkIndex, chunkSize);
        final int chunkSizeAdjusted = adjustChunkSize(offset, chunkSize, fileSize);
        log.info("Writing bytes {} .. {} to {}...", offset, offset + chunkSizeAdjusted - 1, path);
 
        try (SeekableByteChannel channel = createWritableChannel(path)) {
            final ByteBuffer buffer = ByteBuffer.wrap(data, 0, chunkSizeAdjusted);
            channel.position(offset);
            channel.write(buffer);
        } catch (final IOException e) {
            log.error("Error while writing binary chunk.", e);
            throw new ErrorWritingChunkException(path, offset, chunkSizeAdjusted, e);
        }
    }
 
    private SeekableByteChannel createWritableChannel(final Path path) throws IOException {
        return Files.newByteChannel(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
    }
 
    private SeekableByteChannel createReadableChannel(final Path path) throws IOException {
        return Files.newByteChannel(path, StandardOpenOption.READ);
    }
 
    private int adjustChunkSize(final long offset, final int chunkSize, final long fileSize) {
        if (offset + chunkSize > fileSize) {
            // last chunk: its size must be truncated
            return (int) (fileSize % chunkSize);
        } else {
            return chunkSize;
        }
    }
 
    private long calculateOffset(final int chunkIndex, final int chunkSize) {
        return chunkIndex * chunkSize;
    }
}
```