package lock14.datastructures.impl;

import java.util.ConcurrentModificationException;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Optional;

import lock14.datastructures.Collection;
import lock14.datastructures.SequentialList;

public class LinkedList<E> extends AbstractList<E> implements SequentialList<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;
    private int modificationCount;

    public LinkedList() {
        this(null);
    }

    public LinkedList(Collection<E> c) {
        // create sentinel nodes
        head = new Node<>(null);
        tail = new Node<>(null);
        head.next = tail;
        tail.prev = head;
        size = 0;
        modificationCount = 0;
        Optional.ofNullable(c).ifPresent(this::addAll);
    }

    @Override
    public void add(int index, E element) {
        if (index != size) {
            check(index);
        }
        insertBefore(nodeAt(index), element);
    }

    @Override
    public E get(int index) {
        check(index);
        return nodeAt(index).data;
    }

    @Override
    public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        if (index != size) {
            check(index);
        }
        return new LinkedListIterator(index);
    }

    @Override
    public void remove(int index) {
        check(index);
        unlink(nodeAt(index));
    }

    @Override
    public void set(int index, E element) {
        check(index);
        nodeAt(index).data = element;
    }

    @Override
    public int size() {
        return size;
    }

    private void insertBefore(Node<E> node, E element) {
        Node<E> temp = new Node<>(element, node.prev, node);
        node.prev.next = temp;
        node.prev = temp;
        size++;
        modificationCount++;
    }

    private void unlink(Node<E> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        node.prev = null;
        node.next = null;
        size--;
        modificationCount++;
    }

    private Node<E> nodeAt(int index) {
        if (index < size >> 1) {
            Node<E> cur = head.next;
            int i = 0;
            while (i < index) {
                cur = cur.next;
                i++;
            }
            return cur;
        } else {
            Node<E> cur = tail;
            int i = size;
            while (i > index) {
                cur = cur.prev;
                i--;
            }
            return cur;
        }
    }

    private class LinkedListIterator implements ListIterator<E> {
        Node<E> lastReturned;
        Node<E> next;
        int index;
        int expectedModCount;

        public LinkedListIterator(int index) {
            this.lastReturned = null;
            this.next = nodeAt(index);
            this.index = index;
            this.expectedModCount = modificationCount;
        }

        @Override
        public void add(E element) {
            checkForModification();
            insertBefore(next, element);
            expectedModCount++;
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public boolean hasPrevious() {
            return index > 0;
        }

        @Override
        public E next() {
            checkForModification();
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastReturned = next;
            next = next.next;
            index++;
            return lastReturned.data;
        }

        @Override
        public int nextIndex() {
            return index;
        }

        @Override
        public E previous() {
            checkForModification();
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            next = next.prev;
            lastReturned = next;
            index--;
            return lastReturned.data;
        }

        @Override
        public int previousIndex() {
            return index - 1;
        }

        @Override
        public void remove() {
            checkForModification();
            if (lastReturned == null) {
                throw new IllegalStateException();
            }
            if (lastReturned == next) {
                next = next.next;
            } else {
                index--;
            }
            unlink(lastReturned);
            lastReturned = null;
            expectedModCount++;
        }

        @Override
        public void set(E element) {
            checkForModification();
            if (lastReturned == null) {
                throw new IllegalStateException();
            }
            lastReturned.data = element;
        }

        private void checkForModification() {
            if (expectedModCount != modificationCount) {
                throw new ConcurrentModificationException();
            }
        }
    }

    private static final class Node<E> {
        E data;
        Node<E> prev;
        Node<E> next;

        Node(E data) {
            this(data, null, null);
        }

        Node(E data, Node<E> prev, Node<E> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

}
