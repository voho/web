package graph.algorithm.path;

import graph.model.DirectedGraph;
import graph.model.MutableDirectedGraph;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class FloydWarshallTest {
    private static void assertShortestPath(final FloydWarshallOutput<String> output, final String a, final String b, final int expDistance, final List<String> expPath) {
        assertEquals(Optional.of(expPath), output.getShortestPath(a, b));
        assertEquals(Optional.of(expDistance), output.getMinimalDistance(a, b));
    }

    private static void assertNoPath(final FloydWarshallOutput<String> output, final String a, final String b) {
        assertFalse(output.getShortestPath(a, b).isPresent());
        assertFalse(output.getMinimalDistance(a, b).isPresent());
    }

    private static FloydWarshallOutput<String> calculate(final String[] nodes, final Object[][] data) {
        final MutableDirectedGraph<String> g = new MutableDirectedGraph<>();
        Arrays.stream(nodes).forEach(g::addNode);
        final Map<DirectedGraph.Edge<String>, Integer> weight = new HashMap<>();
        for (final Object[] datum : data) {
            final DirectedGraph.Edge<String> edge = g.addEdge((String) datum[0], (String) datum[1]);
            weight.put(edge, (Integer) datum[2]);
        }
        return FloydWarshall.calculate(g, weight::get);
    }

    @Test
    public void testExample1() {
        final String[] nodes = {"1", "2", "3", "4"};

        final FloydWarshallOutput<String> output = calculate(
                nodes,
                new Object[][]{
                        new Object[]{"1", "2", 1},
                        new Object[]{"2", "3", 2},
                        new Object[]{"3", "4", 3},
                        new Object[]{"4", "1", 4},
                        new Object[]{"4", "2", 7}
                }
        );

        assertNoPath(output, "1", "XXX");
        assertNoPath(output, "1", "XXX");
        assertNoPath(output, "XXX", "1");
        assertNoPath(output, "XXX", "XXX");
        assertNoPath(output, "XXX", "YYY");

        assertShortestPath(output, "1", "1", 0, Arrays.asList("1"));
        assertShortestPath(output, "1", "2", 1, Arrays.asList("1", "2"));
        assertShortestPath(output, "1", "3", 3, Arrays.asList("1", "2", "3"));
        assertShortestPath(output, "1", "4", 6, Arrays.asList("1", "2", "3", "4"));
        assertShortestPath(output, "2", "1", 9, Arrays.asList("2", "3", "4", "1"));
        assertShortestPath(output, "2", "2", 0, Arrays.asList("2"));
        assertShortestPath(output, "2", "3", 2, Arrays.asList("2", "3"));
        assertShortestPath(output, "2", "4", 5, Arrays.asList("2", "3", "4"));
        assertShortestPath(output, "3", "1", 7, Arrays.asList("3", "4", "1"));
        assertShortestPath(output, "3", "2", 8, Arrays.asList("3", "4", "2"));
        assertShortestPath(output, "3", "3", 0, Arrays.asList("3"));
        assertShortestPath(output, "3", "4", 3, Arrays.asList("3", "4"));
        assertShortestPath(output, "4", "1", 4, Arrays.asList("4", "1"));
        assertShortestPath(output, "4", "2", 5, Arrays.asList("4", "1", "2"));
        assertShortestPath(output, "4", "3", 7, Arrays.asList("4", "1", "2", "3"));
        assertShortestPath(output, "4", "4", 0, Arrays.asList("4"));
    }
}