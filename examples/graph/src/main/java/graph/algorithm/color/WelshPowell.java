package graph.algorithm.color;


import cz.voho.grafo.UndirectedGraph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of Welsh-Powell`s algorithm.
 */
public final class WelshPowell {
    /**
     * Calculate the upper-bound approximate of graph color number.
     *
     * @param graph given graph
     * @return map of node to its color index (starting from 0)
     */
    public static <N, E> Map<N, Integer> colorByWelshPowell(final UndirectedGraph<N, E> graph) {
        final Set<N> allNodes = graph.nodes();
        final int n = allNodes.size();
        final Map<N, Integer> colors = new HashMap<>(n);
        final List<N> sortedNodesToColor = new ArrayList<>(n);

        sortedNodesToColor.addAll(allNodes
                .stream()
                .sorted(Comparator.comparingInt(graph::degree).reversed())
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
                    .filter(node -> !graph.isNeighbour(first, node))
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
