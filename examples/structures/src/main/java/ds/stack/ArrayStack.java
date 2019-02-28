package ds.stack;

/**
 * Array based stack with maximum capacity.
 * @author Vojtěch Hordějčuk
 */
public class ArrayStack<DATA> implements Stack<DATA> {
    private final DATA[] storage;
    private int top;

    /**
     * Creates a new instance.
     * @param capacity stack capacity (maximum number of elements to store)
     */
    public ArrayStack(final int capacity) {
        this.storage = (DATA[]) new Object[capacity];
        this.top = -1;
    }

    /**
     * Adds an element on the top of the stack.
     * @param value data to push
     */
    @Override
    public void push(final DATA value) {
        if (top == storage.length - 1) {
            throw new IllegalStateException("Stack overflow.");
        }

        storage[++top] = value;
    }

    /**
     * Pops an element from the stack top.
     * Throws exception if no element is present.
     * @return popped element (former stack top)
     */
    @Override
    public DATA pop() {
        if (top == -1) {
            throw new IllegalStateException("Stack underflow.");
        }

        return storage[top--];
    }

    /**
     * Checks if the stack is empty.
     * @return TRUE if the stack is empty, FALSE otherwise
     */
    @Override
    public boolean isEmpty() {
        return top == -1;
    }
}
