package gof.strategy;

/**
 * Standardní implementace násobení.
 * @author Vojtěch Hordějčuk
 */
public class TimesStrategy implements Strategy {
    @Override
    public int multiply(final int a, final int b) {
        return a * b;
    }
}
