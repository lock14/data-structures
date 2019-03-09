package lock14.datastructures.impl;

import java.util.Iterator;

import lock14.datastructures.Stack;

public abstract class AbstractStack<E> extends AbstractCollection<E> implements Stack<E> {
    @Override
    public void add(E element) {
        push(element);
    }
    
    @Override
    public final boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof Stack) {
            Iterator<E> thisItr = this.iterator();
            Iterator<?> otherItr = ((Stack<?>) o).iterator();
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
    
    @Override
    public final int hashCode() {
        int hash = 1;
        for (E e : this)
            hash = 23 * hash + (e == null ? 0 : e.hashCode());
        return hash;
    }
}
