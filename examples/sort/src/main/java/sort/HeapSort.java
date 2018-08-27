package sort;

public class HeapSort {
    /**
     * Implementace algoritmu heap sort.
     * @param input pole k seřazení
     * @param ascending TRUE pro vzestupné řazení, FALSE pro sestupné
     */
    public static <T extends Comparable<? super T>> void heapSort(final T[] input, final boolean ascending) {
        // požadovaný směr řazení

        final int dir = ascending ? -1 : 1;

        // inicializovat haldu ve vstupním poli

        for (int i = (input.length / 2) - 1; i >= 0; i--) {
            HeapSort.sift(input, i, input.length - 1, dir);
        }

        // seřadit pole postupným posunem prvků na správné místo

        for (int i = input.length - 1; i > 0; i--) {
            // vložit prvek "i" na vrchol
            HeapSort.swap(input, i, 0);
            // prosít prvek na správé místo
            HeapSort.sift(input, 0, i - 1, dir);
        }
    }

    /**
     * Metoda pro opravu haldy - umístí zvolený prvek na správné místo.
     * @param input pole k seřazení
     * @param iTop index vrcholu haldy
     * @param iBottom poslední index haldy
     * @param dir směr řazení (-1 vzestupně, +1 sestupně)
     */
    private static <T extends Comparable<? super T>> void sift(final T[] input, final int iTop, final int iBottom, final int dir) {
        int iRoot = iTop;
        int iChild = (iRoot * 2) + 1;

        while (iChild <= iBottom) {
            // vyhledat místo pro vložení hodnoty

            if ((iChild < iBottom) && (input[iChild].compareTo(input[iChild + 1]) == dir)) {
                iChild++;
            }

            if (input[iRoot].compareTo(input[iChild]) == dir) {
                // prohodit oba prvky
                HeapSort.swap(input, iRoot, iChild);
                // pokračovat v prosívání od potomka
                iRoot = iChild;
                iChild = (iRoot * 2) + 1;
                continue;
            }

            // ukončit prosívání
            break;
        }
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
