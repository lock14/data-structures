package lock14.datastructures.impl;

import lock14.datastructures.Edge;
import lock14.datastructures.Graph;
import lock14.datastructures.Pair;

import java.util.AbstractSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

// TODO: switch to my implementation of Map and Set
//       once they are finished
public abstract class AbstactGraph<V> implements Graph<V> {
    private GraphMap<V> graph;
    private int edgeCount;

    public AbstactGraph(boolean directed) {
        this(Collections.emptyList(), directed);
    }

    public AbstactGraph(Iterable<Edge<V>> edges, boolean directed) {
        if (directed) {
            graph = new DirectedGraphMap<>();
        } else {
            graph = new UndirectedGraphMap<>();
        }
        edgeCount = 0;
        for (Edge<V> edge : edges) {
            addEdge(edge);
        }
    }

    @Override
    public void addEdge(V u, V v) {
        if (!containsEdge(u, v)) {
            addVertex(v);
            addVertex(u);
            graph.successors(u).add(v);
            graph.predecessors(v).add(u);
            edgeCount++;
            // self loops counted twice
            if (u.equals(v)) {
                edgeCount++;
            }
        }
    }

    @Override
    public void addEdge(Edge<V> edge) {
        addEdge(edge.getU(), edge.getV());
    }

    @Override
    public void addVertex(V v) {
        if (!contains(v)) {
            graph.add(v);
        }
    }

    @Override
    public boolean contains(Object v) {
        return graph.contains(v);
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
        return Collections.unmodifiableSet(graph.successors(v));
    }

    @Override
    public Set<V> getAdjacentIn(Object v) {
        validateVertex(v);
        return Collections.unmodifiableSet(graph.predecessors(v));
    }

    @Override
    public Set<V> getAdjacentOut(Object v) {
        return getAdjacent(v);
    }

    @Override
    public void removeVertex(V v) {
        if (contains(v)) {
            // remove each edge this vertex is the start of
            for (V u : getAdjacent(v)) {
                removeEdge(v, u);
            }
            graph.remove(v);
            // remove each edge this vertex is the end of
            for (V u : vertices()) {
                removeEdge(u, v);
            }
        }
    }

    public void removeEdge(V u, V v) {
        if (containsEdge(u, v)) {
            graph.successors(u).remove(v);
            graph.predecessors(v).remove(u);
            edgeCount--;
            // self loops counted twice
            if (Objects.equals(u, v)) {
                edgeCount--;
            }
        }
    }

    public void removeEdge(Edge<V> edge) {
        removeEdge(edge.getU(), edge.getV());
    }

    @Override
    public int vertexCount() {
        return vertices().size();
    }

    @Override
    public Set<V> vertices() {
        return Collections.unmodifiableSet(graph.vertices());
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
               Objects.equals(graph, that.graph);
    }

    @Override
    public int hashCode() {
        return Objects.hash(graph, edgeCount);
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

    private interface GraphMap<V> {
        void add(V v);
        boolean contains(Object v);
        Set<V> predecessors(Object v);
        void remove(V v);
        Set<V> successors(Object v);
        Set<V> vertices();
    }

    private static final class UndirectedGraphMap<V> implements GraphMap<V> {
        private Map<V, Set<V>> map;

        UndirectedGraphMap() {
            map = new HashMap<>();
        }

        @Override
        public void add(V v) {
            map.put(v, new HashSet<>());
        }

        @Override
        public boolean contains(Object v) {
            return map.containsKey(v);
        }

        @Override
        public void remove(V v) {
            map.remove(v);
        }

        @Override
        public Set<V> predecessors(Object v) {
            return map.get(v);
        }

        @Override
        public Set<V> successors(Object v) {
            return map.get(v);
        }

        @Override
        public Set<V> vertices() {
            return map.keySet();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof UndirectedGraphMap)) {
                return false;
            }
            UndirectedGraphMap<?> that = (UndirectedGraphMap<?>) o;
            return Objects.equals(map, that.map);
        }

        @Override
        public int hashCode() {
            return Objects.hash(map);
        }
    }

    private static final class DirectedGraphMap<V> implements GraphMap<V> {
        private Map<V, Pair<Set<V>, Set<V>>> map;

        public DirectedGraphMap() {
            map = new HashMap<>();
        }

        @Override
        public void add(V v) {
            map.put(v, new TwoTuple<>(new HashSet<>(), new HashSet<>()));
        }

        @Override
        public boolean contains(Object v) {
            return map.containsKey(v);
        }

        @Override
        public Set<V> predecessors(Object v) {
            return map.get(v).second();
        }

        @Override
        public void remove(V v) {
            map.remove(v);
        }

        @Override
        public Set<V> successors(Object v) {
            return map.get(v).first();
        }

        @Override
        public Set<V> vertices() {
            return map.keySet();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof DirectedGraphMap)) {
                return false;
            }
            DirectedGraphMap<?> that = (DirectedGraphMap<?>) o;
            return Objects.equals(map, that.map);
        }

        @Override
        public int hashCode() {
            return Objects.hash(map);
        }
    }
}
