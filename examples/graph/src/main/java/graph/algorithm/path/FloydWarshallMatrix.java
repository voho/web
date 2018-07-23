package graph.algorithm.path;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.nCopies;

public class FloydWarshallMatrix<VALUE, PARENT> {
    private final List<VALUE> matrix;
    private final List<PARENT> parent;
    private final int n;

    public FloydWarshallMatrix(final int n) {
        this.matrix = new ArrayList<>(nCopies(n * n, null));
        this.parent = new ArrayList<>(nCopies(n * n, null));
        this.n = n;
    }

    public int size() {
        return n;
    }

    public Optional<VALUE> get(final int x, final int y) {
        return Optional.ofNullable(matrix.get(map(x, y)));
    }

    public Optional<PARENT> getParent(final int x, final int y) {
        return Optional.ofNullable(parent.get(map(x, y)));
    }

    public void setMinimumDistance(final int x, final int y, final VALUE value) {
        matrix.set(map(x, y), value);
    }

    public void setPredecessor(final int x, final int y, final PARENT value) {
        parent.set(map(x, y), value);
    }

    private int map(final int x, final int y) {
        return x * n + y;
    }
}
