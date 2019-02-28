package ds.stack;

import org.junit.Test;

public class ArrayStackTest extends AbstractStackTest {
    private static final int MAX_CAPACITY = 3;

    @Override
    protected Stack<String> createInstance() {
        return new ArrayStack<>(MAX_CAPACITY);
    }

    @Test(expected = IllegalStateException.class)
    public void fullStack() {
        for (int i = 0; i < MAX_CAPACITY + 1; i++) {
            toTest.push("X");
        }
    }
}
