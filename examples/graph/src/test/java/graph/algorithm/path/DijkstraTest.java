package graph.algorithm.path;

import graph.model.MutableDirectedGraph;
import graph.model.MutableUndirectedGraph;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DijkstraTest {
    @Test
    public void testSingleNode() {
        final MutableUndirectedGraph<String> g = new MutableUndirectedGraph<>();
        g.addNode("a");

        final DijkstraOutput<String> output = Dijkstra.dijkstra(g, "a", edge -> 0);

        assertEquals(0, output.getDistance("a"));
    }

    @Test
    public void testRegularCase() {
        final Map<Object, Integer> weight = new HashMap<>();

        final MutableDirectedGraph<String> g = new MutableDirectedGraph<>();
        Stream.of("a", "b", "c", "d", "e", "f", "g", "h").forEach(g::addNode);
        weight.put(g.addEdge("a", "b"), 2);
        weight.put(g.addEdge("a", "c"), 1);
        weight.put(g.addEdge("a", "d"), 4);
        weight.put(g.addEdge("b", "c"), 5);
        weight.put(g.addEdge("b", "e"), 10);
        weight.put(g.addEdge("b", "f"), 2);
        weight.put(g.addEdge("c", "a"), 9);
        weight.put(g.addEdge("c", "e"), 11);
        weight.put(g.addEdge("d", "c"), 2);
        weight.put(g.addEdge("e", "d"), 7);
        weight.put(g.addEdge("e", "g"), 1);
        weight.put(g.addEdge("f", "h"), 3);
        weight.put(g.addEdge("g", "e"), 3);
        weight.put(g.addEdge("g", "f"), 2);
        weight.put(g.addEdge("h", "g"), 1);

        final DijkstraOutput<String> output = Dijkstra.dijkstra(g, "a", weight::get);

        assertEquals(0, output.getDistance("a"));
        assertEquals(2, output.getDistance("b"));
        assertEquals(1, output.getDistance("c"));
        assertEquals(4, output.getDistance("d"));
        assertEquals(11, output.getDistance("e"));
        assertEquals(4, output.getDistance("f"));
        assertEquals(8, output.getDistance("g"));
        assertEquals(7, output.getDistance("h"));

        assertNull(output.getPrevious("a"));
        assertEquals("a", output.getPrevious("b"));
        assertEquals("a", output.getPrevious("c"));
        assertEquals("a", output.getPrevious("d"));
        assertEquals("g", output.getPrevious("e"));
        assertEquals("b", output.getPrevious("f"));
        assertEquals("h", output.getPrevious("g"));
        assertEquals("f", output.getPrevious("h"));
    }
}