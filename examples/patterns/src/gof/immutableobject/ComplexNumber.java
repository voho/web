package gof.immutableobject;

/**
 * Třída představující komplexní číslo (a + bi).
 * @author Vojtěch Hordějčuk
 */
public class ComplexNumber {
    /**
     * reálná složka
     */
    private final double a;
    /**
     * imaginární složka
     */
    private final double b;

    /**
     * Vytvoří novou instanci.
     * @param pA reálná složka
     * @param pB imaginární složka
     */
    public ComplexNumber(final double pA, final double pB) {
        this.a = pA;
        this.b = pB;
    }

    // DOTAZY
    // ======

    // getRealPart()
    // getImaginaryPart()

    // OPERACE
    // =======

    /**
     * Sečte číslo s jiným komplexním číslem a vrátí výsledek.
     * @param other druhý sčítanec
     * @return výsledné komplexní číslo
     */
    public ComplexNumber plus(final ComplexNumber other) {
        return new ComplexNumber(this.a + other.a, this.b + other.b);
    }

    /**
     * Sečte číslo s jiným reálným číslem a vrátí výsledek.
     * @param other druhý sčítanec
     * @return výsledné komplexní číslo
     */
    public ComplexNumber plus(final double other) {
        return new ComplexNumber(this.a + other, this.b);
    }

    // ROVNOST
    // =======

    @Override
    public int hashCode() {
        return (int) (this.a - this.b);
    }

    @Override
    public boolean equals(final Object other) {
        if (other == null) {
            return false;
        }

        if (this == other) {
            return true;
        }

        if (!(other instanceof ComplexNumber)) {
            return false;
        }

        final ComplexNumber that = (ComplexNumber) other;

        if (that.a != this.a) {
            return false;
        }

        return that.b == this.b;
    }

    // REPREZENTACE
    // ============

    @Override
    public String toString() {
        return String.format("%s + %si", this.a, this.b);
    }
}