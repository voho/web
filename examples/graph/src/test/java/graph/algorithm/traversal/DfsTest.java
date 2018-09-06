package graph.algorithm.traversal;

import graph.model.MutableDirectedGraph;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class DfsTest {
    @Test
    public void testDfsSingleNode() {
        final graph.model.MutableDirectedGraph<String> g = new MutableDirectedGraph<>();
        g.addNode("a");

        final List<String> dfs = Dfs.traverseDepthFirst(g, "a");
        assertEquals(Arrays.asList("a"), dfs);
    }

    @Test
    public void testDfsRegular() {
        final graph.model.MutableDirectedGraph<String> g = new graph.model.MutableDirectedGraph<>();
        Stream.of("a", "b", "c", "d", "e", "f", "g").forEach(g::addNode);
        g.addEdge("a", "b");
        g.addEdge("a", "c");
        g.addEdge("a", "e");
        g.addEdge("b", "d");
        g.addEdge("b", "f");
        g.addEdge("c", "g");
        g.addEdge("e", "f");

        final List<String> dfs = Dfs.traverseDepthFirst(g, "a");
        assertEquals(Arrays.asList("a", "e", "f", "c", "g", "b", "d"), dfs);
    }
}