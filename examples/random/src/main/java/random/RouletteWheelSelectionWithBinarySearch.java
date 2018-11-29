package random;

public class RouletteWheelSelectionWithBinarySearch {
    /**
     * Returns a random integer in range (0, weights.length-1), both inclusive.
     * Every integer n is selected with a probability that corresponds to its weight.
     * The weight of integer n is stored in as weights[n].
     * The weights can be an arbitrary numbers.
     * @param weights array of weights for each number (index = number)
     * @return random index or -1 if the input is empty
     */
    public static int randomWeightedInteger(final double[] weights) {
        if (weights.length == 0) {
            return -1;
        }

        final double[] cumulativeWeights = new double[weights.length + 1];
        double cumulativeWeight = 0;

        for (int i = 0; i < weights.length; i++) {
            cumulativeWeights[i] = cumulativeWeight;
            cumulativeWeight += weights[i];
        }

        final double randomRoulettePosition = Math.random() * cumulativeWeight;

        // as the cumulativeWeights array is sorted, we can use binary search
        return binarySearch(cumulativeWeights, randomRoulettePosition);
    }

    private static int binarySearch(final double[] values, final double key) {
        int start = 0;
        int end = values.length - 1;

        while (end - start > 1) {
            final int mid = (start + end) / 2;
            if (values[mid] > key) {
                // go to the left subtree
                end = mid;
            } else {
                // go to the right subtree
                start = mid;
            }
        }

        // we do not need an exact match, just the greatest lower bound
        return start;
    }
}
