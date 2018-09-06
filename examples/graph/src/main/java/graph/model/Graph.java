package graph.model;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiFunction;

public interface Graph<NODE, EDGE> {
    Set<NODE> nodes();

    Set<EDGE> edges();

    BiFunction<NODE, NODE, EDGE> incidence();

    default EDGE edgeIncidentWithNodes(final NODE node1, final NODE node2) {
        return incidence().apply(node1, node2);
    }

    default Set<EDGE> edgesIncomingTo(final NODE node) {
        final Set<EDGE> result = new LinkedHashSet<>();
        for (final NODE temp : nodes()) {
            final EDGE edge = edgeIncidentWithNodes(temp, node);
            if (edge != null) {
                result.add(edge);
            }
        }
        return result;
    }

    default Set<EDGE> edgesOutgoingFrom(final NODE node) {
        final Set<EDGE> result = new LinkedHashSet<>();
        for (final NODE temp : nodes()) {
            final EDGE edge = edgeIncidentWithNodes(node, temp);
            if (edge != null) {
                result.add(edge);
            }
        }
        return result;
    }

    default Set<NODE> successors(final NODE node) {
        final Set<NODE> result = new LinkedHashSet<>();
        for (final NODE temp : nodes()) {
            final EDGE edge = edgeIncidentWithNodes(node, temp);
            if (edge != null) {
                result.add(temp);
            }
        }
        return result;
    }

    default Set<NODE> predecessors(final NODE node) {
        final Set<NODE> result = new LinkedHashSet<>();
        for (final NODE temp : nodes()) {
            final EDGE edge = edgeIncidentWithNodes(temp, node);
            if (edge != null) {
                result.add(temp);
            }
        }
        return result;
    }
}
