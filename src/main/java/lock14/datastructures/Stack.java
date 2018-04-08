package lock14.datastructures;

public interface Stack<E> extends Collection<E> {

    public E peek();

    public E pop();

    public void push(E element);
}
