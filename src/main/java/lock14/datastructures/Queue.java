package lock14.datastructures;

public interface Queue<E> extends Collection<E> {

    void enqueue(E element);

    E dequeue();

    E peek();
}
