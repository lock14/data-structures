package lock14.datastructures.impl;

import lock14.datastructures.Edge;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

// a simple implementation of an undirected graph
public class UndirectedGraph<V> extends AbstactGraph<V> {

    public UndirectedGraph() {
        super(false);
    }

    public UndirectedGraph(Iterable<Edge<V>> edges) {
        super(edges, false);
    }

    @Override
    public Iterator<Edge<V>> edgeIterator() {
        return new UndirectedEdgeIterator();
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
