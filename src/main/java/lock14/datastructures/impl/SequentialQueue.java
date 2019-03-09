package lock14.datastructures.impl;

import java.util.Iterator;
import java.util.Optional;

import lock14.datastructures.Collection;
import lock14.datastructures.SequentialList;

public class SequentialQueue<E> extends AbstractQueue<E> {
    protected SequentialList<E> queue;
    
    public SequentialQueue() {
        this((Collection<E>)null);
    }
    
    public SequentialQueue(Collection<E> c) {
        this(c, LinkedList.class);
    }
    
    public <T extends SequentialList<?>> SequentialQueue(Class<T> backingClass) {
        this(null, backingClass);
    }
    
    @SuppressWarnings("unchecked")
    public <T extends SequentialList<?>> SequentialQueue(Collection<E> c, Class<T> backingClass) {
        try {
            queue = (SequentialList<E>) backingClass.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            // convert checked exception to Runtime Exception
            throw new RuntimeException(e);
        }
        Optional.ofNullable(c).ifPresent(this::addAll);
    }
    
    @Override
    public void enqueue(E element) {
        queue.add(element);
    }

    @Override
    public E dequeue() {
        E e = peek();
        queue.remove(0);
        return e;
    }

    @Override
    public E peek() {
        return queue.get(0);
    }

    @Override
    public Iterator<E> iterator() {
        return queue.iterator();
    }

    @Override
    public int size() {
        return queue.size();
    }

}
