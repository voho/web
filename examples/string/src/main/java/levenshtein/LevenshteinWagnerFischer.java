package levenshtein;

public class LevenshteinWagnerFischer {
    /**
     * Calculates the Levenshtein distance of two strings.
     * Uses a Wagner-Fischer algorithm.
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