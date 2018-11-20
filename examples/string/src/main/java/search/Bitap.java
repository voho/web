package search;

public class Bitap {
    public static int find(final char[] haystack, final char[] needle) {
        final int m = needle.length;

        if (m > haystack.length) {
            // jehla je větší než kupka sena
            return -1;
        }

        if (m < 1 || m >= Long.SIZE) {
            throw new IllegalArgumentException("Pattern has to be 1-63 characters long.");
        }

        final long[] mask = new long[Character.MAX_VALUE + 1];

        for (int iMask = 0; iMask < mask.length; iMask++) {
            // ~0 = 111...111
            mask[iMask] = ~0;
        }

        for (int iNeedle = 0; iNeedle < m; iNeedle++) {
            final int iMask = (int) needle[iNeedle];
            // ~(1 << 0) = 1...11111110
            // ~(1 << 1) = 1...11111101
            // ~(1 << 2) = 1...11111011
            // ~(1 << 3) = 1...11110111
            mask[iMask] &= ~(1L << iNeedle);
        }

        // = 1...1110
        long R = ~1;
        // = 000...1...000
        final long z = 1L << m;

        for (int iHaystack = 0; iHaystack < haystack.length; ++iHaystack) {
            // logický součet (OR)
            R |= mask[haystack[iHaystack]];
            // bitový posun vlevo
            R <<= 1;

            if ((R & z) == 0) {
                // nalezeno
                return iHaystack - m + 1;
            }
        }

        // nenalezeno
        return -1;
    }
}
