package ds.list;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

@Ignore
abstract class AbstractListTest {
    List<String> toTest = createInstance();

    protected abstract List<String> createInstance();

    @Test
    public void emptyListSize() {
        assertTrue(toTest.isEmpty());
        assertEquals(0, toTest.size());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void cannotGetFirstElementOfEmptyList() {
        toTest.get(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void cannotGetPositiveElementOfEmptyList() {
        toTest.get(1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void cannotGetNegativeElementOfEmptyList() {
        toTest.get(-1);
    }

    @Test
    public void regularList() {
        toTest.add("A");
        toTest.add("B");
        toTest.add("C");

        assertFalse(toTest.isEmpty());
        assertEquals(3, toTest.size());
        assertEquals("A", toTest.get(0));
        assertEquals("B", toTest.get(1));
        assertEquals("C", toTest.get(2));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void regularListWithInvalidPositiveIndex() {
        toTest.add("A");
        toTest.add("B");
        toTest.add("C");

        toTest.get(3);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void regularListWithInvalidNegativeIndex() {
        toTest.add("A");
        toTest.add("B");
        toTest.add("C");

        toTest.get(3);
    }

    @Test
    public void regularListRemovingFirst() {
        toTest.add("A");
        toTest.add("B");
        toTest.add("C");

        assertTrue(toTest.remove("A"));

        assertFalse(toTest.isEmpty());
        assertEquals(2, toTest.size());
        assertEquals("B", toTest.get(0));
        assertEquals("C", toTest.get(1));
    }

    @Test
    public void regularListRemovingLast() {
        toTest.add("A");
        toTest.add("B");
        toTest.add("C");

        assertTrue(toTest.remove("C"));

        assertFalse(toTest.isEmpty());
        assertEquals(2, toTest.size());
        assertEquals("A", toTest.get(0));
        assertEquals("B", toTest.get(1));
    }

    @Test
    public void regularListRemovingMiddle() {
        toTest.add("A");
        toTest.add("B");
        toTest.add("C");

        assertTrue(toTest.remove("B"));

        assertFalse(toTest.isEmpty());
        assertEquals(2, toTest.size());
        assertEquals("A", toTest.get(0));
        assertEquals("C", toTest.get(1));
    }

    @Test
    public void regularListRemovingNonExisting() {
        toTest.add("A");
        toTest.add("B");
        toTest.add("C");

        assertFalse(toTest.remove("X"));

        assertFalse(toTest.isEmpty());
        assertEquals(3, toTest.size());
        assertEquals("A", toTest.get(0));
        assertEquals("B", toTest.get(1));
        assertEquals("C", toTest.get(2));
    }
}