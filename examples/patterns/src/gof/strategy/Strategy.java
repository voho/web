package gof.strategy;

/**
 * Strategie pro násobení dvou čísel.
 * @author Vojtěch Hordějčuk
 */
public interface Strategy {
    /**
     * Vynásobí dvě čísla.
     * @param a činitel
     * @param b činitel
     * @return součin
     */
    int multiply(int a, int b);
}
