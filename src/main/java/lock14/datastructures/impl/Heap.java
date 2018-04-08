package lock14.datastructures.impl;

import java.util.Arrays;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Random;

public class Heap<E extends Comparable<? super E>> extends AbstractQueue<E> {
    public static int     DEFAULT_CAPACITY = 10;
    private Comparator<? super E> comparator;
    private Object[]      heap;
    private int           size;
    private int           modCount;

    public Heap() {
        this(Comparator.naturalOrder());
    }

    public Heap(Comparator<? super E> comparator) {
        this(DEFAULT_CAPACITY, comparator);
    }

    public Heap(int capacity, Comparator<? super E> comparator) {
        this.comparator = comparator;
        heap = new Object[capacity];
        size = 0;
        modCount = 0;
    }
    
    @Override
    public void enqueue(E item) {
        if (size == heap.length) {
            increaseCapacity();
        }
        heap[size++] = item;
        modCount++;
        siftUp(size - 1);
    }
    
    @Override
    public E dequeue() {
        E e = peek();
        delete(0);
        return e;
    }
    
    @Override
    public Iterator<E> iterator() {
        return new HeapIterator();
    }
    
    @Override
    public E peek() {
        return get(0);
    }
    
    @Override
    public int size() {
        return size;
    }
    
    private void delete(int index) {
        heap[index] = heap[--size];
        heap[size] = null;
        modCount++;
        if (hasParent(index) && !heapCondition(parent(index), index)) {
            siftUp(index);
        } else {
            siftDown(index);
        }
    }
    
    private void siftUp(int child) {
        while (hasParent(child) && !heapCondition(parent(child), child)) {
            swap(parent(child), child);
            child = parent(child);
        }
    }
    
    private void siftDown(int parent) {
        while (hasLeft(parent)) {
            int target = left(parent);
            if (hasRight(parent) && heapCondition(right(parent), left(parent))) {
                target = right(parent);
            }
            if (!heapCondition(parent, target)) {
                swap(parent, target);
            } else {
                break;
            }
            parent = target;
        }
    }
    
    @SuppressWarnings("unchecked")
    private E get(int index) {
        return (E) heap[index];
    }
    
    private void swap(int index1, int index2) {
        E temp = get(index1);
        heap[index1] = heap[index2];
        heap[index2] = temp;
    }
    
    private boolean heapCondition(int index1, int index2) {
        return comparator.compare(get(index1), get(index2)) <= 0;
    }

    private void increaseCapacity() {
        int newSize = heap.length + (heap.length >> 1);
        heap = Arrays.copyOf(heap, newSize);
    }
    
    private boolean hasParent(int index) {
        return index > 0;
    }
    
    private boolean hasLeft(int index) {
        return left(index) < size;
    }
    
    private boolean hasRight(int index) {
        return right(index) < size;
    }
    
    private int parent(int index) {
        return (index - 1) >> 1;
    }
    
    private int left(int index) {
        return 2 * index + 1;
    }
    
    private int right(int index) {
        return 2 * index + 2;
    }
    
    private class HeapIterator implements Iterator<E> {
        int lastReturned;
        int index;
        int expectedModCount;
        
        public HeapIterator() {
            index = 0;
            lastReturned = -1;
            expectedModCount = modCount;
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public E next() {
            checkForModification();
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastReturned = index;
            return get(index++);
        }
        
        @Override
        public void remove() {
            checkForModification();
            if (lastReturned == -1) {
                throw new IllegalStateException();
            }
            delete(lastReturned);
            lastReturned = -1;
            index--;
        }
        
        private void checkForModification() {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
        }
    }
    
    public static void main(String[] args) {
        Heap<Integer> heap = new Heap<>();
        PriorityQueue<Integer> heap2 = new PriorityQueue<>();
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            int item = r.nextInt(100) + 1;
            heap.add(item);
            heap2.add(item);
        }
        System.out.println(heap);
        System.out.println(heap2);
        for (int i = 0; i < 10; i++) {
            System.out.print(heap.remove() + " ");
        }
        System.out.println();
        for (int i = 0; i < 10; i++) {
            System.out.print(heap2.remove() + " ");
        }
        System.out.println();
    }
}
