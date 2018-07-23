package graph.algorithm.mst;

import cz.voho.grafo.Graph;
import cz.voho.grafo.MutableUndirectedGraph;
import cz.voho.grafo.UndirectedGraph;
import cz.voho.grafo.UnorderedPair;
import cz.voho.grafo.WeightedEdge;

import java.util.Comparator;
import java.util.Optional;

/**
 * Implementation of Jarnik-Prim`s algorithm.
 */
public final class JarnikPrim {
    /**
     * Finds the minimum spanning tree of the given graph.
     *
     * @param originalGraph original graph
     * @return minimum spanning tree
     */
    public static <N, E extends WeightedEdge<Integer>> UndirectedGraph<N, E> compute(final UndirectedGraph<N, E> originalGraph) {
        final MutableUndirectedGraph<N, E> result = Graph.createMutableUndirectedGraph();

        if (!originalGraph.isEmpty()) {
            // chose arbitrary node to start with

            result.addNode(originalGraph.nodes().iterator().next());

            // grow by one edge

            while (result.nodeCount() < originalGraph.nodeCount()) {
                // find an edge that connects some node from MST to some non-MST nodes
                // and has the minimum weight of such edges

                final Optional<E> edgeToAddMaybe = originalGraph
                        .edges()
                        .stream()
                        .filter(edge -> {
                            UnorderedPair<N> pair = originalGraph.incidence(edge);
                            final N eitherNode = pair.getEither();
                            final N anotherNode = pair.getAnother();

                            boolean p1 = result.hasNode(eitherNode) && !result.hasNode(anotherNode);
                            boolean p2 = result.hasNode(anotherNode) && !result.hasNode(eitherNode);
                            return p1 || p2;
                        })
                        .min(Comparator.comparingInt(WeightedEdge::getEdgeWeight));

                if (!edgeToAddMaybe.isPresent()) {
                    throw new IllegalArgumentException("Graph is not strongly connected.");
                }

                // merge the edge with the result

                final E edgeToAdd = edgeToAddMaybe.get();
                final UnorderedPair<N> pair = originalGraph.incidence(edgeToAdd);
                final N eitherNode = pair.getEither();
                final N anotherNode = pair.getAnother();

                if (!result.hasNode(eitherNode)) {
                    result.addNode(eitherNode);
                }

                if (!result.hasNode(anotherNode)) {
                    result.addNode(anotherNode);
                }

                result.addEdge(edgeToAdd, eitherNode, anotherNode);
            }
        }

        return result;
    }
}
