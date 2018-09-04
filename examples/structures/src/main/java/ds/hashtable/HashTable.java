package ds.hashtable;

/**
 * Interface for a hash table, where key is an integer (for simplicity).
 * @param <VALUE> value type
 */
public interface HashTable<VALUE> {
    /**
     * Adds a value with the given key. Allows more values with the same key.
     * @param key key
     * @param value value
     */
    void add(int key, VALUE value);

    /**
     * Finds a first value by the given key.
     * @param key key to find
     * @return first value found with this key or NULL
     */
    VALUE findByKey(int key);

    /**
     * Removes a first occurrence of a value with the given key.
     * @param key key to remove
     * @return TRUE if a value was found and removed, FALSE otherwise
     */
    boolean removeByKey(int key);
}
