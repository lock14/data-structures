package lock14.datastructures;

public interface Deque<E> extends Queue<E> {

    @Override
    public default void enqueue(E element) {
        enqueueLast(element);
    }

    public void enqueueFirst(E element);

    public void enqueueLast(E element);

    @Override
    public default E dequeue() {
        return dequeueFirst();
    }

    public E dequeueFirst();

    public E dequeueLast();

    @Override
    public default E peek() {
        return peekFirst();
    }

    public E peekFirst();

    public E peekLast();
}
