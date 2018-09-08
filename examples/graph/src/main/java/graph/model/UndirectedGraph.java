package graph.model;

import com.google.common.base.Objects;

public interface UndirectedGraph<NODE> extends Graph<NODE, UndirectedGraph.Edge<NODE>> {
    class Edge<NODE> {
        private final NODE either;
        private final NODE another;

        Edge(final NODE either, final NODE another) {
            this.either = either;
            this.another = another;
        }

        public NODE either() {
            return either;
        }

        public NODE another() {
            return another;
        }

        @Override
        public String toString() {
            return String.format("{%s,%s}", either, another);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Edge)) return false;
            Edge<?> edge = (Edge<?>) o;
            boolean p1 = Objects.equal(either, edge.either) &&
                    Objects.equal(another, edge.another);
            boolean p2 = Objects.equal(either, edge.another) &&
                    Objects.equal(another, edge.either);
            return p1 || p2;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(either, another);
        }
    }
}
