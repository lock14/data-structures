package lock14.datastructures.impl;

import lock14.datastructures.Collection;
import lock14.datastructures.Deque;
import lock14.datastructures.SequentialList;

public class SequentialDeque<E> extends SequentialQueue<E> implements Deque<E> {
    
    public SequentialDeque() {
        super();
    }
    
    public SequentialDeque(Collection<E> c) {
        super(c);
    }
    
    public <T extends SequentialList<?>> SequentialDeque(Class<T> backingClass) {
        super(backingClass);
    }
    
    public <T extends SequentialList<?>> SequentialDeque(Collection<E> c, Class<T> backingClass) {
        super(c, backingClass);
    }

    @Override
    public void enqueueFirst(E element) {
        queue.add(0, element);
    }

    @Override
    public void enqueueLast(E element) {
        queue.add(element);
    }

    @Override
    public E dequeueFirst() {
        E e = peekFirst();
        queue.remove(0);
        return e;
    }

    @Override
    public E dequeueLast() {
        E e = peekLast();
        queue.remove(size() -1);
        return e;
    }

    @Override
    public E peekFirst() {
        return queue.get(0);
    }

    @Override
    public E peekLast() {
        return queue.get(size() - 1);
    }

}
