package graph.algorithm.mst;

import graph.model.MutableUndirectedGraph;
import graph.model.UndirectedGraph;

import java.util.*;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

/**
 * Implementation of Boruvka-Kruskal`s algorithm.
 */
public final class BoruvkaKruskal {
    /**
     * Finds the minimum spanning tree of the given graph.
     * @param graph original graph
     * @return minimum spanning tree
     */
    public static <N> UndirectedGraph<N> compute(final UndirectedGraph<N> graph, final ToIntFunction<UndirectedGraph.Edge<N>> weighter) {
        // STEP 1
        // Sort edges by weight (ascending).

        final List<UndirectedGraph.Edge<N>> sortedEdges = graph
                .edges()
                .stream()
                .sorted(Comparator.comparingInt(weighter))
                .collect(Collectors.toList());

        // STEP 2
        // Create empty graph with all the nodes of the original graph.
        // But note there are no edges added.

        final MutableUndirectedGraph<N> minimumSpanningTree = new MutableUndirectedGraph<>();
        graph.nodes().forEach(minimumSpanningTree::addNode);

        // STEP 3
        // Iterate over all sorted edges and add each, unless they cause a cycle. Skip the rest.
        // Use simple DFS algorithm to find the cycle possibility.

        sortedEdges
                .stream()
                .filter(edge -> !isReachable(minimumSpanningTree, edge.either(), edge.another()))
                .forEach(edge -> minimumSpanningTree.addEdge(edge.either(), edge.another()));

        return minimumSpanningTree;
    }

    private static <N> boolean isReachable(final UndirectedGraph<N> graph, final N either, final N another) {
        final Stack<N> open = new Stack<>();
        final Set<N> closed = new HashSet<>();
        open.push(either);
        while (!open.isEmpty()) {
            final N temp = open.pop();
            if (temp == another) {
                return true;
            }
            if (!closed.contains(temp)) {
                closed.add(temp);
                graph.successors(temp).forEach(open::push);
            }
        }
        return false;
    }
}
