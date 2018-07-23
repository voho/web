import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class LZ77Test {
    private final List<Integer> sizesBack = Arrays.asList(1, 5, 10, 100, 1000);
    private final List<Integer> sizesFront = Arrays.asList(1, 3, 10, 80, 500, 1100);

    @Test
    public void testAll() {
        testEncodingAndDecoding("");
        testEncodingAndDecoding("a");
        testEncodingAndDecoding("aaaaaa");
        testEncodingAndDecoding("abaabbabaacaacabb");
        testEncodingAndDecoding("MAMA MELE MASO. MASO MELE MAMU.");
        testEncodingAndDecoding("JELENOVI PIVO NELEJ");
        testEncodingAndDecoding("JEDE JEDE POSTOVSKY PANACEK");
        testEncodingAndDecoding("Žlutý pes úpěl ďábelské ódy.");
        testEncodingAndDecoding(IntStream.range(0, 1000).mapToObj(String::valueOf).reduce((a, b) -> a + b).get());
    }

    @Test
    public void testEncoding() {
        final String value = "abaabbabaacaacabb";
        final List<LZ77Codeword> encoded = LZ77.encode(value.toCharArray(), 6, 5);
        assertEquals("(0,0,a)", encoded.get(0).toString());
        assertEquals("(0,0,b)", encoded.get(1).toString());
        assertEquals("(2,1,a)", encoded.get(2).toString());
        assertEquals("(3,1,b)", encoded.get(3).toString());
        assertEquals("(6,4,c)", encoded.get(4).toString());
        assertEquals("(3,4,b)", encoded.get(5).toString());
        assertEquals("(1,1,<eof>)", encoded.get(6).toString());
        assertEquals(7, encoded.size());
    }

    @Test
    public void testDecoding() {
        final List<LZ77Codeword> encoded = new LinkedList<>();
        encoded.add(new LZ77Codeword(0, 0, 'a'));
        encoded.add(new LZ77Codeword(0, 0, 'b'));
        encoded.add(new LZ77Codeword(2, 1, 'a'));
        encoded.add(new LZ77Codeword(3, 1, 'b'));
        encoded.add(new LZ77Codeword(6, 4, 'c'));
        encoded.add(new LZ77Codeword(3, 4, 'b'));
        encoded.add(new LZ77Codeword(1, 1, (char) 0));
        final String expected = "abaabbabaacaacabb";
        final String actual = new String(LZ77.decode(encoded));
        assertEquals(expected, actual);
    }

    private void testEncodingAndDecoding(final String original) {
        for (final int sizeBack : sizesBack) {
            for (final int sizeFront : sizesFront) {
                final List<LZ77Codeword> encoded = LZ77.encode(original.toCharArray(), sizeBack, sizeFront);
                final char[] decoded = LZ77.decode(encoded);
                assertEquals(original, new String(decoded));
            }
        }
    }
}
