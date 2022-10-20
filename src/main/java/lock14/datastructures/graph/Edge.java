package lock14.datastructures.graph;

import java.util.Objects;

public interface Edge<V> {
    V getU();

    V getV();

    boolean isOrdered();

    static <V> Edge<V> of(V u, V v) {
        return new SimpleEdge<>(u, v);
    }

    final class SimpleEdge<V> implements Edge<V> {
        private V u;
        private V v;

        public SimpleEdge(V u, V v) {
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
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof SimpleEdge)) {
                return false;
            }
            SimpleEdge<?> that = (SimpleEdge<?>) o;
            return Objects.equals(u, that.u) && Objects.equals(v, that.v);
        }

        @Override
        public int hashCode() {
            return Objects.hash(u, v);
        }

        @Override
        public String toString() {
            return "(" + u + ", " + v + ")";
        }
    }
}
