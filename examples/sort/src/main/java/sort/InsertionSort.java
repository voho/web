package sort;

public class InsertionSort {
    /**
     * Implementace algoritmu insertion sort.
     * @param input vstupní pole
     * @author Vojtěch Hordějčuk
     */
    public static <T extends Comparable<? super T>> void insertionSort(final T[] input) {
        for (int i = 1; i < input.length; i++) {
            // začni na aktuálním indexu

            int j = i;

            // zapamatuj si číslo, které se bude posouvat na správnou pozici
            // (na toto místo v poli se totiž mohou dostat větší čísla)

            final T moved = input[i];

            // posouvej ostatní čísla vpravo, dokud:
            // 1) nejsi na konci pole
            // 2) a zároveň jsou tato čísla větší než posouvané číslo

            while ((j > 0) && (input[j - 1].compareTo(moved) > 0)) {
                input[j] = input[j - 1];
                j--;
            }

            // správná pozice prvku byla nalezena, vlož ho tam

            input[j] = moved;
        }
    }
}
