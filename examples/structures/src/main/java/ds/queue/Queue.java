package ds.queue;

public interface Queue<DATA> {
    /**
     * Enqueues a value by placing it at the end of the queue.
     *
     * @param value value to be added
     */
    void enqueue(DATA value);

    /**
     * Dequeues (retrieves and removes) the oldest value in the queue.
     * Throws exception if the queue is empty.
     *
     * @return the oldest value in the queue
     */
    DATA dequeue();

    /**
     * Checks if the queue is empty.
     *
     * @return TRUE if the queue is empty, FALSE otherwise
     */
    boolean isEmpty();
}
