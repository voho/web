package ds.list;

import java.util.Iterator;

/**
 * Singly iterable linked list with head pointer only.
 *
 * @param <DATA> type of inner data values
 * @author Vojtěch Hordějčuk
 */
public class SinglyLinkedList<DATA> implements List<DATA> {
    private static class Node<DATA> {
        private DATA innerValue = null;
        private Node<DATA> next = null;
    }

    private Node<DATA> head = null;

    @Override
    public void add(DATA value) {
        Node<DATA> newNode = new Node<DATA>();
        newNode.innerValue = value;

        if (head == null) {
            // empty list
            head = newNode;
        } else {
            Node<DATA> temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
    }

    @Override
    public boolean remove(DATA value) {
        Node<DATA> temp = head;
        Node<DATA> prevOfTemp = null;

        while (temp != null) {
            if (temp.innerValue.equals(value)) {
                if (prevOfTemp == null) {
                    // removing head
                    head = head.next;
                    return true;
                } else {
                    prevOfTemp.next = temp.next;
                    return true;
                }
            }

            prevOfTemp = temp;
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
