package lock14.datastructures;

import lock14.datastructures.impl.AbstractPair;

public interface Pair<T1, T2> {

    T1 first();

    T2 second();

    static <T1, T2> Pair<T1, T2> of(T1 t1, T2 t2) {
        return new AbstractPair<T1, T2>() {
            @Override
            public T1 first() {
                return t1;
            }

            @Override
            public T2 second() {
                return t2;
            }
        };
    }
}
