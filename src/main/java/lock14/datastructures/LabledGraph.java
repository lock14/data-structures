package lock14.datastructures;

import java.util.Optional;
import java.util.Set;

/**
 * An interface to denote a graph whose edges are labeled.
 * The getLabel does not need to be numeric, it could be any type.
 *
 * @param <V> the vertex type
 * @param <L> the label type
 */
public interface LabledGraph<V, L> extends Graph<V> {

    public void addEdge(V u, V v, L label);

    public void addEdge(Edge<V> edge, L label);

    public void addEdge(LabledEdge<V, L> edge);

    public Optional<L> label(V u, V v);

    public Optional<L> label(Edge<V> edge);

    public Set<LabledEdge<V, L>> labledEdges();

    public Set<LabledEdge<V, L>> incidentLabledEdges(V v);

    public Set<LabledEdge<V, L>> incidentLabledEdgesOut(V v);

    public Set<LabledEdge<V, L>> incidentLabledEdgesIn(V v);
}
