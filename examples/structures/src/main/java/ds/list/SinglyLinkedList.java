package ds.list;

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
    public DATA get(int index) {
        SinglyLinkedList.Node<DATA> temp = head;
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
    public int size() {
        int count = 0;

        SinglyLinkedList.Node<DATA> temp = head;

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
