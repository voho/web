package ds.hashtable;

public class HashTableBackedByArray<VALUE> implements HashTable<VALUE> {
    private static class HashEntry<DATA> {
        private int key;
        private DATA value;
    }

    private final HashEntry<VALUE>[] table;

    @SuppressWarnings("unchecked")
    public HashTableBackedByArray(final int tableSize) {
        table = new HashEntry[tableSize];
    }

    @Override
    public void add(final int key, final VALUE value) {
        final Integer firstExpectedIndexForKey = scanForNextFreeIndexByKey(key);

        if (firstExpectedIndexForKey == null) {
            throw new IllegalStateException("No free space in table.");
        }

        final HashEntry<VALUE> newEntry = new HashEntry<VALUE>();
        newEntry.key = key;
        newEntry.value = value;
        table[firstExpectedIndexForKey] = newEntry;
    }

    @Override
    public VALUE findByKey(final int key) {
        final Integer index = scanForIndexByKey(key);

        if (index != null) {
            return table[index].value;
        }

        return null;
    }

    @Override
    public boolean removeByKey(final int key) {
        final Integer index = scanForIndexByKey(key);

        if (index != null) {
            if (table[index] != null) {
                table[index] = null;
            }

            return true;
        }

        return false;
    }

    private Integer scanForNextFreeIndexByKey(final int key) {
        int index = getKeyHash(key);

        for (int i = 0; i < table.length; i++) {
            if (table[index] == null) {
                // empty cell found
                return index;
            }

            // continue and wrap around
            index = (index + 1) % table.length;
        }

        return null;
    }

    private Integer scanForIndexByKey(final int key) {
        int index = getKeyHash(key);

        for (int i = 0; i < table.length; i++) {
            if (table[index] != null && table[index].key == key) {
                // non-empty cell with correct key found
                return index;
            }

            // continue and wrap around
            index = (index + 1) % table.length;
        }

        return null;
    }

    private int getKeyHash(final int key) {
        return (key % table.length);
    }
}
