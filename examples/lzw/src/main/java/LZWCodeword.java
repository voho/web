/**
 * Kódové slovo kompresního algoritmu LZW.
 *
 * @author Vojtěch Hordějčuk
 */
public class LZWCodeword {
    private final int index;

    /**
     * Vytvoří nové kódové slovo.
     *
     * @param index index uzlu
     */
    public LZWCodeword(final int index) {
        assert (index > 0);

        this.index = index;
    }

    /**
     * Vrátí index uzlu.
     *
     * @return index uzlu
     */
    public int getIndex() {
        return this.index;
    }

    @Override
    public String toString() {
        return String.format("%d", this.index);
    }
}
