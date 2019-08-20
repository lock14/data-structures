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
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

// TODO: switch to my implementation of Map and Set
// once they are finished
/**
 * Models a graph that does not allow more than one edge between any two vertices
 *
 * Can either be directed or undirected, and allows self loops (e.g. a getVertex has an edge to
 * itself)
 */
public class SimpleGraph<V> implements Graph<V> {
    private GraphMap<V> graph;
    private int edgeCount;
    private boolean directed;

    public SimpleGraph(boolean directed) {
        this(Collections.emptyList(), directed);
    }

    public SimpleGraph(Iterable<Edge<V>> edges, boolean directed) {
        if (directed) {
            graph = new DirectedGraphMap<>();
        } else {
            graph = new UndirectedGraphMap<>();
        }
        this.directed = directed;
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
    public Graph<V> emptyGraph() {
        return new SimpleGraph<>(this.directed);
    }

    @Override
    public Set<Edge<V>> incidentEdges(V v) {
        return incidentEdgesOut(v);
    }

    @Override
    public Set<Edge<V>> incidentEdgesIn(V v) {
        return new AbstractSet<Edge<V>>() {
            Set<V> adjacentIn = getAdjacentIn(v);

            @Override
            public Iterator<Edge<V>> iterator() {
                return new Iterator<Edge<V>>() {
                    Iterator<V> neighborsIn = adjacentIn.iterator();

                    @Override
                    public boolean hasNext() {
                        return neighborsIn.hasNext();
                    }

                    @Override
                    public Edge<V> next() {
                        return edge(neighborsIn.next(), v);
                    }
                };
            }

            @Override
            public boolean contains(Object o) {
                if (!(o instanceof Edge)) {
                    return false;
                }
                Edge<?> edge = (Edge<?>) o;
                return Objects.equals(v, edge.getV()) && adjacentIn.contains(edge.getU());
            }

            @Override
            public int size() {
                return adjacentIn.size();
            }
        };
    }

    @Override
    public Set<Edge<V>> incidentEdgesOut(V v) {
        return new AbstractSet<Edge<V>>() {
            Set<V> adjacentOut = getAdjacentOut(v);

            @Override
            public Iterator<Edge<V>> iterator() {
                return new Iterator<Edge<V>>() {
                    Iterator<V> neighborsOut = adjacentOut.iterator();

                    @Override
                    public boolean hasNext() {
                        return neighborsOut.hasNext();
                    }

                    @Override
                    public Edge<V> next() {
                        return edge(v, neighborsOut.next());
                    }
                };
            }

            @Override
            public boolean contains(Object o) {
                if (!(o instanceof Edge)) {
                    return false;
                }
                Edge<?> edge = (Edge<?>) o;
                return Objects.equals(v, edge.getU()) && adjacentOut.contains(edge.getV());
            }

            @Override
            public int size() {
                return adjacentOut.size();
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
    public boolean isDirected() {
        return directed;
    }

    @Override
    public void removeVertex(V v) {
        if (contains(v)) {
            // remove each edge this getVertex is the start of
            for (V u : getAdjacent(v)) {
                removeEdge(v, u);
            }
            graph.remove(v);
            // remove each edge this getVertex is the end of
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
        if (!(o instanceof SimpleGraph)) {
            return false;
        }
        SimpleGraph<?> that = (SimpleGraph<?>) o;
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

    private Iterator<Edge<V>> edgeIterator() {
        if (directed) {
            return new DirectedEdgeIterator();
        } else {
            return new UndirectedEdgeIterator();
        }
    }

    private Edge<V> edge(V u, V v) {
        if (directed) {
            return new DirectedEdge<>(u, v);
        } else {
            return new UndirectedEdge<>(u, v);
        }
    }

    private void validateVertex(Object v) {
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

    public class DirectedEdgeIterator implements Iterator<Edge<V>> {
        private Iterator<V> uIterator;
        private Iterator<V> vIterator;
        private V nextU;
        private V nextV;

        public DirectedEdgeIterator() {
            this.uIterator = vertices().iterator();
            findNextUAndV();
        }

        @Override
        public boolean hasNext() {
            return nextU != null && nextV != null;
        }

        @Override
        public Edge<V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Edge<V> edge = new DirectedEdge<>(nextU, nextV);
            findNextUAndV();
            return edge;
        }

        private void findNextUAndV() {
            boolean nextVFound = findNextV();
            while (uIterator.hasNext() && !nextVFound) {
                nextU = uIterator.next();
                vIterator = getAdjacent(nextU).iterator();
                nextVFound = findNextV();
            }
            if (!nextVFound) {
                // couldn't find a next v node, we are done
                nextU = null;
                nextV = null;
            }
        }

        private boolean findNextV() {
            boolean nextVFound = false;
            if (vIterator != null && vIterator.hasNext()) {
                nextV = vIterator.next();
                nextVFound = true;
            }
            return nextVFound;
        }
    }

    public static final class DirectedEdge<V> implements Edge<V> {
        private final V u;
        private final V v;

        public DirectedEdge(V u, V v) {
            this.u = u;
            this.v = v;
        }

        @Override
        public V getU() {
            return u;
        }

        @Override
        public V getV() {
            return v;
        }

        @Override
        public boolean isOrdered() {
            return true;
        }

        @Override
        public final int hashCode() {
            return Objects.hash(u, v);
        }

        @Override
        public final boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof DirectedEdge)) {
                return false;
            }
            DirectedEdge<?> other = (DirectedEdge<?>) o;
            return Objects.equals(this.u, other.u) && Objects.equals(this.v, other.v);
        }

        @Override
        public final String toString() {
            return "(" + u + ", " + v + ")";
        }
    }

    public class UndirectedEdgeIterator implements Iterator<Edge<V>> {
        private Set<V> visited;
        private Iterator<V> uIterator;
        private Iterator<V> vIterator;
        private V nextU;
        private V nextV;

        public UndirectedEdgeIterator() {
            this.uIterator = vertices().iterator();
            visited = new HashSet<>();
            findNextUAndV();
        }

        @Override
        public boolean hasNext() {
            return nextU != null && nextV != null;
        }

        @Override
        public Edge<V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Edge<V> edge = new UndirectedEdge<>(nextU, nextV);
            // move to next u and v nodes
            findNextUAndV();
            return edge;
        }

        private void findNextUAndV() {
            boolean nextVFound = findNextV();
            while (uIterator.hasNext() && !nextVFound) {
                if (nextU != null) {
                    visited.add(nextU);
                }
                nextU = uIterator.next();
                vIterator = getAdjacent(nextU).iterator();
                nextVFound = findNextV();
            }
            if (!nextVFound) {
                // couldn't find a next v node, we are done
                nextU = null;
                nextV = null;
            }
        }

        private boolean findNextV() {
            boolean nextVFound = false;
            while (vIterator != null && vIterator.hasNext() && !nextVFound) {
                V v = vIterator.next();
                if (!visited.contains(v)) {
                    nextV = v;
                    nextVFound = true;
                }
            }
            return nextVFound;
        }
    }

    public static final class UndirectedEdge<V> implements Edge<V> {
        private final V u;
        private final V v;

        public UndirectedEdge(V u, V v) {
            this.u = u;
            this.v = v;
        }

        @Override
        public V getU() {
            return u;
        }

        @Override
        public V getV() {
            return v;
        }

        @Override
        public boolean isOrdered() {
            return false;
        }

        @Override
        public final int hashCode() {
            return u.hashCode() + v.hashCode();
        }

        @Override
        public final boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof UndirectedEdge)) {
                return false;
            }
            UndirectedEdge<?> other = (UndirectedEdge<?>) o;
            return (Objects.equals(this.u, other.u) && Objects.equals(this.v, other.v))
                    || (Objects.equals(this.u, other.v) && Objects.equals(this.v, other.u));
        }

        @Override
        public final String toString() {
            return "{" + u + ", " + v + "}";
        }
    }
}
