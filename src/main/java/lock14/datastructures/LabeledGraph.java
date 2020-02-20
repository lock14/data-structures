package lock14.datastructures;

import lock14.datastructures.impl.SimpleLabeledGraph;
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
public interface LabeledGraph<V, L> extends Graph<V> {

    void addEdge(V u, V v, L label);

    void addEdge(Edge<V> edge, L label);

    void addEdge(LabeledEdge<V, L> edge);

    Optional<L> label(V u, V v);

    Optional<L> label(Edge<V> edge);

    Set<LabeledEdge<V, L>> labeledEdges();

    Set<LabeledEdge<V, L>> incidentLabeledEdges(V v);

    Set<LabeledEdge<V, L>> incidentLabeledEdgesOut(V v);

    Set<LabeledEdge<V, L>> incidentLabeledEdgesIn(V v);

    static <V, L> Builder<V, L> directed() {
        return new Builder<>(true);
    }

    static <V, L> Builder<V, L> undirected() {
        return new Builder<>(false);
    }

    final class Builder<V, L> {
        private boolean directed;
        private Set<LabeledEdge<V, L>> edges;

        private Builder(boolean directed) {
            this.directed = directed;
            this.edges = new HashSet<>();
        }

        public <V1 extends V, L1 extends L> Builder<V1, L1> withEdge(V1 u, V1 v, L1 label) {
            return withEdge(LabeledEdge.of(u, v, label));
        }

        public <V1 extends V, L1 extends L> Builder<V1, L1> withEdge(LabeledEdge<V1, L1> edge) {
            edges.add((LabeledEdge) edge);
            return (Builder<V1, L1>) this;
        }

        public <V1 extends V, L1 extends L> Builder<V1, L1> withEdges(Set<LabeledEdge<V1, L1>> edges) {
            this.edges.addAll((Set) edges);
            return (Builder<V1, L1>) this;
        }

        public LabeledGraph<V, L> build() {
            return new SimpleLabeledGraph<V, L>(directed, edges);
        }
    }
}
