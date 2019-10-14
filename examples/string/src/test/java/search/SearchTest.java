package search;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SearchTest {
    @Test
    public void test() {
        assertUsingAllMethods(-1, "x", "xx");
        assertUsingAllMethods(-1, "hello world", "wox");
        assertUsingAllMethods(0, "xx", "x");
        assertUsingAllMethods(0, "hello world", "h");
        assertUsingAllMethods(0, "hello world", "hello");
        assertUsingAllMethods(0, "hello world", "hello world");
        assertUsingAllMethods(10, "hello world", "d");
        assertUsingAllMethods(6, "hello world", "world");
        assertUsingAllMethods(6, "hello world", "w");
        assertUsingAllMethods(4, "zvětšení", "š");
    }

    private void assertUsingAllMethods(final int expectedIndex, final String haystack, final String needle) {
        assertEquals(expectedIndex, KarpRabin.find(haystack.toCharArray(), needle.toCharArray()));
        assertEquals(expectedIndex, Bitap.find(haystack.toCharArray(), needle.toCharArray()));
        assertEquals(expectedIndex, BoyerMooreHorspool.find(haystack.toCharArray(), needle.toCharArray()));
    }
}
