package ds.stack;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

@Ignore
abstract class AbstractStackTest {
    Stack<String> toTest = createInstance();

    protected abstract Stack<String> createInstance();

    @Test
    public void emptyStack() {
        assertTrue(toTest.isEmpty());
    }

    @Test
    public void testItemOrder() {
        toTest.push("A");
        toTest.push("B");
        toTest.push("C");

        assertEquals("C", toTest.pop());
        assertEquals("B", toTest.pop());
        assertEquals("A", toTest.pop());
    }

    @Test(expected = IllegalStateException.class)
    public void testCannotPopEmptyStack() {
        toTest.pop();
    }

    @Test
    public void singleStackBecomesEmpty() {
        toTest.push("A");

        assertFalse(toTest.isEmpty());
        assertEquals("A", toTest.pop());

        assertTrue(toTest.isEmpty());
    }

    @Test(expected = IllegalStateException.class)
    public void popEmptyStack() {
        toTest.pop();
    }
}