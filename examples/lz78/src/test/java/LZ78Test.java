import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class LZ78Test {
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
        final String value = "abbcbcababcaabcaab";
        final List<LZ78Codeword> encoded = LZ78.encode(value.toCharArray());
        assertEquals(7, encoded.size());
        assertEquals("(0,a)", encoded.get(0).toString());
        assertEquals("(0,b)", encoded.get(1).toString());
        assertEquals("(2,c)", encoded.get(2).toString());
        assertEquals("(3,a)", encoded.get(3).toString());
        assertEquals("(2,a)", encoded.get(4).toString());
        assertEquals("(4,a)", encoded.get(5).toString());
        assertEquals("(6,b)", encoded.get(6).toString());
    }

    @Test
    public void testDecoding() {
        final List<LZ78Codeword> encoded = new LinkedList<>();
        encoded.add(new LZ78Codeword(0, 'a'));
        encoded.add(new LZ78Codeword(0, 'b'));
        encoded.add(new LZ78Codeword(2, 'c'));
        encoded.add(new LZ78Codeword(3, 'a'));
        encoded.add(new LZ78Codeword(2, 'a'));
        encoded.add(new LZ78Codeword(4, 'a'));
        encoded.add(new LZ78Codeword(6, 'b'));
        final String expected = "abbcbcababcaabcaab";
        final String actual = new String(LZ78.decode(encoded));
        assertEquals(expected, actual);
    }

    private void testEncodingAndDecoding(final String original) {
        final List<LZ78Codeword> encoded = LZ78.encode(original.toCharArray());
        final char[] decoded = LZ78.decode(encoded);
        assertEquals(original, new String(decoded));
    }
}
