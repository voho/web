import java.util.Arrays;

public class Levenshtein {
    /**
     * Calculates the Levenshtein distance of two strings.
     * Uses an optimized Wagner-Fischer algorithm.
     *
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

    /**
     * Calculates the Levenshtein distance of two strings.
     * Uses a Wagner-Fischer algorithm.
     *
     * @param s first string
     * @param t second string
     * @return Levenshtein distance
     * @see https://en.wikipedia.org/wiki/Wagner%E2%80%93Fischer_algorithm
     */
    public static int distanceWagnerFischer(final char[] s, final char[] t) {
        if (s.length == 0) {
            // edge case: S is empty
            return t.length;
        } else if (t.length == 0) {
            // edge case: T is empty
            return s.length;
        }

        final int[][] d = new int[s.length + 1][t.length + 1];

        for (int i = 0; i <= s.length; i++) {
            // reset first row
            d[i][0] = i;
        }

        for (int i = 0; i <= t.length; i++) {
            // reset first column
            d[0][i] = i;
        }

        for (int it = 1; it <= t.length; it++) {
            for (int is = 1; is <= s.length; is++) {
                // up = deletion
                final int costDeletion = d[is - 1][it] + 1;
                // left = insertion
                final int costInsertion = d[is][it - 1] + 1;
                // cost for replacing the character in case it is different
                final int costReplacement = (s[is - 1] == t[it - 1]) ? 0 : 1;
                // diagonal = substitution (if necessary)
                final int costSubstitution = d[is - 1][it - 1] + costReplacement;

                d[is][it] = min(costDeletion, costInsertion, costSubstitution);
            }
        }

        return d[s.length][t.length];
    }

    private static int min(final int a, final int b, final int c) {
        return Math.min(a, Math.min(b, c));
    }
}