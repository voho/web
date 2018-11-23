package graph.algorithm.topo;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class ToposorterTest {
    @Test
    public void test() {
        final Toposorter<String> sorter = new Toposorter<>();

        sorter.addDependency("pásek", "sako");
        sorter.addDependency("kravata", "sako");
        sorter.addDependency("košile", "pásek");
        sorter.addDependency("kalhoty", "pásek");
        sorter.addDependency("kalhoty", "boty");
        sorter.addDependency("košile", "kravata");
        sorter.addDependency("trenky", "kalhoty");
        sorter.addDependency("trenky", "boty");
        sorter.addDependency("ponožky", "boty");

        sorter.compute();

        assertEquals(
                Arrays.asList("košile", "trenky", "kalhoty", "pásek", "kravata", "sako", "ponožky", "boty"),
                sorter.getTopologicalOrder()
        );
    }
}