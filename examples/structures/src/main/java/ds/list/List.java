package ds.list;

public interface List<DATA> {
    /**
     * Gets element at a certain index.
     *
     * @param index index (starts with 0)
     * @return element at the given index
     */
    DATA get(int index);

    /**
     * Adds an element to the end of this list.
     *
     * @param value value to be added
     */
    void add(DATA value);

    /**
     * Removes a value from this list.
     *
     * @param value value to be removed
     * @return TRUE if the value was found and removed, FALSE otherwise
     */
    boolean remove(DATA value);

    /**
     * Returns the list size.
     *
     * @return list size
     */
    int size();

    /**
     * Checks whether the list is empty.
     *
     * @return TRUE if the list is empty, FALSE otherwise
     */
    boolean isEmpty();
}
