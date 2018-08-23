package ds.queue;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

@Ignore
abstract class AbstractQueueTest {
    Queue<String> toTest = createInstance();

    protected abstract Queue<String> createInstance();

    @Test
    public void emptyQueue() {
        assertTrue(toTest.isEmpty());
    }

    @Test
    public void testItemOrder() {
        toTest.enqueue("A");
        toTest.enqueue("B");
        toTest.enqueue("C");

        assertEquals("A", toTest.dequeue());
        assertEquals("B", toTest.dequeue());
        assertEquals("C", toTest.dequeue());
    }

    @Test(expected = IllegalStateException.class)
    public void testCannotDequeueEmptyQueue() {
        toTest.dequeue();
    }

    @Test
    public void singleQueueBecomesEmpty() {
        toTest.enqueue("A");

        assertFalse(toTest.isEmpty());
        assertEquals("A", toTest.dequeue());

        assertTrue(toTest.isEmpty());
    }

    @Test(expected = IllegalStateException.class)
    public void dequeueEmptyQueue() {
        toTest.dequeue();
    }
}