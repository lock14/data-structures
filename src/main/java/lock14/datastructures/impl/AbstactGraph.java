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
    private Map<V, Set<V>> graph;
    private Map<V, Set<V>> predecessors;
    private int edgeCount;

    public AbstactGraph(boolean directed) {
        this(Collections.emptyList(), directed);
    }

    public AbstactGraph(Iterable<Edge<V>> edges, boolean directed) {
        graph = new HashMap<>();
        predecessors = graph;
        if (directed) {
            predecessors = new HashMap<>();
        }
        edgeCount = 0;
        for (Edge<V> edge : edges) {
            addEdge(edge);
        }
    }

    @Override
    public void addEdge(V u, V v) {
        addVertex(v);
        addVertex(u);
        graph.get(u).add(v);
        predecessors.get(v).add(u);
        edgeCount++;
        // self loops counted twice
        if (u.equals(v)) {
            edgeCount++;
        }
    }

    @Override
    public void addEdge(Edge<V> edge) {
        addEdge(edge.getU(), edge.getV());
    }

    @Override
    public void addVertex(V v) {
        if (!contains(v)) {
            graph.put(v, new HashSet<>());
        }
        if (!predecessors.containsKey(v)) {
            predecessors.put(v, new HashSet<>());
        }
    }

    @Override
    public boolean contains(Object v) {
        return graph.containsKey(v);
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
    public int degree(V v) {
        return getAdjacent(v).size();
    }

    @Override
    public int degreeIn(V v) {
        return getAdjacentIn(v).size();
    }

    @Override
    public int degreeOut(V v) {
        return getAdjacentOut(v).size();
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
    public Set<V> getAdjacent(Object v) {
        validateVertex(v);
        return Collections.unmodifiableSet(graph.get(v));
    }

    @Override
    public Set<V> getAdjacentIn(Object v) {
        validateVertex(v);
        return Collections.unmodifiableSet(predecessors.get(v));
    }

    @Override
    public Set<V> getAdjacentOut(Object v) {
        return getAdjacent(v);
    }

    @Override
    public void removeVertex(V v) {
        if (contains(v)) {
            // remove from graph
            graph.remove(v);
            predecessors.remove(v);
            // remove from each other vertex's adjacency list
            for (V u : vertices()) {
                graph.get(u).remove(v);
                predecessors.get(u).remove(v);
            }
        }
    }

    public void removeEdge(V u, V v) {
        // removing both vertices will suffice
        validateVertex(u);
        validateVertex(v);
        graph.get(u).remove(v);
        predecessors.get(v).remove(u);
        edgeCount--;
        // self loops counted twice
        if (Objects.equals(u, v)) {
            edgeCount--;
        }
    }

    public void removeEdge(Edge<V> edge) {
        removeEdge(edge.getU(), edge.getV());
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstactGraph)) {
            return false;
        }
        AbstactGraph<?> that = (AbstactGraph<?>) o;
        return edgeCount == that.edgeCount &&
               Objects.equals(graph, that.graph) &&
               Objects.equals(predecessors, that.predecessors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(graph, predecessors, edgeCount);
    }

    @Override
    public final String toString() {
        return "V: " + vertices() + ", " + "E: " + edges();
    }

    public abstract Iterator<Edge<V>> edgeIterator();

    protected void validateVertex(Object v) {
        if (!contains(v)) {
            throw new IllegalArgumentException("Vertex " + v + " is not in this graph!");
        }
    }
}
