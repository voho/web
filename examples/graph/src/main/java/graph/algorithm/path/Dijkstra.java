package graph.algorithm.path;

import graph.model.Graph;

import java.util.*;
import java.util.function.ToIntFunction;

public class Dijkstra {
    /**
     * Computes all shortest paths starting from the given start node.
     * @param graph graph
     * @param start start node
     * @param weighter edge weighter
     * @param <N> node type
     * @param <E> edge type
     * @return output with shortest path info
     */
    public static <N, E> DijkstraOutput<N> dijkstra(final Graph<N, E> graph, final N start, final ToIntFunction<E> weighter) {
        final DijkstraOutput<N> output = new DijkstraOutput<>();

        for (final N node : graph.nodes()) {
            // set previous node in optimal path as unknown
            output.setPrevious(node, null);
            // set distance as unknown (max possible value)
            output.setDistance(node, Integer.MAX_VALUE);
        }

        // this is zero by definition
        output.setDistance(start, 0);

        final Collection<N> unprocessed = new LinkedList<>(graph.nodes());

        while (!unprocessed.isEmpty()) {
            // select an unprocessed node with the minimum distance and remove it
            final N u = Collections.min(unprocessed, Comparator.comparing(output::getDistance));
            unprocessed.remove(u);

            for (final Map.Entry<N, E> entry : graph.successorsWithEdges(u).entrySet()) {
                // get the edge between nodes (u, v)
                final N v = entry.getKey();
                final E edge = entry.getValue();
                // compute alternative and evaluate whether it is shorter
                final int alt = output.getDistance(u) + weighter.applyAsInt(edge);

                if (alt < output.getDistance(v)) {
                    // alternative is shorter, so use it for the shortest path
                    output.setDistance(v, alt);
                    output.setPrevious(v, u);
                }
            }
        }

        return output;
    }
}
