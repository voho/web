package ds.bitarray;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BitArrayTest {
    @Test
    public void testToString() {
        final BitArray bits = new DefaultBitArray(10);

        bits.set(2);
        bits.set(6);
        bits.set(3);
        bits.set(8);

        assertEquals("0011001010", bits.toString());

        bits.setAll();
        assertEquals("1111111111", bits.toString());

        bits.unsetAll();
        assertEquals("0000000000", bits.toString());
    }

    @Test
    public void testIdempotence() {
        final int repeats = 5;
        final BitArray bits = new DefaultBitArray(10);

        for (int i = 0; i < repeats; i++) {
            bits.set(2);
            assertTrue(bits.get(2));
        }

        for (int i = 0; i < repeats; i++) {
            bits.unset(2);
            assertFalse(bits.get(2));
        }

        for (int i = 0; i < repeats; i++) {
            bits.setAll();
            for (int j = 0; j < bits.size(); j++) {
                assertTrue(bits.get(j));
            }
        }

        for (int i = 0; i < repeats; i++) {
            bits.unsetAll();
            for (int j = 0; j < bits.size(); j++) {
                assertFalse(bits.get(j));
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRangeTooHigh() {
        final BitArray bits = new DefaultBitArray(3);
        bits.get(3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRangeTooLow() {
        final BitArray bits = new DefaultBitArray(3);
        bits.get(-1);
    }

    @Test
    public void testVariousSizes() {
        testArray(0);
        testArray(1);
        testArray(5);
        testArray(Byte.SIZE);
        testArray(Short.SIZE);
        testArray(Integer.SIZE);
        testArray(Long.SIZE);
        testArray(1 + Byte.SIZE);
        testArray(1 + Short.SIZE);
        testArray(1 + Integer.SIZE);
        testArray(1 + Long.SIZE);
        testArray(1000);
        testArray(10000);
        testArray(100000);
        testArray(1000000);
        testArray(10000000);
    }

    private void testArray(final int size) {
        final BitArray bits = new DefaultBitArray(size);

        // set all - on by one

        for (int i = 0; i < size; i++) {
            assertFalse(bits.get(i));
            bits.set(i);
            assertTrue(bits.get(i));
        }

        // unset all - on by one

        for (int i = 0; i < size; i++) {
            assertTrue(bits.get(i));
            bits.unset(i);
            assertFalse(bits.get(i));
        }

        // reset all

        bits.unsetAll();

        for (int i = 0; i < size; i++) {
            assertFalse(bits.get(i));
        }

        // set all

        bits.setAll();

        for (int i = 0; i < size; i++) {
            assertTrue(bits.get(i));
        }
    }
}
