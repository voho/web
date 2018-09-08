package graph.algorithm.color;

import graph.model.Graph;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of Welsh-Powell`s algorithm.
 */
public final class WelshPowell {
    /**
     * Calculate the upper-bound approximate of graph color number.
     * @param graph given graph
     * @return map of node to its color index (starting from 0)
     */
    public static <N> Map<N, Integer> colorByWelshPowell(final Graph<N, ?> graph) {
        final Set<N> allNodes = graph.nodes();
        final int n = allNodes.size();
        final Map<N, Integer> colors = new HashMap<>(n);
        final List<N> sortedNodesToColor = new ArrayList<>(n);

        sortedNodesToColor.addAll(allNodes
                .stream()
                .sorted(Comparator.<N>comparingInt(node -> graph.adjacent(node).size()).reversed())
                .collect(Collectors.toList()));

        int color = 0;

        while (!sortedNodesToColor.isEmpty()) {
            final Set<N> thisStepColoredNodes = new LinkedHashSet<>();

            // take the first node

            final N first = sortedNodesToColor.get(0);
            thisStepColoredNodes.add(first);

            // find all nodes not connected with that node

            sortedNodesToColor
                    .stream()
                    .filter(node -> first != node)
                    .filter(node -> !graph.isAdjacent(first, node))
                    .forEach(thisStepColoredNodes::add);

            // color them by the next available color

            final int nextColor = color++;
            thisStepColoredNodes.forEach(node -> colors.put(node, nextColor));

            // remove them from queue

            sortedNodesToColor.removeAll(thisStepColoredNodes);
        }

        return colors;
    }
}
