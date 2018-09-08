package graph.algorithm.mst;

import graph.model.MutableUndirectedGraph;
import graph.model.UndirectedGraph;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by vojta on 18/08/15.
 */
public class JarnikPrimTest {
    @Test
    public void testEmpty() {
        final MutableUndirectedGraph<String> g = new MutableUndirectedGraph<>();

        final UndirectedGraph<String> mst = JarnikPrim.compute(g, (edge) -> 0);
        assertEquals(0, mst.nodes().size());
        assertEquals(0, mst.edges().size());
    }

    @Test
    public void testSingleNode() {
        final MutableUndirectedGraph<String> g = new MutableUndirectedGraph<>();
        g.addNode("a");

        final UndirectedGraph<String> mst = JarnikPrim.compute(g, (edge) -> 0);
        assertEquals(1, mst.nodes().size());
        assertEquals(0, mst.edges().size());
    }

    @Test
    public void testSingleEdge() {
        final Map<UndirectedGraph.Edge<String>, Integer> w = new HashMap<>();
        final MutableUndirectedGraph<String> g = new MutableUndirectedGraph<>();
        g.addNode("a");
        g.addNode("b");
        w.put(g.addEdge("a", "b"), 10);

        final UndirectedGraph<String> mst = JarnikPrim.compute(g, w::get);
        assertEquals(2, mst.nodes().size());
        assertEquals(1, mst.edges().size());
        assertTrue(mst.isSuccessor("a", "b"));
        assertEquals(10, mst.edges().stream().mapToInt(w::get).sum());
    }

    @Test
    public void testRegularCase() {
        final Map<UndirectedGraph.Edge<String>, Integer> w = new HashMap<>();
        final MutableUndirectedGraph<String> g = new MutableUndirectedGraph<>();
        Arrays.asList("a", "b", "c", "d", "e", "f").forEach(g::addNode);
        w.put(g.addEdge("a", "b"), 5);
        w.put(g.addEdge("b", "d"), 2);
        w.put(g.addEdge("b", "f"), 3);
        w.put(g.addEdge("b", "c"), 1);
        w.put(g.addEdge("b", "e"), 3);
        w.put(g.addEdge("d", "f"), 9);
        w.put(g.addEdge("d", "e"), 1);

        final UndirectedGraph<String> mst = JarnikPrim.compute(g, w::get);
        assertEquals(6, mst.nodes().size());
        assertEquals(5, mst.edges().size());
        assertTrue(mst.isSuccessor("a", "b"));
        assertTrue(mst.isSuccessor("b", "c"));
        assertTrue(mst.isSuccessor("b", "d"));
        assertTrue(mst.isSuccessor("b", "f"));
        assertTrue(mst.isSuccessor("d", "e"));
        assertEquals(12, mst.edges().stream().mapToInt(w::get).sum());
    }

    @Test
    public void testComplexCase() {
        final Map<UndirectedGraph.Edge<String>, Integer> w = new HashMap<>();
        final MutableUndirectedGraph<String> g = new MutableUndirectedGraph<>();
        Arrays.asList("a", "b", "c", "d", "e", "f", "g").forEach(g::addNode);
        w.put(g.addEdge("a", "b"), 7);
        w.put(g.addEdge("a", "d"), 5);
        w.put(g.addEdge("b", "d"), 9);
        w.put(g.addEdge("b", "c"), 8);
        w.put(g.addEdge("b", "e"), 7);
        w.put(g.addEdge("c", "e"), 5);
        w.put(g.addEdge("d", "e"), 15);
        w.put(g.addEdge("d", "f"), 6);
        w.put(g.addEdge("f", "e"), 8);
        w.put(g.addEdge("f", "g"), 11);
        w.put(g.addEdge("g", "e"), 9);

        final UndirectedGraph<String> mst = JarnikPrim.compute(g, w::get);
        assertEquals(7, mst.nodes().size());
        assertEquals(6, mst.edges().size());
        assertTrue(mst.isSuccessor("f", "d"));
        assertTrue(mst.isSuccessor("d", "a"));
        assertTrue(mst.isSuccessor("a", "b"));
        assertTrue(mst.isSuccessor("b", "e"));
        assertTrue(mst.isSuccessor("e", "c"));
        assertTrue(mst.isSuccessor("e", "g"));
        assertEquals(39, mst.edges().stream().mapToInt(w::get).sum());
    }
}