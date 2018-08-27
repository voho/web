package sort;

public class QuickSort {
    /**
     * Implementace algoritmu quick sort.
     * @param input pole k seřazení
     * @author Vojtěch Hordějčuk
     */
    public static <T extends Comparable<? super T>> void quickSort(final T[] input) {
        quickSort(input, 0, input.length - 1);
    }

    /**
     * Hlavní metoda pro rekurzivní řazení.
     * @param input pole k seřazení
     * @param iStart dolní index řazené části pole
     * @param iEnd horní index řazené části pole
     */
    private static <T extends Comparable<? super T>> void quickSort(final T[] input, final int iStart, final int iEnd) {
        if (iEnd > iStart) {
            // přesuň pivot a ostatní prvky na správné místo
            final int iPivot = split(input, iStart, iEnd);

            // seřaď část pole menší než pivot
            quickSort(input, iStart, iPivot - 1);

            // seřaď část pole větší než pivot
            quickSort(input, iPivot + 1, iEnd);
        }
    }

    /**
     * Metoda pro přesun pivotu a ostatních prvků na správné místo. Přesune prvky
     * menší než pivot před něj a prvky větší než pivot za něj.
     * @param input vstupní pole k rozdělení
     * @param iStart dolní index řazené části pole
     * @param iEnd horní index řazené části pole
     * @return nový index pivotu
     */
    private static <T extends Comparable<? super T>> int split(final T[] input, final int iStart, final int iEnd) {
        // zvol index pivotu (zde je to prostřední prvek)
        // používá se i první prvek, náhodný prvek nebo zjednodušený medián

        final int iPivot = (iStart + iEnd) / 2;
        final T pivot = input[iPivot];

        // přesuň pivot na konec

        swap(input, iPivot, iEnd);

        // začni posupovat od prvního prvku pole

        int iTemp = iStart;

        for (int i = iStart; i < iEnd; i++) {
            if ((input[i]).compareTo(pivot) <= 0) {
                // aktuální prvek je menší než pivot, prohoď je

                swap(input, i, iTemp);

                // pokračuj na další prvek

                iTemp++;
            }
        }

        // přesuň pivot zpět doprostřed

        swap(input, iEnd, iTemp);

        return iTemp;
    }

    /**
     * Metoda pro prohození dvou prvků.
     * @param input vstupní pole
     * @param i1 první index
     * @param i2 druhý index
     */
    private static <T extends Comparable<? super T>> void swap(final T[] input, final int i1, final int i2) {
        final T temp = input[i2];
        input[i2] = input[i1];
        input[i1] = temp;
    }
}
