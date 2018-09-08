package graph.algorithm.path;

import graph.model.MutableUndirectedGraph;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AStarTest {
    @Test
    public void testSingleNode() {
        final MutableUndirectedGraph<String> g = new MutableUndirectedGraph<>();
        g.addNode("a");

        final AStarOutput<String> output = AStar.aStar(g, "a", "a", edge -> 0, node -> 0);

        assertEquals(0, output.getTotalEstimatedDistance("a"));
        assertEquals(0, output.getDistanceSoFar("a"));
        assertNull(output.getPrevious("a"));
    }

    @Test
    public void testRegularCase() {
        final Map<Object, Integer> w = new HashMap<>();
        final Map<Object, Integer> h = new HashMap<>();

        final MutableUndirectedGraph<String> g = new MutableUndirectedGraph<>();
        Stream.of("s", "a", "b", "c", "d", "e", "f", "g").forEach(g::addNode);
        h.put("s", 17);
        h.put("a", 10);
        h.put("b", 13);
        h.put("c", 4);
        h.put("d", 2);
        h.put("e", 4);
        h.put("f", 1);
        h.put("g", 0);
        w.put(g.addEdge("s", "a"), 6);
        w.put(g.addEdge("s", "b"), 5);
        w.put(g.addEdge("s", "c"), 10);
        w.put(g.addEdge("a", "e"), 6);
        w.put(g.addEdge("b", "e"), 6);
        w.put(g.addEdge("b", "d"), 7);
        w.put(g.addEdge("c", "d"), 6);
        w.put(g.addEdge("e", "f"), 4);
        w.put(g.addEdge("d", "f"), 6);
        w.put(g.addEdge("f", "g"), 3);

        final AStarOutput<String> output = AStar.aStar(g, "s", "g", w::get, h::get);

        assertNull(output.getPrevious("s"));
        assertEquals("s", output.getPrevious("b"));
        assertEquals("b", output.getPrevious("e"));
        assertEquals("e", output.getPrevious("f"));
        assertEquals("f", output.getPrevious("g"));
    }
}