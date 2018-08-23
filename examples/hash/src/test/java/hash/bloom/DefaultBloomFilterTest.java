package hash.bloom;

import org.junit.Before;
import org.junit.Test;

import java.util.function.Function;

import static org.junit.Assert.*;

public class DefaultBloomFilterTest {
    private BloomFilter<String> filter;

    @Before
    public void before() {
        final Function<String, Integer> hash1 = s -> s.toLowerCase().hashCode();
        final Function<String, Integer> hash2 = s -> s.toUpperCase().hashCode();

        filter = new DefaultBloomFilter<>(10, hash1, hash2);
    }

    @Test
    public void testGetOptimalNumberOfHashFunctions() {
        assertEquals(1, BloomFilter.getOptimalNumberOfHashFunctions(0, 0));
        assertEquals(1, BloomFilter.getOptimalNumberOfHashFunctions(1, 0));
        assertEquals(1, BloomFilter.getOptimalNumberOfHashFunctions(0, 1));
        assertEquals(1, BloomFilter.getOptimalNumberOfHashFunctions(1, 1));
        assertEquals(7, BloomFilter.getOptimalNumberOfHashFunctions(3072, 319));
        assertEquals(7, BloomFilter.getOptimalNumberOfHashFunctions(100, 10));
        assertEquals(3, BloomFilter.getOptimalNumberOfHashFunctions(50, 10));
        assertEquals(6931, BloomFilter.getOptimalNumberOfHashFunctions(100000, 10));
        assertEquals(1, BloomFilter.getOptimalNumberOfHashFunctions(64, 1000000));
        assertEquals(1, BloomFilter.getOptimalNumberOfHashFunctions(1000, 1000000));
        assertEquals(1, BloomFilter.getOptimalNumberOfHashFunctions(1000000, 10000000));
    }

    @Test
    public void testGetOptimalNumberOfBits() {
        assertEquals(1, BloomFilter.getOptimalNumberOfBits(0, 0));
        assertEquals(1, BloomFilter.getOptimalNumberOfBits(0, 1));
        assertEquals(1549, BloomFilter.getOptimalNumberOfBits(1, 0));
        assertEquals(1442, BloomFilter.getOptimalNumberOfBits(1000, 0.5));
        assertEquals(2885, BloomFilter.getOptimalNumberOfBits(1000, 0.25));
        assertEquals(4792, BloomFilter.getOptimalNumberOfBits(1000, 0.1));
        assertEquals(1549454, BloomFilter.getOptimalNumberOfBits(1000, 0));
        assertEquals(1442695, BloomFilter.getOptimalNumberOfBits(1000000, 0.5));
        assertEquals(2885390, BloomFilter.getOptimalNumberOfBits(1000000, 0.25));
        assertEquals(4792529, BloomFilter.getOptimalNumberOfBits(1000000, 0.1));
        assertEquals(1549454473, BloomFilter.getOptimalNumberOfBits(1000000, 0));
    }

    @Test
    public void testExpectedFalsePositiveProbability() {
        assertEquals(0.0, filter.expectedFalsePositiveProbability(), 0.001);
        filter.add("one");
        assertEquals(0.04, filter.expectedFalsePositiveProbability(), 0.001);
        filter.add("two");
        assertEquals(0.09, filter.expectedFalsePositiveProbability(), 0.001);
        filter.add("three");
        assertEquals(0.09, filter.expectedFalsePositiveProbability(), 0.001);
        filter.add("four");
        assertEquals(0.16, filter.expectedFalsePositiveProbability(), 0.001);
        filter.add("five");
        assertEquals(0.25, filter.expectedFalsePositiveProbability(), 0.001);
    }

    @Test
    public void testEmptyFilter() {
        assertFalse(filter.probablyContains("hello"));
        assertFalse(filter.probablyContains("world"));
        assertEquals(0, filter.getNumberOfActiveBits());
        assertEquals(10, filter.getNumberOfBits());
        assertEquals(2, filter.getNumberOfHashFunctions());
    }

    @Test
    public void testSingleElement() {
        filter.add("hello");

        assertTrue(filter.probablyContains("hello"));
        assertTrue(filter.probablyContains("world")); // FALSE positive
        assertFalse(filter.probablyContains("super"));
        assertEquals(1, filter.getNumberOfActiveBits());
        assertEquals(10, filter.getNumberOfBits());
        assertEquals(2, filter.getNumberOfHashFunctions());
    }

    @Test
    public void testToString() {
        assertEquals("0000000000", filter.toString());
        filter.add("one");
        filter.add("two");
        filter.add("three");
        filter.add("four");
        filter.add("five");
        assertEquals("1010101010", filter.toString());
    }
}