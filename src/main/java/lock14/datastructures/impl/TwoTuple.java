package lock14.datastructures.impl;

import lock14.datastructures.Pair;
import java.util.Objects;

public class TwoTuple<T1, T2> implements Pair<T1, T2> {
    private T1 fst;
    private T2 snd;

    public TwoTuple(T1 first, T2 second) {
        fst = Objects.requireNonNull(first);
        snd = Objects.requireNonNull(second);
    }

    public T1 first() {
        return fst;
    }

    public T2 second() {
        return snd;
    }

    public boolean equals(Object o) {
        if (o instanceof TwoTuple) {
            Pair<?, ?> other = (Pair<?, ?>) o;
            return Objects.equals(this.first(), other.first())
                    && Objects.equals(this.second(), other.second());
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(fst, snd);
    }

    public String toString() {
        return "(" + fst + ", " + snd + ")";
    }
}
