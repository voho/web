package ds.list;

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
    public DATA get(int index) {
        Node<DATA> temp = head;
        for (int j = 0; j < index; j++) {
            if (temp != null) {
                temp = temp.next;
            } else {
                break;
            }
        }
        if (temp == null) {
            throw new IndexOutOfBoundsException(String.valueOf(index));
        }
        return temp.innerValue;
    }

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
    public int size() {
        int count = 0;

        Node<DATA> temp = head;

        while (temp != null) {
            count++;
            temp = temp.next;
        }

        return count;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }
}
