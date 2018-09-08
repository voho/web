package graph.algorithm.path;

import java.util.HashMap;
import java.util.Map;

public class DijkstraOutput<N> {
    private final Map<N, N> previous = new HashMap<>();
    private final Map<N, Integer> distance = new HashMap<>();

    void setPrevious(final N node, final N previous) {
        this.previous.put(node, previous);
    }

    void setDistance(final N node, final int distance) {
        this.distance.put(node, distance);
    }

    public int getDistance(final N node) {
        return distance.get(node);
    }

    public N getPrevious(final N node) {
        return previous.get(node);
    }
}
