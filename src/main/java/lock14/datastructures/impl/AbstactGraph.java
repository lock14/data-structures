package lock14.datastructures.impl;

import lock14.datastructures.Edge;
import lock14.datastructures.Graph;

import java.util.AbstractSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public abstract class AbstactGraph<V> implements Graph<V> {
    protected Map<V, Set<V>> graph;
    protected int edgeCount;

    public AbstactGraph() {
        graph = new HashMap<>();
        edgeCount = 0;
    }

    public AbstactGraph(Iterable<Edge<V>> edges) {
        this();
        for (Edge<V> edge : edges) {
            addEdge(edge);
        }
    }

    @Override
    public void addVertex(V vertex) {
        if (!contains(vertex)) {
            graph.put(vertex, new HashSet<>());
        }
    }

    @Override
    public void addEdge(V u, V v) {
        addVertex(v);
        addVertex(u);
        graph.get(u).add(v);
        edgeCount++;
    }

    @Override
    public void addEdge(Edge<V> edge) {
        addEdge(edge.getU(), edge.getV());
    }

    @Override
    public boolean contains(Object vertex) {
        return graph.containsKey(vertex);
    }

    @Override
    public boolean containsEdge(Object u, Object v) {
        return contains(u) && contains(v) && getAdjacent(u).contains(v);
    }

    @Override
    public boolean containsEdge(Edge<?> edge) {
        return containsEdge(edge.getU(), edge.getV());
    }

    @Override
    public int edgeCount() {
        return edgeCount;
    }

    @Override
    public Set<Edge<V>> edges() {
        return new AbstractSet<Edge<V>>() {

            @Override
            public Iterator<Edge<V>> iterator() {
                return edgeIterator();
            }

            @Override
            public boolean contains(Object o) {
                if (!(o instanceof Edge)) {
                    return false;
                }
                Edge<?> edge = (Edge<?>) o;
                return containsEdge(edge);
            }

            @Override
            public int size() {
                return edgeCount();
            }
        };
    }

    @Override
    public Set<V> getAdjacent(Object vertex) {
        if (!contains(vertex)) {
            throw new IllegalArgumentException("Vertex " + vertex + " is not in this graph!");
        }
        return Collections.unmodifiableSet(graph.get(vertex));
    }

    @Override
    public int vertexCount() {
        return graph.keySet().size();
    }

    @Override
    public Set<V> vertices() {
        return Collections.unmodifiableSet(graph.keySet());
    }

    @Override
    public final int hashCode() {
        return graph.hashCode();
    }

    @Override
    public final boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AbstactGraph)) {
            return false;
        }
        AbstactGraph<?> other = (AbstactGraph<?>) o;
        return Objects.equals(this.graph, other.graph);
    }

    @Override
    public final String toString() {
        return "V: " + vertices() + ", " + "E: " + edges();
    }

    public abstract Iterator<Edge<V>> edgeIterator();
}
