package graph.algorithm.color;

import graph.model.MutableDirectedGraph;
import org.junit.Test;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by vojta on 18/08/15.
 */
public class WelshPowellTest {
    @Test
    public void testEmptyGraph() {
        final MutableDirectedGraph<String> g = new MutableDirectedGraph<>();

        final Map<String, Integer> colors = WelshPowell.colorByWelshPowell(g);
        assertTrue(colors.isEmpty());
    }

    @Test
    public void testSingleNodeGraph() {
        final MutableDirectedGraph<String> g = new MutableDirectedGraph<>();
        g.addNode("a");

        final Map<String, Integer> colors = WelshPowell.colorByWelshPowell(g);
        assertEquals(1, colors.size());
        assertEquals(0, (int) colors.get("a"));
    }

    @Test
    public void testRegularGraph() {
        final MutableDirectedGraph<String> g = new MutableDirectedGraph<>();
        Stream.of("a", "b", "c", "d", "e", "f", "g").forEach(g::addNode);
        g.addEdge("a", "c");
        g.addEdge("b", "c");
        g.addEdge("b", "e");
        g.addEdge("c", "d");
        g.addEdge("c", "e");
        g.addEdge("c", "f");
        g.addEdge("e", "f");
        g.addEdge("e", "g");
        g.addEdge("f", "g");

        final Map<String, Integer> colors = WelshPowell.colorByWelshPowell(g);
        assertEquals(7, colors.size());
        assertEquals(3, colors.values().stream().distinct().count());
        assertEquals(0, (int) colors.get("c"));
        assertEquals(0, (int) colors.get("g"));
        assertEquals(1, (int) colors.get("a"));
        assertEquals(1, (int) colors.get("e"));
        assertEquals(1, (int) colors.get("d"));
        assertEquals(2, (int) colors.get("b"));
        assertEquals(2, (int) colors.get("f"));
    }
}