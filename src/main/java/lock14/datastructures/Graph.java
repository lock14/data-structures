package lock14.datastructures;

import java.util.Set;

public interface Graph<V> {

    public void addEdge(V u, V v);

    public void addEdge(Edge<V> edge);

    public void addVertex(V v);

    public boolean contains(Object vertex);

    public boolean containsEdge(Object u, Object v);

    public boolean containsEdge(Edge<?> edge);

    public int degree(V v);

    public int degreeIn(V v);

    public int degreeOut(V v);

    public int edgeCount();

    public Graph<V> emptyGraph();

    public Set<Edge<V>> edges();

    public Set<Edge<V>> incidentEdges(V v);

    public Set<Edge<V>> incidentEdgesIn(V v);

    public Set<Edge<V>> incidentEdgesOut(V v);

    public Set<V> getAdjacent(Object v);

    public Set<V> getAdjacentIn(Object v);

    public Set<V> getAdjacentOut(Object v);

    public boolean isDirected();

    public void removeEdge(V u, V v);

    public void removeEdge(Edge<V> edge);

    public void removeVertex(V v);

    public int vertexCount();

    public Set<V> vertices();
}
