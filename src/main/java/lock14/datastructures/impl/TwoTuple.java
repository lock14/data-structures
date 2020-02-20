package lock14.datastructures.impl;

import java.util.Objects;

public class TwoTuple<T1, T2> extends AbstractPair<T1, T2> {
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
}
