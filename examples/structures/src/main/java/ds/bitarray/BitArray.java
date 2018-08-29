package ds.bitarray;

/**
 * Interface of a fixed-size bit array. It allows you to set/unset/get bits at arbitrary indexes.
 */
public interface BitArray {
    /**
     * Returns the number of bits which can be stored in this array.
     *
     * @return number of bits stored in this array
     */
    int size();

    /**
     * Checks if the bit on the given index is set (equal to 1).
     *
     * @param index bit index (0 to size - 1, inclusive)
     * @return TRUE if the bit is set, FALSE otherwise
     */
    boolean get(int index);

    /**
     * Sets the bit on the given index to 1.
     *
     * @param index bit index (0 to size - 1, inclusive)
     */
    void set(int index);

    /**
     * Unsets the bit on the given index to 0.
     *
     * @param index bit index (0 to size - 1, inclusive)
     */
    void unset(int index);

    /**
     * Sets all bits to 1.
     */
    void setAll();

    /**
     * Unsets all bits to 0.
     */
    void unsetAll();
}
