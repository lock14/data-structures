package lock14.datastructures;

import java.util.Iterator;

public interface Collection<E> extends Iterable<E> {

    void add(E e);

    void addAll(Collection<? extends E> c);

    void clear();

    boolean contains(Object o);

    boolean containsAll(Collection<?> c);

    boolean isEmpty();

    Iterator<E> iterator();

    void remove(Object o);

    void removeAll(Collection<?> c);

    void retainAll(Collection<?> c);

    int size();
}
