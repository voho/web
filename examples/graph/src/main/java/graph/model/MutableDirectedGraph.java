package graph.model;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiFunction;

public class MutableDirectedGraph<NODE> implements Graph<NODE, MutableDirectedGraph.DirectedEdge<NODE>> {
    private final Set<NODE> nodes = new LinkedHashSet<>();
    private final Set<MutableDirectedGraph.DirectedEdge<NODE>> edges = new LinkedHashSet<>();
    private final Table<NODE, NODE, MutableDirectedGraph.DirectedEdge<NODE>> incidence = HashBasedTable.create();

    public NODE addNode(final NODE node) {
        nodes.add(node);
        return node;
    }

    public MutableDirectedGraph.DirectedEdge<NODE> addEdge(final NODE source, final NODE target) {
        addNode(source);
        addNode(target);
        final DirectedEdge<NODE> newEdge = new DirectedEdge<>(source, target);
        edges.add(newEdge);
        incidence.put(source, target, newEdge);
        return newEdge;
    }

    @Override
    public Set<NODE> nodes() {
        return nodes;
    }

    @Override
    public Set<DirectedEdge<NODE>> edges() {
        return edges;
    }

    @Override
    public BiFunction<NODE, NODE, DirectedEdge<NODE>> incidence() {
        return incidence::get;
    }

    public static class DirectedEdge<NODE> {
        private final NODE source;
        private final NODE target;

        private DirectedEdge(final NODE source, final NODE target) {
            this.source = source;
            this.target = target;
        }

        public NODE source() {
            return source;
        }

        public NODE target() {
            return target;
        }
    }
}
