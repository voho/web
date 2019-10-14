package gof.strategy;

/**
 * Kontext obsahující strategii.
 * @author Vojtěch Hordějčuk
 */
public class Context {
    /**
     * aktivní strategie
     */
    private final Strategy strategy;

    /**
     * Vytvoří novou instanci.
     * @param strategy zvolená strategie
     */
    public Context(final Strategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Vynásobí dvě čísla.
     * @param a činitel
     * @param b činitel
     * @return součin
     */
    public int multiply(final int a, final int b) {
        return strategy.multiply(a, b);
    }
}