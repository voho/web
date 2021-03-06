package graph.algorithm.path;

import java.util.HashMap;
import java.util.Map;

public class AStarOutput<N> {
    private final Map<N, N> previous = new HashMap<>();
    private final Map<N, Integer> distanceSoFar = new HashMap<>();
    private final Map<N, Integer> totalDistanceEstimated = new HashMap<>();

    void setPrevious(final N node, final N previous) {
        this.previous.put(node, previous);
    }

    void setDistanceSoFar(final N node, final int distance) {
        this.distanceSoFar.put(node, distance);
    }

    void setTotalEstimatedDistance(final N node, final int distance) {
        this.totalDistanceEstimated.put(node, distance);
    }

    public N getPrevious(final N node) {
        return previous.get(node);
    }

    public int getDistanceSoFar(final N node) {
        return distanceSoFar.get(node);
    }

    public int getTotalEstimatedDistance(final N node) {
        return this.totalDistanceEstimated.get(node);
    }
}
