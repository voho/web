package ds.heap;

public class ArrayHeap<T extends Comparable<? super T>> implements Heap<T> {
    private final Comparable[] array;
    private int size;

    public ArrayHeap(final int maxSize) {
        this.array = new Comparable[maxSize];
        this.size = 0;
    }

    @Override
    public void enqueue(final T value) {
        if (size >= array.length) {
            throw new IllegalStateException("Heap is already full.");
        }

        // put element to the end of the array
        array[size] = value;
        // fix the heap starting from the last element added
        fixHeapBottomUp(size);
        // increment the heap size
        size++;
    }

    @Override
    public T dequeueMinimum() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty.");
        }

        // the least element is always in the front
        final T min = (T) array[0];
        // remove the first element
        array[0] = null;
        // take the last element and move it to the front
        swap(0, size - 1);
        // make heap smaller
        size--;
        // fix the heap again
        fixHeapTopToBottom(0);

        return min;
    }

    @Override
    public T peekMinimum() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty.");
        }

        return (T) array[0];
    }

    @Override
    public int size() {
        return size;
    }

    private void fixHeapBottomUp(final int fixingIndex) {
        if (fixingIndex != 0) {
            final int fixingIndexParent = parent(fixingIndex);

            if (array[fixingIndex].compareTo(array[fixingIndexParent]) < 0) {
                // invalid order (bigger element above smaller one) - fix it
                swap(fixingIndex, fixingIndexParent);
                // continue fixing from the parent
                fixHeapBottomUp(fixingIndexParent);
            }
        }
    }

    private void fixHeapTopToBottom(final int rootIndex) {
        final int left = leftChild(rootIndex);
        final int right = rightChild(rootIndex);

        // find smallest between three elements: root, left child, right child
        int smallestIndex = rootIndex;

        if (left < size && array[left].compareTo(array[smallestIndex]) < 0) {
            smallestIndex = left;
        }
        if (right < size && array[right].compareTo(array[smallestIndex]) < 0) {
            smallestIndex = right;
        }

        if (smallestIndex != rootIndex) {
            // root was not the smallest, so swap it
            swap(rootIndex, smallestIndex);
            // and continue fixing the heap further down
            fixHeapTopToBottom(smallestIndex);
        }
    }

    // UTILITIES

    private int parent(final int pos) {
        return pos / 2;
    }

    private int leftChild(final int pos) {
        return (2 * pos);
    }

    private int rightChild(final int pos) {
        return (2 * pos) + 1;
    }

    private void swap(final int i1, final int i2) {
        final T backup = (T) array[i1];
        array[i1] = array[i2];
        array[i2] = backup;
    }
}
