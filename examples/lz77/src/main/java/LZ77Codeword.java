/**
 * Single LZ-77 codeword.
 * Immutable class.
 */
public class LZ77Codeword {
    private final int i;
    private final int j;
    private final char x;

    public LZ77Codeword(final int i, final int j, final char x) {
        this.i = i;
        this.j = j;
        this.x = x;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public char getX() {
        return x;
    }

    @Override
    public String toString() {
        return String.format("(%d,%d,%s)", i, j, x == 0 ? "<eof>" : x);
    }
}
