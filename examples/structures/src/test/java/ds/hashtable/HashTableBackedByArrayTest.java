package ds.hashtable;

public class HashTableBackedByArrayTest extends AbstractHashTableTest {
    @Override
    protected HashTable<String> createInstance(final int capacity) {
        return new HashTableBackedByArray<>(capacity);
    }
}