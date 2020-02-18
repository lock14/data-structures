package lock14.datastructures;

import java.util.Objects;

public interface LabledEdge<V, L> extends Edge<V> {
    L getLabel();

    static <V, L> LabledEdge<V, L> of(V u, V v, L label) {
        return new SimpleLabledEdge<>(u, v, label);
    }

    final class SimpleLabledEdge<V, L> implements LabledEdge<V, L> {
        private V u;
        private V v;
        private L label;

        public SimpleLabledEdge(V u, V v, L label) {
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
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof SimpleLabledEdge)) {
                return false;
            }
            SimpleLabledEdge<?, ?> that = (SimpleLabledEdge<?, ?>) o;
            return Objects.equals(u, that.u) &&
                   Objects.equals(v, that.v) &&
                   Objects.equals(label, that.label);
        }

        @Override
        public int hashCode() {
            return Objects.hash(u, v, label);
        }

        @Override
        public String toString() {
            return "(" + u + ", " + v + "):" + label;
        }
    }
}
