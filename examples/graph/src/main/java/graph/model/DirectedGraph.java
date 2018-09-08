package graph.model;

import com.google.common.base.Objects;

public interface DirectedGraph<NODE> extends Graph<NODE, DirectedGraph.Edge<NODE>> {
    class Edge<NODE> {
        private final NODE first;
        private final NODE second;

        Edge(final NODE first, final NODE second) {
            this.first = first;
            this.second = second;
        }

        public NODE first() {
            return first;
        }

        public NODE second() {
            return second;
        }

        @Override
        public String toString() {
            return String.format("(%s,%s)", first, second);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (!(o instanceof Edge)) return false;
            final Edge<?> edge = (Edge<?>) o;
            return Objects.equal(first, edge.first) && Objects.equal(second, edge.second);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(first, second);
        }
    }
}
