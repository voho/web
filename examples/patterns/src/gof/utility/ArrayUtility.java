package gof.utility;

/**
 * Ukázková knihovní třída pro práci s polem.
 * @author Vojtěch Hordějčuk
 */
public final class ArrayUtility {
    /**
     * Není možné vytvářet instance knihovní třídy.
     */
    private ArrayUtility() {
        throw new UnsupportedOperationException();
    }

    /**
     * Prohodí dvě čísla v poli.
     * @param array pole
     * @param i1 první index
     * @param i2 druhý index
     */
    public static void swap(final int[] array, final int i1, final int i2) {
        final int temp = array[i1];
        array[i1] = array[i2];
        array[i2] = temp;
    }

    /**
     * Převede pole na řetězec, např. "<b>1, 2, 3, 4</b>".
     * @param array pole
     * @return řetězcová reprezentace pole
     */
    public static String toString(final int[] array) {
        final StringBuilder buffer = new StringBuilder(32);

        for (final int element : array) {
            if (buffer.length() != 0) {
                buffer.append(", ");
            }

            buffer.append(element);
        }

        return buffer.toString();
    }
}
