package lock14.datastructures;

import java.util.ListIterator;

public interface List<E> extends Collection<E> {

    void add(int index, E element);

    E get(int index);

    int indexOf(Object o);

    ListIterator<E> listIterator();

    ListIterator<E> listIterator(int index);

    void remove(int index);

    void set(int index, E element);
}
