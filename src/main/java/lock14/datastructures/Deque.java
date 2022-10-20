package lock14.datastructures;

public interface Deque<E> extends Queue<E>, Stack<E> {

    @Override
    default void enqueue(E element) {
        enqueueLast(element);
    }

    void enqueueFirst(E element);

    void enqueueLast(E element);

    @Override
    default E dequeue() {
        return dequeueFirst();
    }

    E dequeueFirst();

    E dequeueLast();

    @Override
    default E peek() {
        return peekFirst();
    }

    E peekFirst();

    E peekLast();

    @Override
    default E pop() {
        return dequeueFirst();
    }

    @Override
    default void push(E item) {
        enqueueFirst(item);
    }
}
