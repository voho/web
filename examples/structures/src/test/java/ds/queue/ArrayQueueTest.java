package ds.queue;

import org.junit.Test;

public class ArrayQueueTest extends AbstractQueueTest {
    private static final int MAX_CAPACITY = 3;

    @Override
    protected Queue<String> createInstance() {
        return new ArrayQueue<>(MAX_CAPACITY);
    }

    @Test(expected = IllegalStateException.class)
    public void fullQueue() {
        for (int i = 0; i < MAX_CAPACITY + 1; i++) {
            toTest.enqueue("X");
        }
    }
}
