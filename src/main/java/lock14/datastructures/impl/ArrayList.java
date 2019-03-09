package lock14.datastructures.impl;

import java.util.*;

import lock14.datastructures.Collection;
import lock14.datastructures.RandomAccessList;

public class ArrayList<E> extends AbstractList<E> implements RandomAccessList<E> {
    public static final int DEFAULT_CAPACITY = 10;
    private Object[] data;
    private int size;
    private int modificationCount;
    
    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }
    
    public ArrayList(int capacity) {
        data = new Object[capacity];
        size = 0;
        modificationCount = 0;
    }
    
    public ArrayList(Collection<E> c) {
        this(c.size());
        Optional.ofNullable(c).ifPresent(this::addAll);
    }

    @Override
    public void add(int index, E element) {
        if (index != size) {
            check(index);
        }
        if (size == data.length) {
            increaseCapacity();
        }
        shiftRight(index);
        data[index] = element;
        size++;
        modificationCount++;
    }
  
    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) {
        check(index);
        return (E) data[index];
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
        return new ArrayListIterator(index);
    }

    @Override
    public void remove(int index) {
        check(index);
        shiftLeft(index);
        data[--size] = null;
        modificationCount++;
    }
    
    @Override
    public void set(int index, E element) {
        check(index);
        data[index] = element;
    }

    @Override
    public int size() {
        return size;
    }
    
    private void increaseCapacity() {
        int newSize = data.length + (data.length >> 1);
        data = Arrays.copyOf(data, newSize);
    }
    
    private void shiftLeft(int index) {
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i+1];
        }
    }
    
    private void shiftRight(int index) {
        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
        }
    }
    
    private class ArrayListIterator implements ListIterator<E> {
        int lastReturned;
        int index;
        int expectedModCount;
        
        public ArrayListIterator(int index) {
            this.lastReturned = -1;
            this.index = index;
            this.expectedModCount = modificationCount;
        }
        
        @Override
        public void add(E element) {
            checkForModification();
            ArrayList.this.add(index, element);
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
            lastReturned = index;
            return get(index++);
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
            lastReturned = index - 1;
            return get(--index);
        }

        @Override
        public int previousIndex() {
            return index - 1;
        }

        @Override
        public void remove() {
            checkForModification();
            if (lastReturned == -1) {
                throw new IllegalStateException();
            }
            if (lastReturned != index) {
                index--;
            }
            ArrayList.this.remove(lastReturned);
            lastReturned = -1;
            expectedModCount++;
        }

        @Override
        public void set(E element) {
            checkForModification();
            ArrayList.this.set(index, element);
        }
        
        private void checkForModification() {
            if (expectedModCount != modificationCount) {
                throw new ConcurrentModificationException();
            }
        }
        
    }
    
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<Integer> list2 = new ArrayList<>();
        System.out.println(list.equals(list2));
    }
}
