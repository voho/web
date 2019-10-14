package search;

public class BoyerMooreHorspool {
    public static int find(final char[] haystack, final char[] needle) {
        final int[] jumpTable = createJumpTable(needle);
        int iHaystack = 0;
        while (haystack.length - iHaystack >= needle.length) {
            // start comparing haystack piece with the needle right-to-left
            int iNeedle = needle.length - 1;
            while (haystack[iHaystack + iNeedle] == needle[iNeedle]) {
                if (iNeedle == 0) {
                    // FOUND
                    return iHaystack;
                }
                iNeedle--;
            }
            // move position in haystack
            final int iHaystackForNeedleEnd = iHaystack + needle.length - 1;
            iHaystack += jumpTable[haystack[iHaystackForNeedleEnd]];
        }
        // NOT FOUND
        return -1;
    }

    /**
     * Creates jump table.
     * @param needle string to search for
     * @return array of jumps for each character of the needle
     */
    private static int[] createJumpTable(final char[] needle) {
        final int alphabetSize = Character.MAX_VALUE - Character.MIN_VALUE + 1;
        final int[] jumpTable = new int[alphabetSize];

        for (int iJumpTable = 0; iJumpTable < jumpTable.length; iJumpTable++) {
            // for characters not in needle
            jumpTable[iJumpTable] = needle.length;
        }

        for (int iNeedle = 0; iNeedle < needle.length; iNeedle++) {
            // for characters in needle
            final int iTableForNeedleCharacter = (int) needle[iNeedle];
            jumpTable[iTableForNeedleCharacter] = needle.length - 1 - iNeedle;
        }

        return jumpTable;
    }
}
