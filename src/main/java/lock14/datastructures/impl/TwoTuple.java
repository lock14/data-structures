package lock14.datastructures.impl;

import lock14.datastructures.Pair;

public class TwoTuple<T1, T2> implements Pair<T1, T2> {
    private T1 fst;
    private T2 snd;
    
    public TwoTuple(T1 first, T2 second) {
        if (first == null || second == null) {
            throw new IllegalArgumentException();
        }
        fst = first;
        snd = second;
    }
    
    public T1 first() {
        return fst;
    }
    
    public T2 second() {
        return snd;
    }
    
    public boolean equals(Object o) {
        if (o instanceof TwoTuple) {
            TwoTuple<?, ?> other = (TwoTuple<?, ?>) o;
            return this.fst.equals(other.fst) && this.snd.equals(other.snd);
        }
        return false;
    }
    
    public int hashCode() {
        int hash = 1;
        hash = 31 * hash + fst.hashCode();
        hash = 31 * hash + snd.hashCode();
        return hash;
    }
    
    public String toString() {
        return new StringBuilder("(")
                   .append(fst)
                   .append(", ")
                   .append(snd)
                   .append(")")
                   .toString();
    }
}
