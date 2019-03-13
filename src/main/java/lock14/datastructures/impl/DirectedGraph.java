package lock14.datastructures.impl;

import lock14.datastructures.Edge;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

// a simple implementation of an directed graph
public class DirectedGraph<V> extends AbstactGraph<V> {
    private Map<V, Set<V>> predecessors;

    public DirectedGraph() {
        super(true);
    }

    public DirectedGraph(Iterable<Edge<V>> edges) {
        super(edges, true);
    }

    @Override
    public Iterator<Edge<V>> edgeIterator() {
        return new DirectedEdgeIterator();
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
}
