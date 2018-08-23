package ds.queue;

/**
 * Queue implementation using array.
 *
 * @author Vojtěch Hordějčuk
 */
public class ArrayQueue<DATA> implements Queue<DATA> {
    private final DATA[] storage;
    private int top;
    private int bottom;

    public ArrayQueue(final int capacity) {
        this.storage = (DATA[]) new Object[capacity];
        this.top = -1;
        this.bottom = -1;
    }

    @Override
    public void enqueue(final DATA value) {
        if (top < 0) {
            // first element
            top = 0;
            bottom = 0;
            storage[0] = value;
        } else {
            final int bottomToBe = (bottom + 1) % storage.length;

            if (bottomToBe == top) {
                throw new IllegalStateException("Queue is full.");
            }

            bottom = bottomToBe;
            storage[bottom] = value;
        }
    }

    @Override
    public DATA dequeue() {
        if (top < 0) {
            throw new IllegalStateException("Queue is empty.");
        } else if (top == bottom) {
            // last element
            final DATA valueTuReturn = storage[top];
            storage[top] = null;
            top = -1;
            bottom = -1;
            return valueTuReturn;
        } else {
            final DATA valueTuReturn = storage[top];
            storage[top] = null;
            top = (top + 1) % storage.length;
            return valueTuReturn;
        }
    }

    @Override
    public boolean isEmpty() {
        return top == -1;
    }
}