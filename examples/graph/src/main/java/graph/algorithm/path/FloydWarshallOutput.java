package graph.algorithm.path;

import java.util.List;
import java.util.Optional;

public interface FloydWarshallOutput<N> {
    Optional<Integer> getMinimalDistance(N a, N b);

    Optional<List<N>> getShortestPath(N a, N b);
}
