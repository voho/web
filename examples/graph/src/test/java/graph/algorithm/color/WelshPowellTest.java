package graph.algorithm.color;

import cz.voho.grafo.Graph;
import cz.voho.grafo.MutableUndirectedGraph;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by vojta on 18/08/15.
 */
public class WelshPowellTest {
    @Test
    public void testEmptyGraph() {
        final MutableUndirectedGraph<String, ?> g = Graph.createMutableUndirectedGraph();

        final Map<String, Integer> colors = WelshPowell.colorByWelshPowell(g);
        assertTrue(colors.isEmpty());
    }

    @Test
    public void testSingleNodeGraph() {
        final MutableUndirectedGraph<String, ?> g = Graph.createMutableUndirectedGraph();
        g.addNode("a");

        final Map<String, Integer> colors = WelshPowell.colorByWelshPowell(g);
        assertEquals(1, colors.size());
        assertEquals(0, (int) colors.get("a"));
    }

    @Test
    public void testRegularGraph() {
        final MutableUndirectedGraph<String, Object> g = Graph.createMutableUndirectedGraph();
        g.addNodes("a", "b", "c", "d", "e", "f", "g");
        g.addEdge(1, "a", "c");
        g.addEdge(2, "b", "c");
        g.addEdge(3, "b", "e");
        g.addEdge(4, "c", "d");
        g.addEdge(5, "c", "e");
        g.addEdge(6, "c", "f");
        g.addEdge(7, "e", "f");
        g.addEdge(8, "e", "g");
        g.addEdge(9, "f", "g");

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