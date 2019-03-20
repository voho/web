package sort;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

import static org.junit.Assert.assertArrayEquals;

public class SortTest {
    private static final int NUMBER_OF_RANDOM_CASES = 1000;
    private static final int RANDOM_MIN = -100;
    private static final int RANDOM_MAX = 100;
    private static final int RANDOM_MIN_LENGTH = 1;
    private static final int RANDOM_MAX_LENGTH = 50;

    private static final List<Consumer<Comparable[]>> ALGORITHMS = Arrays.asList(
            BubbleSort::bubbleSort,
            InsertionSort::insertionSort,
            SelectionSort::selectionSort,
            QuickSort::quickSort,
            MergeSort::mergeSort,
            GnomeSort::gnomeSort,
            input -> HeapSort.heapSort(input, true)
    );

    @Test
    public void testAllAlgorithms() {
        for (final Consumer<Comparable[]> algorithm : ALGORITHMS) {
            testAlgorithm(algorithm);
        }
    }

    private void testAlgorithm(final Consumer<Comparable[]> algorithm) {
        for (int i = 0; i < NUMBER_OF_RANDOM_CASES; i++) {
            final int[] randomCase = generateRandomCase();
            final int[] correctlySorted = sortUsingJavaLibrary(randomCase);
            final int[] customSorted = sortUsingCustomSorter(randomCase, algorithm);

            assertArrayEquals(
                    String.format("%s: incorrect sorter result for %s", algorithm.getClass().getSimpleName(), Arrays.toString(randomCase)),
                    correctlySorted,
                    customSorted
            );
        }
    }

    private int[] sortUsingCustomSorter(final int[] randomCase, final Consumer<Comparable[]> algorithm) {
        final Integer[] boxedArray = Arrays.stream(randomCase).boxed().toArray(Integer[]::new);
        algorithm.accept(boxedArray);
        return Arrays.stream(boxedArray).mapToInt(a -> a).toArray();
    }

    private int[] sortUsingJavaLibrary(final int[] randomCase) {
        final int[] result = Arrays.copyOf(randomCase, randomCase.length);
        Arrays.sort(result);
        return result;
    }

    private int[] generateRandomCase() {
        final int randomLength = generateRandom(RANDOM_MIN_LENGTH, RANDOM_MAX_LENGTH);
        final int[] randomArray = new int[randomLength];

        for (int i = 0; i < randomLength; i++) {
            randomArray[i] = generateRandom(RANDOM_MIN, RANDOM_MAX);
        }

        return randomArray;
    }

    private int generateRandom(final int min, final int max) {
        return min + ThreadLocalRandom.current().nextInt(max - min + 1);
    }
}
