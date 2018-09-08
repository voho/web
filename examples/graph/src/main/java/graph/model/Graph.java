package graph.model;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
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
        return successorsWithEdges(node).keySet();
    }

    default Set<NODE> predecessors(final NODE node) {
        return predecessorsWithEdges(node).keySet();
    }

    default Map<NODE, EDGE> successorsWithEdges(final NODE node) {
        final Map<NODE, EDGE> result = new LinkedHashMap<>();
        for (final NODE temp : nodes()) {
            final EDGE edge = edgeIncidentWithNodes(node, temp);
            if (edge != null) {
                result.put(temp, edge);
            }
        }
        return result;
    }

    default Map<NODE, EDGE> predecessorsWithEdges(final NODE node) {
        final Map<NODE, EDGE> result = new LinkedHashMap<>();
        for (final NODE temp : nodes()) {
            final EDGE edge = edgeIncidentWithNodes(temp, node);
            if (edge != null) {
                result.put(temp, edge);
            }
        }
        return result;
    }

    default Set<NODE> adjacent(final NODE node) {
        final Set<NODE> result = new LinkedHashSet<>();
        for (final NODE temp : nodes()) {
            final EDGE outgoingEdge = edgeIncidentWithNodes(node, temp);
            final EDGE incomingEdge = edgeIncidentWithNodes(temp, node);
            if (outgoingEdge != null || incomingEdge != null) {
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

    default boolean isAdjacent(final NODE first, final NODE second) {
        return isPredecessor(first, second) || isSuccessor(first, second);
    }
}
