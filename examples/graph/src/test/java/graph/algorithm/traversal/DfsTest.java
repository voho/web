package graph.algorithm.traversal;

import cz.voho.grafo.Graph;
import cz.voho.grafo.MutableDirectedGraph;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DfsTest {
    @Test
    public void testDfsSingleNode() {
        final MutableDirectedGraph<String, String> g = Graph.createMutableDirectedGraph();
        g.addNode("a");

        final List<String> dfs = Dfs.traverseDepthFirst(g, "a");
        assertEquals(Arrays.asList("a"), dfs);
    }

    @Test
    public void testDfsRegular() {
        final MutableDirectedGraph<String, String> g = Graph.createMutableDirectedGraph();
        g.addNodes("a", "b", "c", "d", "e", "f", "g");
        g.addEdge("a-b", "a", "b");
        g.addEdge("a-c", "a", "c");
        g.addEdge("a-e", "a", "e");
        g.addEdge("b-d", "b", "d");
        g.addEdge("b-f", "b", "f");
        g.addEdge("c-g", "c", "g");
        g.addEdge("e-f", "e", "f");

        final List<String> dfs = Dfs.traverseDepthFirst(g, "a");
        assertEquals(Arrays.asList("a", "e", "f", "c", "g", "b", "d"), dfs);
    }
}