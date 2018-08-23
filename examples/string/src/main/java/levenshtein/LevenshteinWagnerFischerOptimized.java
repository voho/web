package levenshtein;

public class LevenshteinWagnerFischerOptimized {
    /**
     * Calculates the Levenshtein distance of two strings.
     * Uses an optimized Wagner-Fischer algorithm.
     * @param s first string
     * @param t second string
     * @return Levenshtein distance
     * @see https://en.wikipedia.org/wiki/Wagner%E2%80%93Fischer_algorithm
     */
    public static int distanceWagnerFischerOptimized(final char[] s, final char[] t) {
        if (t.length > s.length) {
            // to save memory, we want the shorter string to be the second parameter
            // (as long as the distance is symmetrical, we do not care about the order)
            return distanceWagnerFischerOptimized(t, s);
        }

        int[] previousRow = new int[t.length + 1];
        int[] currentRow = new int[t.length + 1];

        for (int i = 0; i < previousRow.length; i++) {
            // 0, 1, 2, 3, 4, 5...
            previousRow[i] = i;
        }

        for (int is = 1; is <= s.length; is++) {
            currentRow[0] = is;

            for (int it = 1; it <= t.length; it++) {
                // up = deletion
                final int costDeletion = previousRow[it] + 1;
                // left = insertion
                final int costInsertion = currentRow[it - 1] + 1;
                // cost for replacing the character in case it is different
                final int costReplacement = (s[is - 1] == t[it - 1]) ? 0 : 1;
                // diagonal = substitution (if necessary)
                final int costSubstitution = previousRow[it - 1] + costReplacement;

                currentRow[it] = min(costDeletion, costInsertion, costSubstitution);
            }

            // swap arrays (currentRow will be re-used and overwritten)
            final int[] temp = previousRow;
            previousRow = currentRow;
            currentRow = temp;
        }

        return previousRow[t.length];
    }

    private static int min(final int a, final int b, final int c) {
        return Math.min(a, Math.min(b, c));
    }
}
