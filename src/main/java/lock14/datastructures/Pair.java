package lock14.datastructures;

import lock14.datastructures.impl.TwoTuple;

public interface Pair<T1, T2> {

    public T1 first();

    public T2 second();

    static <T1, T2> Pair<T1, T2> of(T1 t1, T2 t2) {
        return new TwoTuple<>(t1, t2);
    }
}
