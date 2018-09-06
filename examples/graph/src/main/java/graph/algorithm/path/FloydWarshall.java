package graph.algorithm.path;

import graph.model.Graph;

import java.util.*;
import java.util.function.ToIntFunction;

/**
 * Implementation of Floyd-Warshall`s algorithm.
 */
public final class FloydWarshall {
    /**
     * Finds all pair`s shortest paths.
     * @param graph original graph
     * @param weighter edge weight provider
     * @return output containing the shortest paths between any two of the graph nodes
     */
    public static <N, E> FloydWarshallOutput calculate(final Graph<N, E> graph, final ToIntFunction<E> weighter) {
        final List<N> nodes = new ArrayList<>(graph.nodes());
        final FloydWarshallMatrix<Integer, N> matrix = new FloydWarshallMatrix<>(nodes.size());

        for (int iX = 0; iX < matrix.size(); iX++) {
            for (int iY = 0; iY < matrix.size(); iY++) {
                final N xNode = nodes.get(iX);
                final N yNode = nodes.get(iY);

                if (iX == iY) {
                    // the same node - distance is zero
                    matrix.setMinimumDistance(iX, iY, 0);
                    matrix.setPredecessor(iX, iY, xNode);
                } else {
                    final E edge = graph.edgeIncidentWithNodes(xNode, yNode);

                    if (edge != null) {
                        // edge is defined - define distance
                        final int distance = weighter.applyAsInt(edge);
                        matrix.setMinimumDistance(iX, iY, distance);
                        matrix.setPredecessor(iX, iY, xNode);
                    }
                }
            }
        }

        for (int iDetour = 0; iDetour < matrix.size(); iDetour++) {
            for (int iX = 0; iX < matrix.size(); iX++) {
                for (int iY = 0; iY < matrix.size(); iY++) {
                    final Optional<Integer> detourPart1 = matrix.get(iX, iDetour);
                    final Optional<Integer> detourPart2 = matrix.get(iDetour, iY);

                    if (detourPart1.isPresent() && detourPart2.isPresent()) {
                        final int detourDistance = detourPart1.get() + detourPart2.get();
                        final Optional<Integer> currentDistance = matrix.get(iX, iY);

                        if (!currentDistance.isPresent() || detourDistance < currentDistance.get()) {
                            // the detour is better than what we have so far
                            final N detourNode = nodes.get(iDetour);
                            matrix.setMinimumDistance(iX, iY, detourDistance);
                            matrix.setPredecessor(iX, iY, detourNode);
                        }
                    }
                }
            }
        }

        return new FloydWarshallOutput<N>() {
            @Override
            public Optional<Integer> getMinimalDistance(final N a, final N b) {
                final int iA = nodes.indexOf(a);
                final int iB = nodes.indexOf(b);

                if (iA == -1 || iB == -1) {
                    // unknown node
                    return Optional.empty();
                }

                return matrix.get(iA, iB);
            }

            @Override
            public Optional<List<N>> getShortestPath(final N a, final N b) {
                final int iTarget = nodes.indexOf(a);
                int iStart = nodes.indexOf(b);

                if (iStart == -1 || iTarget == -1) {
                    // unknown node
                    return Optional.empty();
                }

                final List<N> result = new LinkedList<>();

                while (true) {
                    result.add(nodes.get(iStart));

                    if (iStart == iTarget) {
                        // we reached the target node
                        // (we must reverse the path, because we started at the end)
                        Collections.reverse(result);
                        return Optional.of(result);
                    }

                    final Optional<N> parent = matrix.getParent(iTarget, iStart);

                    if (parent.isPresent()) {
                        // advance to next node
                        iStart = nodes.indexOf(parent.get());
                    } else {
                        // this should never happen as matrix is under our control
                        return Optional.empty();
                    }
                }
            }
        };
    }

}
