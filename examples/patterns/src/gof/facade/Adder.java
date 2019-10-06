package gof.facade;

public class Adder {
    /**
     * Vrátí součet dvou čísel.
     * @param a sčítanec
     * @param b sčítanec
     * @return součet
     */
    public double add(final double a, final double b) {
        return a + b;
    }

    /**
     * Vrátí rozdíl dvou čísel.
     * @param a menšenec
     * @param b menšitel
     * @return rozdíl
     */
    public double subtract(final double a, final double b) {
        return a - b;
    }
}
