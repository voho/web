package ds.trie;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TrieTest {
    private final Trie toTest = new GenericTrie();

    @Test
    public void testEmptyTrie() {
        assertTrue(toTest.containsPrefix(""));
        assertFalse(toTest.containsWord("a"));
        assertFalse(toTest.containsPrefix("a"));
        assertEquals(0, toTest.numWords());
        assertEquals(1, toTest.numPrefixes());
    }

    @Test
    public void testTrivialTrie() {
        toTest.addWord("a");
        assertTrue(toTest.containsWord("a"));
        assertTrue(toTest.containsPrefix("a"));
        assertEquals(1, toTest.numWords());
        assertEquals(2, toTest.numPrefixes());
    }

    @Test
    public void testSnailTrie() {
        toTest.addWord("a");
        toTest.addWord("ab");
        toTest.addWord("abc");
        assertTrue(toTest.containsWord("a"));
        assertTrue(toTest.containsWord("ab"));
        assertTrue(toTest.containsWord("abc"));
        assertTrue(toTest.containsPrefix("a"));
        assertTrue(toTest.containsPrefix("ab"));
        assertTrue(toTest.containsPrefix("abc"));
        assertEquals(3, toTest.numWords());
        assertEquals(4, toTest.numPrefixes());
    }

    @Test
    public void testNormalTrie() {
        toTest.addWord("hello");
        toTest.addWord("hell");
        toTest.addWord("halo");
        assertTrue(toTest.containsWord("hello"));
        assertTrue(toTest.containsWord("hell"));
        assertTrue(toTest.containsWord("halo"));
        assertTrue(toTest.containsPrefix("h"));
        assertTrue(toTest.containsPrefix("he"));
        assertTrue(toTest.containsPrefix("hel"));
        assertTrue(toTest.containsPrefix("hell"));
        assertTrue(toTest.containsPrefix("hello"));
        assertTrue(toTest.containsPrefix("ha"));
        assertTrue(toTest.containsPrefix("hal"));
        assertTrue(toTest.containsPrefix("halo"));
        assertFalse(toTest.containsWord("h"));
        assertFalse(toTest.containsWord("he"));
        assertFalse(toTest.containsWord("hel"));
        assertFalse(toTest.containsWord("ha"));
        assertFalse(toTest.containsWord("hal"));
        assertEquals(3, toTest.numWords());
        assertEquals(9, toTest.numPrefixes());
    }
}