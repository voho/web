package gof.strategy;

/**
 * Implementace násobení pomocí sčítání.
 * @author Vojtěch Hordějčuk
 */
public class PlusStrategy implements Strategy {
    @Override
    public int multiply(final int a, final int b) {
        int i = Math.abs(b);
        int r = a;

        while (i > 1) {
            r += a;
            i--;
        }

        return (b < 0) ? -r : r;
    }
}