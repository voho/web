package graph.algorithm.path;

import cz.voho.grafo.Graph;
import cz.voho.grafo.MutableGraph;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

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
        final MutableGraph<String, Integer, ?> g = Graph.createMutableDirectedGraph();
        g.addNodes(nodes);
        int i = 0;
        for (final Object[] datum : data) {
            g.addEdge(i++, (String) datum[0], (String) datum[1]);
        }
        final Function<Integer, Integer> w = integer -> {
            final Object[] row = data[integer];
            return (Integer) row[2];
        };
        return FloydWarshall.calculate(g, w);
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