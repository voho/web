package graph.model;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

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
        return nodes().stream().filter(temp -> isSuccessor(node, temp)).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    default Set<NODE> predecessors(final NODE node) {
        return nodes().stream().filter(temp -> isPredecessor(node, temp)).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    default Set<NODE> neighbours(final NODE node) {
        final Set<NODE> result = new LinkedHashSet<>();
        for (final NODE temp : nodes()) {
            final EDGE edge1 = edgeIncidentWithNodes(node, temp);
            final EDGE edge2 = edgeIncidentWithNodes(temp, node);
            if (edge1 != null || edge2 != null) {
                result.add(temp);
            }
        }
        return result;
    }

    default boolean isSuccessor(final NODE ref, final NODE node) {
        return edgeIncidentWithNodes(ref, node) != null;
    }

    default boolean isPredecessor(final NODE ref, final NODE node) {
        return isSuccessor(node, ref);
    }

    default boolean isNeighbour(final NODE first, final NODE second) {
        return isPredecessor(first, second) || isSuccessor(first, second);
    }
}
