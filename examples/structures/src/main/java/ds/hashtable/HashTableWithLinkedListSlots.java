package ds.hashtable;

public class HashTableWithLinkedListSlots<VALUE> implements HashTable<VALUE> {
    private static class Entry<VALUE> {
        private int innerKey;
        private VALUE innerValue;
        private Entry<VALUE> next;
    }

    private static class Slot<VALUE> {
        private Entry<VALUE> head;

        private void add(final int key, final VALUE value) {
            final Entry<VALUE> newEntry = new Entry<VALUE>();
            newEntry.innerKey = key;
            newEntry.innerValue = value;

            if (head == null) {
                // empty list
                head = newEntry;
            } else {
                Entry<VALUE> temp = head;

                while (temp.next != null) {
                    temp = temp.next;
                }

                temp.next = newEntry;
            }
        }

        private VALUE findByKey(final int key) {
            Entry<VALUE> temp = head;

            while (temp != null) {
                if (temp.innerKey == key) {
                    // value found
                    return temp.innerValue;
                }

                temp = temp.next;
            }

            return null;
        }

        private boolean removeByKey(final int key) {
            Entry<VALUE> temp = head;
            Entry<VALUE> prevOfTemp = null;

            while (temp != null) {
                if (temp.innerKey == key) {
                    if (prevOfTemp == null) {
                        // removing head
                        head = head.next;
                    } else {
                        // general case
                        prevOfTemp.next = temp.next;
                    }
                    return true;
                }

                prevOfTemp = temp;
                temp = temp.next;
            }

            return false;
        }
    }

    private final Slot<VALUE>[] slots;

    @SuppressWarnings("unchecked")
    public HashTableWithLinkedListSlots(final int numberOfSlots) {
        slots = new Slot[numberOfSlots];
    }

    @Override
    public void add(final int key, final VALUE value) {
        final Slot<VALUE> slot = getSlotForKey(key, true);
        slot.add(key, value);
    }

    @Override
    public VALUE findByKey(final int key) {
        final Slot<VALUE> slot = getSlotForKey(key, false);
        if (slot != null) {
            return slot.findByKey(key);
        }
        return null;
    }

    @Override
    public boolean removeByKey(final int key) {
        final Slot<VALUE> slot = getSlotForKey(key, false);
        if (slot != null) {
            return slot.removeByKey(key);
        }
        return false;
    }

    private Slot<VALUE> getSlotForKey(final int key, final boolean createIfMissing) {
        final int bucketIndex = getHash(key);
        Slot<VALUE> slot = slots[bucketIndex];
        if (slot == null && createIfMissing) {
            slot = new Slot<VALUE>();
            slots[bucketIndex] = slot;
        }
        return slot;
    }

    private int getHash(final int key) {
        // simple hash function
        return key % slots.length;
    }
}
