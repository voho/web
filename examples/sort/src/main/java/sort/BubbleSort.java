package sort;

public class BubbleSort {
    /**
     * Implementace algoritmu bubble sort.
     * @param input vstupní pole
     * @author Vojtěch Hordějčuk
     */
    public static <T extends Comparable<? super T>> void bubbleSort(final T[] input) {
        // průchod skončí na předposledním prvku (index 'i - 2')
        // (prvek se vždy porovnává se svým následníkem)

        for (int i = 0; i < input.length - 1; i++) {
            // už jsme seřadili 'i' prvků
            // (na konci pole je tedy nemusíme kontrolovat)

            for (int j = 0; j < input.length - 1 - i; j++) {
                // prohoď sousední prvky, pokud jsou ve špatném pořadí

                if (input[j].compareTo(input[j + 1]) == 1) {
                    final T temp = input[j];
                    input[j] = input[j + 1];
                    input[j + 1] = temp;
                }
            }
        }
    }
}
