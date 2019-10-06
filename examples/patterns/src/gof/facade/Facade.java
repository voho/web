package gof.facade;

public class Facade {
    /**
     * sčítačka
     */
    private final Adder adder = new Adder();
    /**
     * násobička
     */
    private final Multiplier multiplier = new Multiplier();

    /**
     * Vrátí číslo opačné.
     * @param a číslo A
     * @return číslo opačné k číslu A
     */
    public double negative(final double a) {
        return this.adder.subtract(0, a);
    }

    /**
     * Vrátí aritmetický průměr dvou čísel.
     * @param a první číslo
     * @param b druhé číslo
     * @return průměr obou čísel
     */
    public double mean(final double a, final double b) {
        return this.multiplier.divide(this.adder.add(a, b), 2.0);
    }

    /**
     * Vrátí druhou mocninu (čtverec) čísla.
     * @param a číslo A
     * @return druhá mocnina čísla A
     */
    public double square(final double a) {
        return this.multiplier.multiply(a, a);
    }
}
