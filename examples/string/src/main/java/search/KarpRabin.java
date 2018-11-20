package search;

public class KarpRabin {
    public static int find(final char[] haystack, final char[] needle) {
        if (needle.length > haystack.length) {
            // jehla je větší než kupka sena
            return -1;
        }

        int needleHash = 0;
        int partialHaystackHash = 0;

        for (int i = 0; i < needle.length; i++) {
            needleHash = recomputeHash(needleHash, needle[i]);
            partialHaystackHash = recomputeHash(partialHaystackHash, haystack[i]);
        }

        int iHaystack = 0;

        while (true) {
            if (partialHaystackHash == needleHash) {
                // potenciální nález - musíme ověřit, zda se nejedná jen o kolizi hashe
                if (findUsingBruteForce(haystack, needle, iHaystack, iHaystack + needle.length)) {
                    return iHaystack;
                }
            }

            if (iHaystack < haystack.length - needle.length) {
                // přepočet hashe a posun vpravo
                final char addedCharacter = haystack[iHaystack + needle.length];
                final char removedCharacter = haystack[iHaystack];
                partialHaystackHash = recomputeHash(partialHaystackHash, addedCharacter, removedCharacter);
                iHaystack++;
            } else {
                // nenalezeno
                return -1;
            }
        }
    }

    private static boolean findUsingBruteForce(final char[] haystack, final char[] needle, final int start, final int end) {
        for (int iHaystack = start; iHaystack < end; iHaystack++) {
            final int iNeedle = iHaystack - start;
            if (haystack[iHaystack] != needle[iNeedle]) {
                return false;
            }
        }
        return true;
    }

    private static int recomputeHash(final int oldHash, final char addedCharacter) {
        // FIXME: v reálném nasazení by hash funkce měla být robustnější :)
        return oldHash + addedCharacter;
    }

    private static int recomputeHash(final int oldHash, final char addedCharacter, final char removedCharacter) {
        // FIXME: v reálném nasazení by hash funkce měla být robustnější :)
        return oldHash - removedCharacter + addedCharacter;
    }
}
