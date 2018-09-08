package graph.algorithm.path;

import graph.model.Graph;

import java.util.*;
import java.util.function.ToIntFunction;

public class AStar {
    /**
     * Finds 'optimal' path from start to goal using a heuristic (A-Star algorithm).
     * @param graph graph
     * @param start start node
     * @param goal goal node
     * @param weighter edge weighter to estimate distance of an edge
     * @param heuristic heuristic to estimate remaining distance from a specific node to goal
     * @param <N> node type
     * @param <E> edge type
     * @return A-Star solution
     */
    public static <N, E> AStarOutput<N> aStar(final Graph<N, E> graph, final N start, final N goal, final ToIntFunction<E> weighter, final ToIntFunction<N> heuristic) {
        final AStarOutput<N> output = new AStarOutput<>();
        final Collection<N> queue = new LinkedList<>();
        queue.add(start);

        for (final N node : graph.nodes()) {
            // set previous node in optimal path as unknown
            output.setPrevious(node, null);
            // set distance as unknown (max possible value)
            output.setDistanceSoFar(node, Integer.MAX_VALUE);
        }

        // this is zero by definition
        output.setDistanceSoFar(start, 0);
        // total estimated cost from start to goal
        output.setTotalEstimatedDistance(start, heuristic.applyAsInt(start));

        while (!queue.isEmpty()) {
            final N current = Collections.min(queue, Comparator.comparingInt(output::getTotalEstimatedDistance));
            queue.remove(current);

            if (current.equals(goal)) {
                // we have found the goal node
                break;
            }

            for (final Map.Entry<N, E> adjacent : graph.successorsWithEdges(current).entrySet()) {
                final N next = adjacent.getKey();
                final E edgeToNext = adjacent.getValue();
                final int edgeToNextDistance = weighter.applyAsInt(edgeToNext);
                final int alternativeDistance = output.getDistanceSoFar(current) + edgeToNextDistance;

                if (alternativeDistance < output.getDistanceSoFar(next)) {
                    final int newTotalEstimatedDistance = alternativeDistance + heuristic.applyAsInt(next);
                    output.setDistanceSoFar(next, alternativeDistance);
                    output.setPrevious(next, current);
                    output.setTotalEstimatedDistance(next, newTotalEstimatedDistance);
                    queue.add(next);
                }
            }
        }

        return output;
    }
}
