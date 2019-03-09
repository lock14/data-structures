package lock14.datastructures;

import java.util.Iterator;

public interface Collection<E> extends Iterable<E> {

    public void add(E e);

    public void addAll(Collection<? extends E> c);

    public void clear();

    public boolean contains(Object o);

    public boolean containsAll(Collection<?> c);

    public boolean isEmpty();

    public Iterator<E> iterator();

    public void remove(Object o);

    public void removeAll(Collection<?> c);

    public void retainAll(Collection<?> c);

    public int size();
}
