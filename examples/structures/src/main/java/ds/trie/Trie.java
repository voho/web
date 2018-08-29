package ds.trie;

public interface Trie {
    /**
     * Returns the number of words stored in the trie.
     * @return number of words in the trie
     */
    default int numWords() {
        return numWords(new char[0]);
    }

    /**
     * Returns the number of prefixes stored in the trie.
     * @return number of prefixes in the trie
     */
    default int numPrefixes() {
        return numPrefixes(new char[0]);
    }

    /**
     * Checks if the trie contains the given key.
     * @param key key to check
     * @return TRUE if the key is present, FALSE otherwise
     */
    boolean containsWord(char[] key);

    /**
     * Checks if the trie contains the given key as prefix.
     * @param keyPrefix key prefix to check
     * @return TRUE if the key is present as prefix, FALSE otherwise
     */
    boolean containsPrefix(char[] keyPrefix);

    int numWords(char[] key);

    int numPrefixes(char[] key);

    /**
     * Adds key to the trie.
     * @param key key to add
     */
    void addWord(char[] key);

    // CONVENIENCE METHODS FOR STRINGS

    default void addWord(final String key) {
        addWord(key.toCharArray());
    }

    default boolean containsWord(final String key) {
        return containsWord(key.toCharArray());
    }

    default boolean containsPrefix(final String key) {
        return containsPrefix(key.toCharArray());
    }
}
