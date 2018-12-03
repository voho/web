package random;

/**
 * @see http://www.keithschwarz.com/darts-dice-coins/
 */
public class WalkerVoseAliasSelection {
    private int n;
    private double[] prob;
    private int[] alias;

    public void initialize(double[] weights) {
        n = weights.length;
        prob = new double[n];
        alias = new int[n];

        final double[] P = new double[n];
        final int[] S = new int[n];
        final int[] L = new int[n];

        // normalize probabilities

        double totalWeight = 0;
        for (final double weight : weights) {
            totalWeight += weight;
        }
        for (int i = 0; i < n; i++) {
            P[i] = weights[i] * n / totalWeight;
        }

        int nS = 0;
        int nL = 0;

        for (int i = n - 1; i >= 0; i--) {
            if (P[i] < 1) {
                S[nS++] = i;
            } else {
                L[nL++] = i;
            }
        }

        while (nS > 0 && nL > 0) {
            final int a = S[--nS];
            final int g = L[--nL];
            prob[a] = P[a];
            alias[a] = g;
            P[g] = P[g] + P[a] - 1;
            if (P[g] < 1) {
                S[nS++] = g;
            } else {
                L[nL++] = g;
            }
        }

        while (nL > 0) {
            prob[L[--nL]] = 1;
        }
        while (nS > 0) {
            prob[S[--nS]] = 1;
        }
    }

    public int sample() {
        if (prob.length == 0) {
            return -1;
        }
        final int col = (int) (Math.random() * n);
        if (Math.random() < prob[col]) {
            return col;
        } else {
            return alias[col];
        }
    }
}
