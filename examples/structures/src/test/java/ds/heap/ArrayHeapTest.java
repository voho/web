package ds.heap;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ArrayHeapTest {
    private final static int MAX_SIZE = 1000;

    private final Heap<Integer> toTest = new ArrayHeap<>(MAX_SIZE);

    @Test
    public void testEmptyQueue() {
        assertThat(toTest.size())
                .isZero();
        assertThatThrownBy(toTest::peekMinimum)
                .isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(toTest::dequeueMinimum)
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void testSingleElement() {
        toTest.enqueue(42);

        assertThat(toTest.size())
                .isEqualTo(1);
        assertThat(toTest.peekMinimum())
                .isEqualTo(42);
        assertThat(toTest.dequeueMinimum())
                .isEqualTo(42);
    }

    @Test
    public void testTwoElementsInWrongOrder() {
        toTest.enqueue(42);
        toTest.enqueue(5);

        assertThat(toTest.size())
                .isEqualTo(2);
        assertThat(toTest.peekMinimum())
                .isEqualTo(5);
        assertThat(toTest.dequeueMinimum())
                .isEqualTo(5);
        assertThat(toTest.peekMinimum())
                .isEqualTo(42);
        assertThat(toTest.dequeueMinimum())
                .isEqualTo(42);
        assertThat(toTest.size())
                .isZero();
    }

    @Test
    public void testFullArray() {
        final Heap<Integer> smallHeap = new ArrayHeap<>(3);
        smallHeap.enqueue(1);
        smallHeap.enqueue(2);
        smallHeap.enqueue(3);

        assertThatThrownBy(() -> smallHeap.enqueue(4))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void testRandomArray() {
        final List<Integer> data = new ArrayList<>(MAX_SIZE);

        for (int i = 0; i < MAX_SIZE; i++) {
            data.add(i);
        }

        Collections.shuffle(data);

        data.forEach(toTest::enqueue);

        assertThat(toTest.size())
                .isEqualTo(MAX_SIZE);

        for (int i = 0; i < MAX_SIZE; i++) {
            assertThat(toTest.peekMinimum())
                    .isEqualTo(i);
            assertThat(toTest.dequeueMinimum())
                    .isEqualTo(i);
        }

        assertThat(toTest.size())
                .isZero();
    }
}