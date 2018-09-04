package ds.hashtable;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@Ignore
abstract class AbstractHashTableTest {
    static final int CAPACITY = 100;
    static final int TEST_KEY = 1;
    static final String TEST_VALUE = "Hello world!";
    static final String ANOTHER_TEST_VALUE = "Where are you?";

    HashTable<String> toTest = createInstance(CAPACITY);

    protected abstract HashTable<String> createInstance(final int capacity);

    @Test
    public void testSingleValue() {
        assertNull(toTest.findByKey(TEST_KEY));
        toTest.add(TEST_KEY, TEST_VALUE);
        assertEquals(TEST_VALUE, toTest.findByKey(TEST_KEY));
        assertTrue(toTest.removeByKey(TEST_KEY));
        assertFalse(toTest.removeByKey(TEST_KEY));
        assertNull(toTest.findByKey(TEST_KEY));
    }

    @Test
    public void testCanAddItemsUpToCapacity() {
        for (int key = 0; key < CAPACITY; key++) {
            final String value = String.valueOf(key);
            toTest.add(key, value);
        }
    }

    @Test
    public void testRemovingOccurrence() {
        toTest.add(TEST_KEY, TEST_VALUE);
        toTest.add(TEST_KEY, ANOTHER_TEST_VALUE);
        assertEquals(TEST_VALUE, toTest.findByKey(TEST_KEY));
        assertTrue(toTest.removeByKey(TEST_KEY));
        assertEquals(ANOTHER_TEST_VALUE, toTest.findByKey(TEST_KEY));
        assertTrue(toTest.removeByKey(TEST_KEY));
        assertFalse(toTest.removeByKey(TEST_KEY));
    }
}
