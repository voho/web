package gof.facade;

public class Multiplier {
    /**
     * Vrátí činitel dvou čísel.
     * @param a činitel
     * @param b činitel
     * @return součin
     */
    public double multiply(final double a, final double b) {
        return a * b;
    }

    /**
     * Vrátí podíl dvou čísel.
     * @param a dělenec
     * @param b dělitel
     * @return podíl
     */
    public double divide(final double a, final double b) {
        return a / b;
    }
}
