package lock14.datastructures;

import lock14.datastructures.impl.SimpleGraph;
import java.util.HashSet;
import java.util.Set;

public interface Graph<V> {

    void addEdge(V u, V v);

    void addEdge(Edge<V> edge);

    void addVertex(V v);

    boolean contains(Object vertex);

    boolean containsEdge(Object u, Object v);

    boolean containsEdge(Edge<?> edge);

    int degree(V v);

    int degreeIn(V v);

    int degreeOut(V v);

    int edgeCount();

    Graph<V> emptyGraph();

    Set<Edge<V>> edges();

    Set<Edge<V>> incidentEdges(V v);

    Set<Edge<V>> incidentEdgesIn(V v);

    Set<Edge<V>> incidentEdgesOut(V v);

    Set<V> getAdjacent(Object v);

    Set<V> getAdjacentIn(Object v);

    Set<V> getAdjacentOut(Object v);

    boolean isDirected();

    void removeEdge(V u, V v);

    void removeEdge(Edge<V> edge);

    void removeVertex(V v);

    int vertexCount();

    Set<V> vertices();

    static <V> Builder<V> directed() {
        return new Builder<>(true);
    }

    static <V> Builder<V> undirected() {
        return new Builder<>(false);
    }

    final class Builder<V> {
        private boolean directed;
        private Set<Edge<V>> edges;

        private Builder(boolean directed) {
            this.directed = directed;
            this.edges = new HashSet<>();
        }

        public <V1 extends V> Builder<V1> withEdge(V1 u, V1 v) {
            return withEdge(Edge.of(u, v));
        }

        public <V1 extends V> Builder<V1> withEdge(Edge<V1> edge) {
            edges.add((Edge) edge);
            return (Builder<V1>) this;
        }

        public <V1 extends V> Builder<V1> withEdges(Set<Edge<V1>> edges) {
            this.edges.addAll((Set) edges);
            return (Builder<V1>) this;
        }

        public Graph<V> build() {
            return new SimpleGraph<>(directed, edges);
        }
    }
}
