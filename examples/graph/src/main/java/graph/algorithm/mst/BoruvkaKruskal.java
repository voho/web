package graph.algorithm.mst;

import cz.voho.grafo.Graph;
import cz.voho.grafo.MutableUndirectedGraph;
import cz.voho.grafo.UndirectedGraph;
import cz.voho.grafo.UnorderedPair;
import cz.voho.grafo.WeightedEdge;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of Boruvka-Kruskal`s algorithm.
 */
public final class BoruvkaKruskal {
    /**
     * Finds the minimum spanning tree of the given graph.
     *
     * @param graph original graph
     * @return minimum spanning tree
     */
    public static <N, E extends WeightedEdge<Integer>> UndirectedGraph<N, E> compute(final UndirectedGraph<N, E> graph) {
        // STEP 1
        // Sort edges by weight (ascending).

        final List<E> sortedEdges = graph
                .edges()
                .stream()
                .sorted(Comparator.comparingInt(WeightedEdge::getEdgeWeight))
                .collect(Collectors.toList());

        // STEP 2
        // Create empty graph with all the nodes of the original graph.
        // But note there are no edges added.

        final MutableUndirectedGraph<N, E> minimumSpanningTree = Graph.createMutableUndirectedGraph();
        graph.nodes().forEach(minimumSpanningTree::addNode);

        // STEP 3
        // Iterate over all sorted edges and add each, unless they cause a cycle. Skip the rest.

        sortedEdges
                .stream()
                .filter(edge -> {
                    UnorderedPair<N> pair = graph.incidence(edge);
                    return !minimumSpanningTree.isReachable(pair.getEither(), pair.getAnother());
                })
                .forEach(edge -> {
                    final UnorderedPair<N> pair = graph.incidence(edge);
                    minimumSpanningTree.addEdge(edge, pair.getEither(), pair.getAnother());
                });

        return minimumSpanningTree;
    }
}
