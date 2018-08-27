package sort;

public class MergeSort {
    /**
     * Implementace algoritmu merge sort.
     * @param input pole k seřazení
     * @author Vojtěch Hordějčuk
     */
    public static <T extends Comparable<? super T>> void mergeSort(final T input[]) {
        // vytvoř dočasné pole o stejné velikosti
        final Object temp[] = new Object[input.length];

        // spusť rekurzivní řazení pro celé pole
        mergeSort(input, temp, 0, input.length - 1);
    }

    /**
     * Hlavní rekurzivní funkce.
     * @param input pole k seřazení
     * @param temp dočasné pracovní pole
     * @param iLeft index levého prvku
     * @param iRight index pravého prvku
     */
    private static <T extends Comparable<? super T>> void mergeSort(final T[] input, final Object[] temp, final int iLeft, final int iRight) {
        // jestliže je koncový index větší než počáteční, je nutné řadit

        if (iLeft < iRight) {
            // vypočítej index středu
            final int iCenter = (iLeft + iRight) / 2;

            // spusť řazení rekurzivně na levou polovinu (včetně středu)
            mergeSort(input, temp, iLeft, iCenter);

            // spusť řazení rekurzivně na pravou polovinu (bez středu)
            mergeSort(input, temp, iCenter + 1, iRight);

            // zkombinuj (slij) obě části
            merge(input, temp, iLeft, iCenter + 1, iRight);
        }
    }

    /**
     * Interní slévací metoda.
     * @param input pole k seřazení
     * @param temp dočasné pracovní pole
     * @param iLeft index levého prvku
     * @param iRight index prvního prvku pravé části
     * @param iEnd index posledního prvku pravé části
     */
    @SuppressWarnings("unchecked")
    private static <T extends Comparable<? super T>> void merge(final T[] input, final Object[] temp, int iLeft, int iRight, int iEnd) {
        // poslední index levé části
        final int iLeftEnd = iRight - 1;

        // aktuální pozice
        int iCurrent = iLeft;

        // počet prvků
        final int numElements = iEnd - iLeft + 1;

        while ((iLeft <= iLeftEnd) && (iRight <= iEnd)) {
            if (input[iLeft].compareTo(input[iRight]) <= 0) {
                temp[iCurrent++] = input[iLeft++];
            } else {
                temp[iCurrent++] = input[iRight++];
            }
        }

        // zkopíruj zbytek levé poloviny
        while (iLeft <= iLeftEnd) {
            temp[iCurrent++] = input[iLeft++];
        }

        // zkopíruj zbytek pravé poloviny
        while (iRight <= iEnd) {
            temp[iCurrent++] = input[iRight++];
        }

        // zkopíruj dočasné pole zpátky
        for (int i = 0; i < numElements; i++) {
            input[iEnd] = (T) temp[iEnd];
            iEnd--;
        }
    }
}
