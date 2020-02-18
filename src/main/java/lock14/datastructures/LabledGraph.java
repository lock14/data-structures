package lock14.datastructures;

import lock14.datastructures.impl.SimpleGraph;
import lock14.datastructures.impl.SimpleLabledGraph;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * An interface to denote a graph whose edges are labeled. The getLabel does not need to be numeric,
 * it could be any type.
 *
 * @param <V> the vertex type
 * @param <L> the label type
 */
public interface LabledGraph<V, L> extends Graph<V> {

    void addEdge(V u, V v, L label);

    void addEdge(Edge<V> edge, L label);

    void addEdge(LabledEdge<V, L> edge);

    Optional<L> label(V u, V v);

    Optional<L> label(Edge<V> edge);

    Set<LabledEdge<V, L>> labledEdges();

    Set<LabledEdge<V, L>> incidentLabledEdges(V v);

    Set<LabledEdge<V, L>> incidentLabledEdgesOut(V v);

    Set<LabledEdge<V, L>> incidentLabledEdgesIn(V v);

    static <V, L> Builder<V, L> directed() {
        return new Builder<>(true);
    }

    static <V, L> Builder<V, L> undirected() {
        return new Builder<>(false);
    }

    final class Builder<V, L> {
        private boolean directed;
        private Set<LabledEdge<V, L>> edges;

        private Builder(boolean directed) {
            this.directed = directed;
            this.edges = new HashSet<>();
        }

        public <V1 extends V, L1 extends L> Builder<V1, L1> withEdge(V1 u, V1 v, L1 label) {
            return withEdge(LabledEdge.of(u, v, label));
        }

        public <V1 extends V, L1 extends L> Builder<V1, L1> withEdge(LabledEdge<V1, L1> edge) {
            edges.add((LabledEdge) edge);
            return (Builder<V1, L1>) this;
        }

        public <V1 extends V, L1 extends L> Builder<V1, L1> withEdges(Set<LabledEdge<V1, L1>> edges) {
            this.edges.addAll((Set) edges);
            return (Builder<V1, L1>) this;
        }

        public LabledGraph<V, L> build() {
            return new SimpleLabledGraph<V, L>(directed, edges);
        }
    }
}
