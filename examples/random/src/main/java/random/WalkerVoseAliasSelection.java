package random;

/**
 * @see http://www.keithschwarz.com/darts-dice-coins/
 */
public class WalkerVoseAliasSelection {
    private int n;
    private double[] probabilities;
    private int[] alias;

    public void initialize(double[] weights) {
        n = weights.length;
        probabilities = new double[n];
        alias = new int[n];

        final double[] P = new double[n];
        final int[] smallIndices = new int[n];
        final int[] largeIndices = new int[n];

        // normalize probabilities

        double totalWeight = 0;
        for (final double weight : weights) {
            totalWeight += weight;
        }
        for (int i = 0; i < n; i++) {
            P[i] = weights[i] * n / totalWeight;
        }

        int numSmall = 0;
        int numLarge = 0;

        for (int i = n - 1; i >= 0; i--) {
            if (P[i] < 1) {
                smallIndices[numSmall++] = i;
            } else {
                largeIndices[numLarge++] = i;
            }
        }

        while (numSmall > 0 && numLarge > 0) {
            final int a = smallIndices[--numSmall];
            final int g = largeIndices[--numLarge];
            probabilities[a] = P[a];
            alias[a] = g;
            P[g] = P[g] + P[a] - 1;
            if (P[g] < 1) {
                smallIndices[numSmall++] = g;
            } else {
                largeIndices[numLarge++] = g;
            }
        }

        while (numLarge > 0) {
            probabilities[largeIndices[--numLarge]] = 1;
        }
        while (numSmall > 0) {
            probabilities[smallIndices[--numSmall]] = 1;
        }
    }

    public int sample() {
        if (probabilities.length == 0) {
            return -1;
        }
        final int col = (int) (Math.random() * n);
        if (Math.random() < probabilities[col]) {
            return col;
        } else {
            return alias[col];
        }
    }
}
