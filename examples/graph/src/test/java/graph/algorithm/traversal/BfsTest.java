package graph.algorithm.traversal;

import cz.voho.grafo.Graph;
import cz.voho.grafo.MutableDirectedGraph;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BfsTest {
    @Test
    public void testBfsSingleNode() {
        final MutableDirectedGraph<String, String> g = Graph.createMutableDirectedGraph();
        g.addNode("a");

        final List<String> bfs = Bfs.traverseBreadthFirst(g, "a");
        assertEquals(Arrays.asList("a"), bfs);
    }

    @Test
    public void testBfsRegular() {
        final MutableDirectedGraph<String, String> g = Graph.createMutableDirectedGraph();
        g.addNodes("a", "b", "c", "d", "e", "f", "g");
        g.addEdge("a-b", "a", "b");
        g.addEdge("a-c", "a", "c");
        g.addEdge("a-e", "a", "e");
        g.addEdge("b-d", "b", "d");
        g.addEdge("b-f", "b", "f");
        g.addEdge("c-g", "c", "g");
        g.addEdge("e-f", "e", "f");

        final List<String> bfs = Bfs.traverseBreadthFirst(g, "a");
        assertEquals(Arrays.asList("a", "b", "c", "e", "d", "f", "g"), bfs);
    }
}