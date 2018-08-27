package sort;

public class SelectionSort {
    /**
     * Implementace algoritmu selection sort.
     * @param input vstupní pole
     * @author Vojtěch Hordějčuk
     */
    public static <T extends Comparable<? super T>> void selectionSort(final T[] input) {
        for (int i = 0; i < input.length; i++) {
            // 'i' je počáteční index neseřazené části pole
            // nejdřív si jako minimum vezmi to první co najdeš
            // (třeba tam nic menšího už není)

            int indexOfMinimum = i;

            // projdi zbytek pole

            for (int j = i + 1; j < input.length; j++) {
                // 'j' je počáteční index části pole, ve které hledám menší číslo

                if (input[j].compareTo(input[indexOfMinimum]) < 0) {
                    // bylo nalezeno menší číslo, zapamatuj si jeho index

                    indexOfMinimum = j;
                }
            }

            // pokud je třeba, prohoď začátek neseřazené části s minimem

            if (i != indexOfMinimum) {
                final T temp = input[i];
                input[i] = input[indexOfMinimum];
                input[indexOfMinimum] = temp;
            }
        }
    }
}
