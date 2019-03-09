package lock14.datastructures;

import java.util.Set;

public interface Graph<V> {
    public void addVertex(V v);

    public void addEdge(V u, V v);

    public void addEdge(Edge<V> edge);

    public boolean contains(Object vertex);

    public boolean containsEdge(Object u, Object v);

    public boolean containsEdge(Edge<?> edge);

    public int edgeCount();

    public Set<Edge<V>> edges();

    public Set<V> getAdjacent(Object v);

    public int vertexCount();

    public Set<V> vertices();
}
