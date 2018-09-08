package graph.model;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiFunction;

public class MutableUndirectedGraph<NODE> implements UndirectedGraph<NODE> {
    private final Set<NODE> nodes = new LinkedHashSet<>();
    private final Set<Edge<NODE>> edges = new LinkedHashSet<>();
    private final Table<NODE, NODE, Edge<NODE>> incidence = HashBasedTable.create();

    public NODE addNode(final NODE node) {
        nodes.add(node);
        return node;
    }

    public Edge<NODE> addEdge(final NODE either, final NODE another) {
        addNode(either);
        addNode(another);
        final Edge<NODE> newEdge = new Edge<>(either, another);
        edges.add(newEdge);
        incidence.put(either, another, newEdge);
        incidence.put(another, either, newEdge);
        return newEdge;
    }

    @Override
    public Set<NODE> nodes() {
        return nodes;
    }

    @Override
    public Set<Edge<NODE>> edges() {
        return edges;
    }

    @Override
    public BiFunction<NODE, NODE, Edge<NODE>> incidence() {
        return incidence::get;
    }
}
