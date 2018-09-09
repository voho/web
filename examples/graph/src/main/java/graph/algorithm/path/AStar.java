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
     * @param <NODE> node type
     * @param <EDGE> edge type
     * @return A-Star solution
     */
    public static <NODE, EDGE> AStarOutput<NODE> aStar(final Graph<NODE, EDGE> graph, final NODE start, final NODE goal, final ToIntFunction<EDGE> weighter, final ToIntFunction<NODE> heuristic) {
        final AStarOutput<NODE> output = initializeOutput(graph, start, heuristic);

        final Collection<NODE> unprocessed = new LinkedList<>();
        unprocessed.add(start);

        while (!unprocessed.isEmpty()) {
            final NODE current = Collections.min(unprocessed, Comparator.comparingInt(output::getTotalEstimatedDistance));
            unprocessed.remove(current);

            if (current.equals(goal)) {
                // we have found the goal node
                break;
            }

            for (final Map.Entry<NODE, EDGE> adjacent : graph.successorsWithEdges(current).entrySet()) {
                final NODE next = adjacent.getKey();
                final EDGE edgeToNext = adjacent.getValue();
                final int edgeToNextDistance = weighter.applyAsInt(edgeToNext);
                final int alternativeDistance = output.getDistanceSoFar(current) + edgeToNextDistance;

                if (alternativeDistance < output.getDistanceSoFar(next)) {
                    final int newTotalEstimatedDistance = alternativeDistance + heuristic.applyAsInt(next);
                    output.setDistanceSoFar(next, alternativeDistance);
                    output.setPrevious(next, current);
                    output.setTotalEstimatedDistance(next, newTotalEstimatedDistance);
                    unprocessed.add(next);
                }
            }
        }

        return output;
    }

    private static <NODE, EDGE> AStarOutput<NODE> initializeOutput(final Graph<NODE, EDGE> graph, final NODE start, final ToIntFunction<NODE> heuristic) {
        final AStarOutput<NODE> output = new AStarOutput<>();

        for (final NODE node : graph.nodes()) {
            // set previous node in optimal path as unknown
            output.setPrevious(node, null);
            // set distance as unknown (max possible value)
            output.setDistanceSoFar(node, Integer.MAX_VALUE);
        }

        // this is zero by definition
        output.setDistanceSoFar(start, 0);
        // total estimated cost from start to goal
        output.setTotalEstimatedDistance(start, heuristic.applyAsInt(start));

        return output;
    }
}
