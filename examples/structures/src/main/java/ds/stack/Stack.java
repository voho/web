package ds.stack;

/**
 * Interface of a stack.
 * @param <DATA> stack element type
 */
public interface Stack<DATA> {
    /**
     * Pushes a value onto the stack.
     * Some implementation might throw an exception if the stack is full.
     * @param value value to push
     */
    void push(final DATA value);

    /**
     * Pops the value from the top of the stack.
     * Throws an exception when the stack is empty.
     * @return popped value
     */
    DATA pop();

    /**
     * Checks whether the stack is empty.
     * @return TRUE if the stack is empty, FALSE otherwise
     */
    boolean isEmpty();
}
