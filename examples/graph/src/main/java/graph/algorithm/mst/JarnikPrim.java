package graph.algorithm.mst;

import graph.model.MutableUndirectedGraph;
import graph.model.UndirectedGraph;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.ToIntFunction;

/**
 * Implementation of Jarnik-Prim`s algorithm.
 */
public final class JarnikPrim {
    /**
     * Finds the minimum spanning tree of the given graph.
     * @param graph original graph
     * @return minimum spanning tree
     */
    public static <N> UndirectedGraph<N> compute(final UndirectedGraph<N> graph, final ToIntFunction<UndirectedGraph.Edge<N>> weighter) {
        final MutableUndirectedGraph<N> result = new MutableUndirectedGraph<>();

        if (!graph.nodes().isEmpty()) {
            // chose arbitrary node to start with

            result.addNode(graph.nodes().iterator().next());

            // grow by one edge

            while (result.nodes().size() < graph.nodes().size()) {
                // find an edge that connects some node from MST to some non-MST nodes
                // and has the minimum weight of such edges

                final Optional<UndirectedGraph.Edge<N>> edgeToAddMaybe = graph
                        .edges()
                        .stream()
                        .filter(edge -> {
                            final N eitherNode = edge.either();
                            final N anotherNode = edge.another();
                            boolean p1 = result.nodes().contains(eitherNode) && !result.nodes().contains(anotherNode);
                            boolean p2 = result.nodes().contains(anotherNode) && !result.nodes().contains(eitherNode);
                            return p1 || p2;
                        })
                        .min(Comparator.comparingInt(weighter));

                if (!edgeToAddMaybe.isPresent()) {
                    throw new IllegalArgumentException("Graph is not strongly connected.");
                }

                // merge the edge with the result

                final N eitherNode = edgeToAddMaybe.get().either();
                final N anotherNode = edgeToAddMaybe.get().another();

                if (!result.nodes().contains(eitherNode)) {
                    result.addNode(eitherNode);
                }

                if (!result.nodes().contains(anotherNode)) {
                    result.addNode(anotherNode);
                }

                result.addEdge(eitherNode, anotherNode);
            }
        }

        return result;
    }
}
