package ds.stack;

/**
 * Stack implemented using linked list.
 * @author Vojtěch Hordějčuk
 */
public class LinkedStack<DATA> implements Stack<DATA> {
    private static class Node<DATA> {
        private DATA innerValue;
        private Node<DATA> next;
    }

    private Node<DATA> top;

    /**
     * Adds an element on the top of the stack.
     * @param value data to push
     */
    @Override
    public void push(final DATA value) {
        final Node<DATA> newTop = new Node<DATA>();
        newTop.innerValue = value;
        newTop.next = top;
        top = newTop;
    }

    /**
     * Pops an element from the stack top.
     * Throws exception if no element is present.
     * @return popped element (former stack top)
     */
    @Override
    public DATA pop() {
        if (top != null) {
            final DATA topValue = top.innerValue;
            top = top.next;
            return topValue;
        } else {
            throw new IllegalStateException("Stack underflow.");
        }
    }

    /**
     * Checks if the stack is empty.
     * @return TRUE if the stack is empty, FALSE otherwise
     */
    @Override
    public boolean isEmpty() {
        return top == null;
    }
}
