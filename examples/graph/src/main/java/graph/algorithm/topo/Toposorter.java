package graph.algorithm.topo;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Toposorter<E> {
    /**
     * množina všech uzlů
     */
    private final Set<E> nodes = new LinkedHashSet<E>();
    /**
     * hrany (mapování uzlu na jeho závislosti)
     */
    private final Map<E, Set<E>> edges = new LinkedHashMap<E, Set<E>>();
    /**
     * množina navštívených uzlů
     */
    private final Set<E> visitedNodes = new LinkedHashSet<E>();
    /**
     * množina zpracovaných uzlů
     */
    private final Set<E> expandedNodes = new LinkedHashSet<E>();
    /**
     * výsledné topologické uspořádání
     */
    private final List<E> result = new LinkedList<E>();

    public List<E> getTopologicalOrder() {
        return Collections.unmodifiableList(this.result);
    }

    public void addNode(final E node) {
        this.nodes.add(node);
    }

    public void compute() throws CycleFoundException {
        // smazat pomocné proměnné
        this.visitedNodes.clear();
        this.expandedNodes.clear();
        this.result.clear();

        // zpracovat všechny uzly
        for (final E node : this.nodes) {
            this.visit(node);
        }
    }

    private void visit(final E node) throws CycleFoundException {
        if (this.visitedNodes.contains(node)) {
            if (this.expandedNodes.contains(node)) {
                // tento uzel je již zpracován
                return;
            }

            // na tento uzel již narážíme podruhé a stále není zpracován = cyklus
            throw new CycleFoundException();
        }

        // označit uzel jako navštívený
        this.visitedNodes.add(node);

        // projít všechny závislosti (pokud nějaké jsou)
        if (this.edges.containsKey(node)) {
            for (final E next : this.edges.get(node)) {
                this.visit(next);
            }
        }

        // označit uzel jako zpracovaný
        this.expandedNodes.add(node);

        // přidat uzel na konec výsledné posloupnosti
        // (jeho závislosti jsou již splněny)
        this.result.add(node);
    }

    public void addDependency(final E before, final E after) {
        this.addNode(before);
        this.addNode(after);

        if (!this.edges.containsKey(after)) {
            this.edges.put(after, new LinkedHashSet<E>());
        }

        this.edges.get(after).add(before);
    }
}
