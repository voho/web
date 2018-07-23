package graph.algorithm.mst;

import cz.voho.grafo.Graph;
import cz.voho.grafo.MutableUndirectedGraph;
import cz.voho.grafo.UndirectedGraph;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by vojta on 18/08/15.
 */
public class JarnikPrimTest {
    @Test
    public void testEmpty() {
        final MutableUndirectedGraph<String, Wedge> g = Graph.createMutableUndirectedGraph();

        final UndirectedGraph<String, Wedge> mst = JarnikPrim.compute(g);
        assertEquals(0, mst.nodes().size());
        assertEquals(0, mst.edges().size());
    }

    @Test
    public void testSingleNode() {
        final MutableUndirectedGraph<String, Wedge> g = Graph.createMutableUndirectedGraph();
        g.addNode("a");

        final UndirectedGraph<String, Wedge> mst = JarnikPrim.compute(g);
        assertEquals(1, mst.nodes().size());
        assertEquals(0, mst.edges().size());
    }

    @Test
    public void testSingleEdge() {
        final MutableUndirectedGraph<String, Wedge> g = Graph.createMutableUndirectedGraph();
        g.addNode("a");
        g.addNode("b");
        g.addEdge(new Wedge(10), "a", "b");

        final UndirectedGraph<String, Wedge> mst = JarnikPrim.compute(g);
        assertEquals(2, mst.nodes().size());
        assertEquals(1, mst.edges().size());
        assertTrue(mst.isDirectlyReachable("a", "b"));
        assertEquals(10, mst.edges().stream().mapToInt(Wedge::getEdgeWeight).sum());
    }

    @Test
    public void testRegularCase() {
        final MutableUndirectedGraph<String, Wedge> g = Graph.createMutableUndirectedGraph();
        Arrays.asList("a", "b", "c", "d", "e", "f").forEach(g::addNode);
        g.addEdge(new Wedge(5), "a", "b");
        g.addEdge(new Wedge(2), "b", "d");
        g.addEdge(new Wedge(3), "b", "f");
        g.addEdge(new Wedge(1), "b", "c");
        g.addEdge(new Wedge(3), "b", "e");
        g.addEdge(new Wedge(9), "d", "f");
        g.addEdge(new Wedge(1), "d", "e");

        final UndirectedGraph<String, Wedge> mst = JarnikPrim.compute(g);
        assertEquals(6, mst.nodes().size());
        assertEquals(5, mst.edges().size());
        assertTrue(mst.isDirectlyReachable("a", "b"));
        assertTrue(mst.isDirectlyReachable("b", "c"));
        assertTrue(mst.isDirectlyReachable("b", "d"));
        assertTrue(mst.isDirectlyReachable("b", "f"));
        assertTrue(mst.isDirectlyReachable("d", "e"));
        assertEquals(12, mst.edges().stream().mapToInt(Wedge::getEdgeWeight).sum());
    }

    @Test
    public void testComplexCase() {
        final MutableUndirectedGraph<String, Wedge> g = Graph.createMutableUndirectedGraph();
        Arrays.asList("a", "b", "c", "d", "e", "f", "g").forEach(g::addNode);
        g.addEdge(new Wedge(7), "a", "b");
        g.addEdge(new Wedge(5), "a", "d");
        g.addEdge(new Wedge(9), "b", "d");
        g.addEdge(new Wedge(8), "b", "c");
        g.addEdge(new Wedge(7), "b", "e");
        g.addEdge(new Wedge(5), "c", "e");
        g.addEdge(new Wedge(15), "d", "e");
        g.addEdge(new Wedge(6), "d", "f");
        g.addEdge(new Wedge(8), "f", "e");
        g.addEdge(new Wedge(11), "f", "g");
        g.addEdge(new Wedge(9), "g", "e");

        final UndirectedGraph<String, Wedge> mst = JarnikPrim.compute(g);
        assertEquals(7, mst.nodes().size());
        assertEquals(6, mst.edges().size());
        assertTrue(mst.isDirectlyReachable("f", "d"));
        assertTrue(mst.isDirectlyReachable("d", "a"));
        assertTrue(mst.isDirectlyReachable("a", "b"));
        assertTrue(mst.isDirectlyReachable("b", "e"));
        assertTrue(mst.isDirectlyReachable("e", "c"));
        assertTrue(mst.isDirectlyReachable("e", "g"));
        assertEquals(39, mst.edges().stream().mapToInt(Wedge::getEdgeWeight).sum());
    }
}