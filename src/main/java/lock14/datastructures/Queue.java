package lock14.datastructures;

public interface Queue<E> extends Collection<E> {

    public void enqueue(E element);

    public E dequeue();

    public E peek();
}
