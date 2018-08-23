package ds.skiplist;

import java.util.Optional;

/**
 * Skip list interface.
 */
public interface SkipList<K extends Comparable<? super K>, V> {
    /**
     * Gets a value by its key.
     *
     * @param key key to search for
     * @return value found (or nothing)
     */
    Optional<V> get(K key);

    /**
     * Checks if there is a mapping for the specified key.
     *
     * @param key key to search for
     * @return TRUE if there is a mapping for that key, FALSE otherwise
     */
    default boolean contains(final K key) {
        return get(key).isPresent();
    }

    /**
     * Saves a value under the given key.
     * Could a value already exists for the given key, it would be overwritten.
     *
     * @param key   key
     * @param value value
     */
    void insert(K key, V value);

    /**
     * Removes the value stored for the given key.
     *
     * @param key key
     * @return TRUE if the key was found and value removed, FALSE if nothing was found
     */
    boolean delete(K key);
}
