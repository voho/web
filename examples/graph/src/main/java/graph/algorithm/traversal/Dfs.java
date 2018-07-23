package graph.algorithm.traversal;

import com.google.common.base.Preconditions;
import cz.voho.grafo.Graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * Implementation of depth-first traversal algorithm.
 */
public final class Dfs {
    /**
     * Performs a DFS walk starting from the given node.
     *
     * @param graph        graph to walk
     * @param startingNode node to start walking from (must be in the given graph)
     * @return list of nodes in order they were visited
     */
    public static <N> List<N> traverseDepthFirst(final Graph<N, ?, ?> graph, final N startingNode) {
        Preconditions.checkArgument(graph.hasNode(startingNode));

        final Set<N> allNodes = graph.nodes();
        final int n = allNodes.size();
        final List<N> result = new ArrayList<>(n);
        final Set<N> closed = new HashSet<>(n);
        final Stack<N> open = new Stack<>();

        // initialize the stack with the root node
        open.push(startingNode);

        while (!open.isEmpty()) {
            // pop the top node
            final N active = open.pop();

            if (!closed.contains(active)) {
                // add the node to result
                result.add(active);

                // mark the node as visited
                closed.add(active);

                // add the node neighbours to the stack
                graph.nodesDirectlyReachableFromNode(active).forEach(open::add);
            }
        }

        return result;
    }
}
