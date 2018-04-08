package lock14.datastructures;

import java.util.ListIterator;

public interface List<E> extends Collection<E> {
    
    public void add(int index, E element);

    public E get(int index);

    public int indexOf(Object o);

    public ListIterator<E> listIterator();

    public ListIterator<E> listIterator(int index);

    public void remove(int index);

    public void set(int index, E element);
}
