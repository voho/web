package graph.algorithm.traversal;

import graph.model.MutableDirectedGraph;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class BfsTest {
    @Test
    public void testBfsSingleNode() {
        final MutableDirectedGraph<String> g = new MutableDirectedGraph<>();
        g.addNode("a");

        final List<String> bfs = Bfs.traverseBreadthFirst(g, "a");
        assertEquals(Arrays.asList("a"), bfs);
    }

    @Test
    public void testBfsRegular() {
        final MutableDirectedGraph<String> g = new MutableDirectedGraph<>();
        Stream.of("a", "b", "c", "d", "e", "f", "g").forEach(g::addNode);
        g.addEdge("a", "b");
        g.addEdge("a", "c");
        g.addEdge("a", "e");
        g.addEdge("b", "d");
        g.addEdge("b", "f");
        g.addEdge("c", "g");
        g.addEdge("e", "f");

        final List<String> bfs = Bfs.traverseBreadthFirst(g, "a");
        assertEquals(Arrays.asList("a", "b", "c", "e", "d", "f", "g"), bfs);
    }
}