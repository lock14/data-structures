package lock14.datastructures.impl;

import java.util.Objects;
import lock14.datastructures.Pair;

public abstract class AbstractPair<T1, T2> implements Pair<T1, T2> {

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pair)) {
            return false;
        }
        Pair<?, ?> other = (Pair<?, ?>) o;
        return Objects.equals(this.first(), other.first())
                && Objects.equals(this.second(), other.second());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(first(), second());
    }

    @Override
    public String toString() {
        return "(" + first() + ", " + second() + ")";
    }
}
