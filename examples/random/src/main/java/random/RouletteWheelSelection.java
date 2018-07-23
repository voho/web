package random;

public class RouletteWheelSelection {
    /**
     * Returns a random integer in range (0, weights.length-1), both inclusive.
     * Every integer n is selected with a probability that corresponds to its weight.
     * The weight of integer n is stored in as weights[n].
     * The weights can be an arbitrary numbers.
     *
     * @param weights array of weights for each number (index = number)
     * @return
     */
    public static int randomWeightedInteger(final double[] weights) {
        double rouletteSize = 0;

        for (int i = 0; i < weights.length; i++) {
            rouletteSize += weights[i];
        }

        final double randomRoulettePosition = Math.random() * rouletteSize;
        double roulettePosition = 0;

        for (int i = 0; i < weights.length; i++) {
            roulettePosition += weights[i];

            if (roulettePosition >= randomRoulettePosition) {
                return i;
            }
        }

        // if the array is empty
        // (other scenarios cannot end up here)
        return -1;
    }
}
