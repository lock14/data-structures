package lock14.datastructures.impl;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.Optional;

import lock14.datastructures.Collection;
import lock14.datastructures.List;
import lock14.datastructures.Stack;

public class ListStack<E> extends AbstractStack<E> {
    private List<E> stack;

    public ListStack() {
        this((Collection<E>) null);
    }

    public ListStack(Collection<E> c) {
        this(c, ArrayList.class);
    }

    public <T extends List<?>> ListStack(Class<T> backingClass) {
        this(null, backingClass);
    }

    @SuppressWarnings("unchecked")
    public <T extends List<?>> ListStack(Collection<E> c, Class<T> backingClass) {
        try {
            stack = (List<E>) backingClass.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            // convert checked exception to Runtime Exception
            throw new RuntimeException(e);
        }
        Optional.ofNullable(c).ifPresent(this::addAll);
    }

    @Override
    public Iterator<E> iterator() {
        return new ListStackIterator(stack);
    }

    @Override
    public E peek() {
        return stack.get(size() - 1);
    }

    @Override
    public E pop() {
        E e = peek();
        stack.remove(size() - 1);
        return e;
    }

    @Override
    public void push(E element) {
        stack.add(element);
    }

    @Override
    public int size() {
        return stack.size();
    }

    private class ListStackIterator implements Iterator<E> {

        private ListIterator<E> listiterator;

        protected ListStackIterator(List<E> list) {
            this.listiterator = list.listIterator(list.size());
        }

        @Override
        public boolean hasNext() {
            return listiterator.hasPrevious();
        }

        @Override
        public E next() {
            return listiterator.previous();
        }

        @Override
        public void remove() {
            listiterator.remove();
        }

    }

    public static void main(String[] args) {
        Stack<Integer> stack = new ListStack<>();
        System.out.println(stack);
        stack.push(2);
        System.out.println(stack);
        stack.push(3);
        System.out.println(stack);
        Stack<Integer> stack2 = new ListStack<>(LinkedList.class);
        System.out.println(stack2);
        stack2.add(2);
        System.out.println(stack2);
        stack2.add(3);
        System.out.println(stack2);
    }
}
