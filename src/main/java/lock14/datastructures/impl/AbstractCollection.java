package lock14.datastructures.impl;

import java.util.Iterator;

import lock14.datastructures.Collection;

public abstract class AbstractCollection<E> implements Collection<E> {
    
    @Override
    public void addAll(Collection<? extends E> other) {
        Iterator<? extends E> itr = other.iterator();
        while (itr.hasNext()) {
            add(itr.next());
        }
    }
    
    @Override
    public void clear() {
        Iterator<E> itr = iterator();
        while (itr.hasNext()) {
            itr.next();
            itr.remove();
        }
    }
    
    @Override
    public boolean contains(Object o) {
        Iterator<E> itr = iterator();
        while (itr.hasNext()) {
            E e = itr.next();
            if (e == null ? o == null : e.equals(o)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean containsAll(Collection<?> other) {
        Iterator<?> itr = other.iterator();
        while (itr.hasNext()) {
            if (!contains(itr.next())) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }
    
    @Override
    public void remove(Object o) {
        Iterator<E> itr = iterator();
        while (itr.hasNext()) {
            E e = itr.next();
            if (e == null ? o == null : e.equals(o)) {
                itr.remove();
                return;
            }
        }
    }
    
    @Override
    public void removeAll(Collection<?> other) {
        Iterator<?> itr = other.iterator();
        while (itr.hasNext()) {
            remove(itr.next());
        }
    }
    
    @Override
    public void retainAll(Collection<?> other) {
        Iterator<E> itr = this.iterator();
        while (itr.hasNext()) {
            if (!other.contains(itr.next())) {
                itr.remove();
            }
        }
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Iterator<E> itr = iterator();
        if (itr.hasNext()) {
            E element = itr.next();
            sb.append(element == this ? "(this collection)" : element);
        }
        while (itr.hasNext()) {
            E element = itr.next();
            sb.append(", ").append(element == this ? "(this collection)" : element);
        }
        return sb.append("]").toString();
    }
}
