package sort;

public class BitonicSort {
    public static <T extends Comparable<? super T>> void bitonicSort(final T[] input, final boolean ascending) {
        bitonicSort(input, 0, input.length, ascending);
    }

    private static <T extends Comparable<? super T>> void bitonicSort(final T[] input, final int start, final int inputLength, final boolean dir) {
        if (inputLength > 1) {
            final int middle = inputLength / 2;
            bitonicSort(input, start, middle, !dir);
            bitonicSort(input, start + middle, inputLength - middle, dir);
            bitonicMerge(input, start, inputLength, dir);
        }
    }

    private static <T extends Comparable<? super T>> void bitonicMerge(final T[] input, final int start, final int inputLength, final boolean dir) {
        if (inputLength > 1) {
            final int greatestPowerOf2 = greatestPowerOf2LessThan(inputLength);
            for (int i = start; i < start + inputLength - greatestPowerOf2; i++) {
                swapIfNecessary(input, i, i + greatestPowerOf2, dir);
            }
            bitonicMerge(input, start, greatestPowerOf2, dir);
            bitonicMerge(input, start + greatestPowerOf2, inputLength - greatestPowerOf2, dir);
        }
    }

    private static <T extends Comparable<? super T>> void swapIfNecessary(final T[] input, final int i, final int j, final boolean dir) {
        if (dir == (input[i].compareTo(input[j]) > 0)) {
            final T temp = input[i];
            input[i] = input[j];
            input[j] = temp;
        }
    }

    private static int greatestPowerOf2LessThan(final int inputLength) {
        int k = 1;
        while (k > 0 && k < inputLength) {
            k = k << 1;
        }
        return k >>> 1;
    }
}
