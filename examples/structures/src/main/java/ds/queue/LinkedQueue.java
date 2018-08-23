package ds.queue;

/**
 * Queue implemented using linked list.
 *
 * @author Vojtěch Hordějčuk
 */
public class LinkedQueue<DATA> implements Queue<DATA> {
    private static class Node<DATA> {
        private DATA innerValue;
        private Node<DATA> next;
    }

    private Node<DATA> first;
    private Node<DATA> last;

    @Override
    public void enqueue(final DATA value) {
        final Node<DATA> newLast = new Node<DATA>();
        newLast.innerValue = value;

        if (first == null) {
            // first element
            first = newLast;
            last = newLast;
        } else {
            last.next = newLast;
            last = newLast;
        }
    }

    @Override
    public DATA dequeue() {
        if (first == null) {
            throw new IllegalStateException("Queue is empty.");
        }

        final DATA valueToReturn = first.innerValue;

        if (first == last) {
            // last element
            first = null;
            last = null;
        } else {
            first = first.next;
        }

        return valueToReturn;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }
}
