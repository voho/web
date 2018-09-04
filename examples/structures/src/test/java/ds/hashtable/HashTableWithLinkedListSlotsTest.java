package ds.hashtable;

public class HashTableWithLinkedListSlotsTest extends AbstractHashTableTest {
    @Override
    protected HashTable<String> createInstance(final int capacity) {
        return new HashTableWithLinkedListSlots<>(capacity);
    }
}