package algorithm.bitap;

public class Bitap {
    public int find(final char[] text, final char[] pattern) {
        final int m = pattern.length;

        if (m < 1 || m >= Long.SIZE) {
            throw new IllegalArgumentException("Pattern has to be 1-63 characters long.");
        }

        final long[] mask = new long[Character.MAX_VALUE + 1];

        for (int iMask = 0; iMask < mask.length; iMask++) {
            // ~0 = 111...111
            mask[iMask] = ~0;
        }

        for (int iPattern = 0; iPattern < m; iPattern++) {
            final int iMask = (int) pattern[iPattern];
            // ~(1 << 0) = 1...11111110
            // ~(1 << 1) = 1...11111101
            // ~(1 << 2) = 1...11111011
            // ~(1 << 3) = 1...11110111
            mask[iMask] &= ~(1L << iPattern);
        }

        // = 1...1110
        long R = ~1;
        // = 000...1...000
        final long z = 1L << m;

        for (int iText = 0; iText < text.length; ++iText) {
            // logický součet (OR)
            R |= mask[text[iText]];
            // bitový posun vlevo
            R <<= 1;

            if ((R & z) == 0) {
                // nalezeno
                return iText - m + 1;
            }
        }

        // nenalezeno
        return -1;
    }
}
