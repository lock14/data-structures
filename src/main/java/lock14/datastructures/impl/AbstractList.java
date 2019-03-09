package lock14.datastructures.impl;

import java.util.Iterator;

import lock14.datastructures.List;

public abstract class AbstractList<E> extends AbstractCollection<E> implements List<E> {

    @Override
    public void add(E element) {
        add(size(), element);
    }

    @Override
    public final boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof List) {
            Iterator<E> thisItr = this.iterator();
            Iterator<?> otherItr = ((List<?>) o).iterator();
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
        for (E e : this) {
            hash = 31 * hash + (e == null ? 0 : e.hashCode());
        }
        return hash;
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        Iterator<E> itr = iterator();
        while (itr.hasNext()) {
            E e = itr.next();
            if (e == null ? o == null : e.equals(o)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    public Iterator<E> iterator() {
        return listIterator();
    }

    protected void check(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index out of range: " + index);
        }
    }
}
