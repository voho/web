/**
 * Single LZ-77 codeword.
 * Immutable class.
 */
public class LZ78Codeword {
    private final int i;
    private final char x;

    public LZ78Codeword(final int i, final char x) {
        this.i = i;
        this.x = x;
    }

    public int getI() {
        return i;
    }

    public char getX() {
        return x;
    }

    @Override
    public String toString() {
        return String.format("(%d,%s)", i, x == 0 ? "<eof>" : x);
    }
}