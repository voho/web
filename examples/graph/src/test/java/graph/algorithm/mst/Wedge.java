package graph.algorithm.mst;

import cz.voho.grafo.WeightedEdge;

/**
 * Created by vojta on 08/09/15.
 */
class Wedge implements WeightedEdge<Integer> {
    private final int weight;

    Wedge(final int weight) {
        this.weight = weight;
    }

    @Override
    public Integer getEdgeWeight() {
        return weight;
    }
}
