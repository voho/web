package ds.list;

import java.util.Iterator;

/**
 * Doubly iterable linked list with both head and tail.
 *
 * @param <DATA> type of inner data values
 * @author Vojtěch Hordějčuk
 */
public class DoublyLinkedList<DATA> implements List<DATA> {
    private static class Node<DATA> {
        private DATA innerValue = null;
        private Node<DATA> prev = null;
        private Node<DATA> next = null;
    }

    private Node<DATA> head = null;
    private Node<DATA> tail = null;

    @Override
    public void add(DATA value) {
        Node<DATA> newNode = new Node<DATA>();
        newNode.innerValue = value;

        if (head == null) {
            // empty list
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    @Override
    public boolean remove(DATA value) {
        Node<DATA> temp = head;

        while (temp != null) {
            if (temp.innerValue.equals(value)) {
                // fix "next" pointers
                if (temp == head) {
                    // move head
                    head = head.next;
                } else {
                    temp.prev.next = temp.next;
                }
                // fix "prev" pointers
                if (temp == tail) {
                    // move tail
                    tail = tail.prev;
                } else {
                    temp.next.prev = temp.prev;
                }
                return true;
            }

            temp = temp.next;
        }

        return false;
    }

    @Override
    public Iterator<DATA> iterator() {
        return new Iterator<DATA>() {
            private Node<DATA> next = head;

            @Override
            public boolean hasNext() {
                return next != null;
            }

            @Override
            public DATA next() {
                assert next != null;
                DATA nextValue = next.innerValue;
                next = next.next;
                return nextValue;
            }

            @Override
            public void remove() {
                // we can implement it, but better is to use the remove() method
                throw new UnsupportedOperationException();
            }
        };
    }
}
