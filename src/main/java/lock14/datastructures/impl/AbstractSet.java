package lock14.datastructures.impl;

import java.util.Iterator;
import lock14.datastructures.Set;

public abstract class AbstractSet<E> extends AbstractCollection<E> implements Set<E> {

    public final boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof Set) {
            Iterator<E> thisItr = this.iterator();
            Iterator<?> otherItr = ((Set<?>) o).iterator();
            while (thisItr.hasNext() && otherItr.hasNext()) {
                E o1 = thisItr.next();
                Object o2 = otherItr.next();
                if (!(o1 == null ? o2 == null : o1.equals(o2))) {
                    return false;
                }
            }
            return !thisItr.hasNext() && !otherItr.hasNext();
        }
        return false;
    }

    public final int hashCode() {
        int hash = 1;
        for (E e : this) {
            hash = 29 * hash + (e == null ? 0 : e.hashCode());
        }
        return hash;
    }
}
