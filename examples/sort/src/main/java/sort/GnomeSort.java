package sort;

public class GnomeSort {
    /**
     * Implementace algoritmu gnome sort.
     * @param input vstupní pole
     * @author Vojtěch Hordějčuk
     */
    public static <T extends Comparable<? super T>> void gnomeSort(final T[] input) {
        int gnomePosition = 1;

        while (gnomePosition < input.length) {
            if (input[gnomePosition - 1].compareTo(input[gnomePosition]) <= 0) {
                // správné pořadí, posun dopředu
                gnomePosition++;
            } else {
                // špatné pořadí, prohodit prvky, posun dozadu
                final T tmp = input[gnomePosition - 1];
                input[gnomePosition - 1] = input[gnomePosition];
                input[gnomePosition] = tmp;

                if (--gnomePosition == 0) {
                    // odrazit se od konce
                    gnomePosition = 1;
                }
            }
        }
    }
}
