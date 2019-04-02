package group;

import java.util.Objects;

public class RingInteger implements Ring<Integer>, Comparable<Integer> {

    private final Integer a;

    public RingInteger(Integer a) {
        this.a = Objects.requireNonNull(a);
    }

    @Override
    public Integer additiveIdentity() {
        return 0;
    }

    @Override
    public Integer additiveInverse(Integer x) {
        return -x;
    }

    @Override
    public Integer multiplicativeIdentity() {
        return 1;
    }

    @Override
    public Integer minus(Integer b) {
        return a - b;
    }

    @Override
    public Integer plus(Integer b) {
        return a + b;
    }

    @Override
    public Integer times(Integer b) {
        return a * b;
    }

    @Override
    public boolean equals(Object o) {
        return a.equals(o);
    }

    @Override
    public int hashCode() {
        return a.hashCode();
    }

    @Override
    public int compareTo(Integer b) {
        return Objects.compare(a, b, Integer::compareTo);
    }
}
