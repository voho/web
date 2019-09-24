package ds.heap;

public interface Heap<T extends Comparable<? super T>> {
    void enqueue(T value);

    T dequeueMinimum();

    T peekMinimum();

    int size();
}
