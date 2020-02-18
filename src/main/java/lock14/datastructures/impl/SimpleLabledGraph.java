package lock14.datastructures.impl;

import lock14.datastructures.Edge;
import lock14.datastructures.Graph;
import lock14.datastructures.LabledEdge;
import lock14.datastructures.LabledGraph;

import java.util.AbstractSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Models a graph that does not allow more than one edge between any two vertices, and each edge has
 * a corresponding getLabel (not necessarily numeric).
 * <p>
 * Can either be directed or undirected, and allows self loops (e.g. a getVertex has an edge to
 * itself)
 *
 * @param <V> the getVertex type
 * @param <L> the getLabel type
 */
public class SimpleLabledGraph<V, L> implements LabledGraph<V, L> {
    private final Map<V, NodeData<V, L>> graph;
    private final boolean directed;
    private int edgeCount;

    public SimpleLabledGraph(boolean directed) {
        this(directed, Collections.emptyList());
    }

    public SimpleLabledGraph(boolean directed, Iterable<LabledEdge<V, L>> edges) {
        this.graph = new HashMap<>();
        this.directed = directed;
        edgeCount = 0;
        for (LabledEdge<V, L> edge : edges) {
            addEdge(edge);
        }
    }

    @Override
    public void addEdge(V u, V v, L label) {
        addVertex(u);
        addVertex(v);
        graph.get(u).addSuccessor(v, label);
        graph.get(v).addPredecessor(u, label);
        edgeCount++;
    }

    @Override
    public void addEdge(Edge<V> edge, L label) {
        addEdge(edge.getU(), edge.getV(), label);
    }

    @Override
    public void addEdge(LabledEdge<V, L> edge) {
        addEdge(edge.getU(), edge.getV(), edge.getLabel());
    }

    @Override
    public void addEdge(V u, V v) {
        addEdge(u, v, null);
    }

    @Override
    public void addEdge(Edge<V> edge) {
        addEdge(edge.getU(), edge.getV());
    }

    @Override
    public void addVertex(V v) {
        if (!contains(v)) {
            graph.put(v, nodeData());
        }
    }

    @Override
    public boolean contains(Object vertex) {
        return graph.containsKey(vertex);
    }

    @Override
    public boolean containsEdge(Object u, Object v) {
        NodeData<V, L> nodeData = graph.get(u);
        return nodeData != null && nodeData.successors().contains(v);
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
    @SuppressWarnings("unchecked")
    public Set<Edge<V>> edges() {
        return (Set) labledEdges();
    }

    @Override
    public Graph<V> emptyGraph() {
        return new SimpleLabledGraph<>(directed);
    }

    @Override
    public Set<V> getAdjacent(Object v) {
        return getAdjacentOut(v);
    }

    @Override
    public Set<V> getAdjacentIn(Object v) {
        validateVertex(v);
        return Collections.unmodifiableSet(graph.get(v).predecessors());
    }

    @Override
    public Set<V> getAdjacentOut(Object v) {
        validateVertex(v);
        return Collections.unmodifiableSet(graph.get(v).successors());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<Edge<V>> incidentEdges(V v) {
        return (Set) incidentLabledEdges(v);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<Edge<V>> incidentEdgesIn(V v) {
        return (Set) incidentLabledEdgesIn(v);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<Edge<V>> incidentEdgesOut(V v) {
        return (Set) incidentLabledEdgesOut(v);
    }

    @Override
    public Set<LabledEdge<V, L>> incidentLabledEdges(V v) {
        return incidentLabledEdgesOut(v);
    }

    @Override
    public Set<LabledEdge<V, L>> incidentLabledEdgesIn(V v) {
        return new AbstractSet<LabledEdge<V, L>>() {
            NodeData<V, L> nodeData = graph.get(v);

            @Override
            public Iterator<LabledEdge<V, L>> iterator() {
                return new Iterator<LabledEdge<V, L>>() {
                    Iterator<V> predecessors = nodeData.predecessors().iterator();

                    @Override
                    public boolean hasNext() {
                        return predecessors.hasNext();
                    }

                    @Override
                    public LabledEdge<V, L> next() {
                        V predecessor = predecessors.next();
                        return edge(predecessor, v, graph.get(predecessor).label(v));
                    }
                };
            }

            @Override
            public boolean contains(Object o) {
                if (!(o instanceof Edge)) {
                    return false;
                }
                Edge<?> edge = (Edge<?>) o;
                return Objects.equals(v, edge.getV()) && nodeData.predecessors().contains(edge.getV());
            }

            @Override
            public int size() {
                return nodeData.predecessors().size();
            }

        };
    }

    @Override
    public Set<LabledEdge<V, L>> incidentLabledEdgesOut(V v) {
        return new AbstractSet<LabledEdge<V, L>>() {
            NodeData<V, L> nodeData = graph.get(v);

            @Override
            public Iterator<LabledEdge<V, L>> iterator() {
                return new Iterator<LabledEdge<V, L>>() {
                    Iterator<V> sucessors = nodeData.successors().iterator();

                    @Override
                    public boolean hasNext() {
                        return sucessors.hasNext();
                    }

                    @Override
                    public LabledEdge<V, L> next() {
                        V successor = sucessors.next();
                        return edge(v, successor, nodeData.label(successor));
                    }
                };
            }

            @Override
            public boolean contains(Object o) {
                if (!(o instanceof Edge)) {
                    return false;
                }
                Edge<?> edge = (Edge<?>) o;
                return Objects.equals(v, edge.getU()) && nodeData.successors().contains(edge.getV());
            }

            @Override
            public int size() {
                return nodeData.successors().size();
            }

        };
    }

    @Override
    public boolean isDirected() {
        return directed;
    }

    @Override
    public void removeEdge(V u, V v) {
        validateVertex(u);
        validateVertex(v);
        graph.get(u).removeSuccessor(v);
        graph.get(v).removePredecessor(v);
        edgeCount--;
    }

    @Override
    public void removeEdge(Edge<V> edge) {
        removeEdge(edge.getU(), edge.getV());
    }

    @Override
    public void removeVertex(V v) {
        validateVertex(v);
        NodeData<V, L> nodeData = graph.remove(v);
        if (nodeData != null) {
            for (V successor : nodeData.successors()) {
                graph.get(successor).removePredecessor(v);
                edgeCount--;
            }
            // already removed all predecessors if undirected
            if (directed) {
                for (V predecessor : nodeData.predecessors()) {
                    graph.get(predecessor).removeSuccessor(v);
                    edgeCount--;
                }
            }
        }
    }

    @Override
    public Optional<L> label(V u, V v) {
        validateVertex(u);
        validateVertex(v);
        return Optional.ofNullable(graph.get(u).label(v));
    }

    @Override
    public Optional<L> label(Edge<V> edge) {
        return label(edge.getU(), edge.getV());
    }

    @Override
    public Set<LabledEdge<V, L>> labledEdges() {
        return new AbstractSet<LabledEdge<V, L>>() {

            @Override
            public Iterator<LabledEdge<V, L>> iterator() {
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
    public int vertexCount() {
        return vertices().size();
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
        if (!(o instanceof SimpleLabledGraph)) {
            return false;
        }
        SimpleLabledGraph<?, ?> other = (SimpleLabledGraph<?, ?>) o;
        return this.directed == other.directed &&
               this.edgeCount == other.edgeCount &&
               Objects.equals(this.graph, other.graph);
    }

    @Override
    public int hashCode() {
        return Objects.hash(graph, directed, edgeCount);
    }

    @Override
    public String toString() {
        return "V: " + vertices() + ", " + "E: " + edges();
    }

    private LabledEdge<V, L> edge(V v, V next, L label) {
        return directed ? new DirectedLabeledEdge<>(v, next, label) : new UndirectedLabeledEdge<>(v, next, label);
    }

    private Iterator<LabledEdge<V, L>> edgeIterator() {
        return directed ? new DirectedEdgeIterator() : new UndirectedEdgeIterator();
    }

    private NodeData<V, L> nodeData() {
        if (isDirected()) {
            return new DirectedNodeData<>();
        } else {
            return new UndirectedNodeData<>();
        }
    }

    private void validateVertex(Object v) {
        if (!contains(v)) {
            throw new IllegalArgumentException("Vertex " + v + " is not in this graph!");
        }
    }

    private static interface NodeData<V, L> {

        public void addPredecessor(V v, L label);

        public void addSuccessor(V v, L label);

        public Set<V> predecessors();

        public void removePredecessor(V v);

        public void removeSuccessor(V v);

        public Set<V> successors();

        public L label(V v);
    }

    private static class UndirectedNodeData<V, L> implements NodeData<V, L> {
        Map<V, L> adjacentNodeValues;

        UndirectedNodeData() {
            adjacentNodeValues = new HashMap<>();
        }

        @Override
        public void addPredecessor(V v, L label) {
            addSuccessor(v, label);
        }

        @Override
        public void addSuccessor(V v, L label) {
            adjacentNodeValues.put(v, label);
        }

        @Override
        public Set<V> predecessors() {
            return successors();
        }

        @Override
        public void removePredecessor(V v) {
            removeSuccessor(v);
        }

        @Override
        public void removeSuccessor(V v) {
            adjacentNodeValues.remove(v);
        }

        @Override
        public Set<V> successors() {
            return adjacentNodeValues.keySet();
        }

        @Override
        public L label(V v) {
            return adjacentNodeValues.get(v);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof UndirectedNodeData)) {
                return false;
            }
            UndirectedNodeData<?, ?> other = (UndirectedNodeData<?, ?>) o;
            return Objects.equals(this.adjacentNodeValues, other.adjacentNodeValues);
        }

        @Override
        public int hashCode() {
            return Objects.hash(adjacentNodeValues);
        }
    }

    private static class DirectedNodeData<V, L> implements NodeData<V, L> {
        private Map<V, L> successorValues;
        private Set<V> predecessors;

        public DirectedNodeData() {
            successorValues = new HashMap<>();
            predecessors = new HashSet<>();
        }

        @Override
        public void addPredecessor(V v, L label) {
            // only successors store a getLabel
            predecessors.add(v);
        }

        @Override
        public void addSuccessor(V v, L label) {
            successorValues.put(v, label);
        }

        @Override
        public Set<V> predecessors() {
            return predecessors;
        }

        @Override
        public void removePredecessor(V v) {
            predecessors.remove(v);
        }

        @Override
        public void removeSuccessor(V v) {
            successorValues.remove(v);
        }

        @Override
        public Set<V> successors() {
            return successorValues.keySet();
        }

        @Override
        public L label(V v) {
            return successorValues.get(v);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof DirectedNodeData)) {
                return false;
            }
            DirectedNodeData<?, ?> that = (DirectedNodeData<?, ?>) o;
            return Objects.equals(successorValues, that.successorValues) &&
                   Objects.equals(predecessors, that.predecessors);
        }

        @Override
        public int hashCode() {
            return Objects.hash(successorValues, predecessors);
        }
    }

    public class DirectedEdgeIterator implements Iterator<LabledEdge<V, L>> {
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
        public LabledEdge<V, L> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            LabledEdge<V, L> edge = new DirectedLabeledEdge<>(nextU, nextV, label(nextU, nextV).get());
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

    public static final class DirectedLabeledEdge<V, L> implements LabledEdge<V, L> {
        private final V u;
        private final V v;
        private final L label;

        public DirectedLabeledEdge(V u, V v, L label) {
            this.u = u;
            this.v = v;
            this.label = label;
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
        public L getLabel() {
            return label;
        }

        @Override
        public boolean isOrdered() {
            return true;
        }

        @Override
        public final int hashCode() {
            return Objects.hash(u, v, label);
        }

        @Override
        public final boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof SimpleLabledGraph.DirectedLabeledEdge)) {
                return false;
            }
            DirectedLabeledEdge<?, ?> other = (DirectedLabeledEdge<?, ?>) o;
            return Objects.equals(this.u, other.u) && Objects.equals(this.v, other.v)
                   && Objects.equals(this.label, other.label);
        }

        @Override
        public final String toString() {
            String labelString = label.toString();
            String edgeString = "(" + u + ", " + v + ")";
            if (labelString != null && !labelString.isEmpty()) {
                edgeString += ":" + labelString;
            }
            return edgeString;
        }
    }

    public class UndirectedEdgeIterator implements Iterator<LabledEdge<V, L>> {
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
        public LabledEdge<V, L> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            LabledEdge<V, L> edge = new UndirectedLabeledEdge<>(nextU, nextV, label(nextU, nextV).get());
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

    public static final class UndirectedLabeledEdge<V, L> implements LabledEdge<V, L> {
        private final V u;
        private final V v;
        private final L label;

        public UndirectedLabeledEdge(V u, V v, L label) {
            this.u = u;
            this.v = v;
            this.label = label;
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
        public L getLabel() {
            return label;
        }

        @Override
        public boolean isOrdered() {
            return false;
        }

        @Override
        public final int hashCode() {
            return Objects.hash(u.hashCode() + v.hashCode(), label);
        }

        @Override
        public final boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof UndirectedLabeledEdge)) {
                return false;
            }
            UndirectedLabeledEdge<?, ?> other = (UndirectedLabeledEdge<?, ?>) o;
            return ((Objects.equals(this.u, other.u) && Objects.equals(this.v, other.v))
                    || (Objects.equals(this.u, other.v) && Objects.equals(this.v, other.u)))
                   && Objects.equals(this.label, other.label);
        }

        @Override
        public final String toString() {
            String labelString = label.toString();
            String edgeString = "{" + u + ", " + v + "}";
            if (labelString != null && !labelString.isEmpty()) {
                edgeString += ":" + labelString;
            }
            return edgeString;
        }
    }
}
