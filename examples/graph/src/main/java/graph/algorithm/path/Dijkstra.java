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
     * @param <NODE> node type
     * @param <EDGE> edge type
     * @return output with shortest path info
     */
    public static <NODE, EDGE> DijkstraOutput<NODE> dijkstra(final Graph<NODE, EDGE> graph, final NODE start, final ToIntFunction<EDGE> weighter) {
        final DijkstraOutput<NODE> output = initializeOutput(graph, start);

        final Collection<NODE> unprocessed = new LinkedList<>(graph.nodes());

        while (!unprocessed.isEmpty()) {
            // select an unprocessed node with the minimum distance and remove it
            final NODE current = Collections.min(unprocessed, Comparator.comparing(output::getDistance));
            unprocessed.remove(current);

            for (final Map.Entry<NODE, EDGE> entry : graph.successorsWithEdges(current).entrySet()) {
                // get the edge between nodes (u, v)
                final NODE next = entry.getKey();
                final EDGE edgeToNext = entry.getValue();
                // compute alternative and evaluate whether it is shorter
                final int alt = output.getDistance(current) + weighter.applyAsInt(edgeToNext);

                if (alt < output.getDistance(next)) {
                    // alternative is shorter, so use it for the shortest path
                    output.setDistance(next, alt);
                    output.setPrevious(next, current);
                }
            }
        }

        return output;
    }

    private static <NODE, EDGE> DijkstraOutput<NODE> initializeOutput(final Graph<NODE, EDGE> graph, final NODE start) {
        final DijkstraOutput<NODE> output = new DijkstraOutput<>();

        for (final NODE node : graph.nodes()) {
            // set previous node in optimal path as unknown
            output.setPrevious(node, null);
            // set distance as unknown (max possible value)
            output.setDistance(node, Integer.MAX_VALUE);
        }

        // this is zero by definition
        output.setDistance(start, 0);

        return output;
    }
}
