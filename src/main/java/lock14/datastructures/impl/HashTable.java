package lock14.datastructures.impl;

import lock14.datastructures.Map;
import lock14.datastructures.Pair;
import lock14.datastructures.Set;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class HashTable<K, V> extends AbstractMap<K, V> {
    private static final int DEFAULT_SIZE = 100;
    private static final double MAX_LOAD_FACTOR = 0.7;

    private HashNode<K, V>[] hashTable;
    private int size;
    private int modificationCount;

    private Set<Pair<K, V>> entrySet = new AbstractSet<Pair<K, V>>() {
        @Override
        public boolean contains(Object o) {
            if (!(o instanceof Pair)) {
                return false;
            }
            Pair<?, ?> entry = (Pair<?, ?>) o;
            return Objects.equals(entry, HashTable.this.get(entry.first()));
        }

        @Override
        public void clear() {
            HashTable.this.clear();
        }

        @Override
        public Iterator<Pair<K, V>> iterator() {
            return new EntryIterator();
        }

        @Override
        public void remove(Object o) {
            if (o instanceof Pair) {
                Pair<?, ?> entry = (Pair<?, ?>) o;
                HashTable.this.remove(entry.first());
            }
        }

        @Override
        public int size() {
            return HashTable.this.size();
        }
    };

    private Set<K> keySet = new AbstractSet<K>() {
        @Override
        public boolean contains(Object o) {
            return containsKey(o);
        }

        @Override
        public void clear() {
            HashTable.this.clear();
        }

        @Override
        public Iterator<K> iterator() {
            return new KeyIterator();
        }

        @Override
        public void remove(Object key) {
            HashTable.this.remove(key);
        }

        @Override
        public int size() {
            return HashTable.this.size();
        }
    };

    public HashTable() {
        this(DEFAULT_SIZE);
    }

    @SuppressWarnings("unchecked")
    public HashTable(int size) {
        hashTable = new HashNode[size];
    }

    @Override
    public V put(K key, V value) {
        if (value == null) {
            throw new NullPointerException();
        }
        HashNode<K, V> entry = getEntry(key);
        // key will not be null if we get to this line
        V result = null;
        if (entry == null) {
            insertEntry(new HashNode<>(key, value));
        } else {
            result = entry.value;
            entry.value = value;
        }
        return result;
    }

    @Override
    public V get(Object key) {
        HashNode<K, V> entry = getEntry(key);
        return (entry == null) ? null : entry.value;
    }

    @Override
    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        if (value == null) {
            throw new NullPointerException();
        }
        for (HashNode<?, ?> entry : hashTable) {
            while (entry != null) {
                if (entry.value.equals(value)) {
                    return true;
                }
                entry = entry.next;
            }
        }
        return false;
    }

    public V remove(Object key) {
        int hash = fixHash(key.hashCode());
        HashNode prev = null;
        HashNode<K, V> entry = hashTable[hash];
        V result = null;
        while (entry != null) {
            if (entry.key.equals(key)) {
                result = (V) entry.value;
                if (prev == null) {
                    hashTable[hash] = entry.next;
                } else {
                    prev.next = entry.next;
                }
                entry.next = null;
            }
            prev = entry;
            entry = entry.next;
        }
        return result;
    }

    public void clear() {
        Arrays.fill(hashTable, null);
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public Set<K> keySet() {
        return keySet;
    }

    public Set<Pair<K, V>> entrySet() {
        return entrySet;
    }

    private int fixHash(int hash) {
        return (hash & 0x7FFFFFFF) % hashTable.length;
    }

    @SuppressWarnings("unchecked")
    private HashNode<K, V> getEntry(Object key) {
        // we want null pointer to be thrown if
        // key is null
        int hash = fixHash(key.hashCode());
        HashNode<K, V> entry = hashTable[hash];
        while (entry != null) {
            if (entry.key.equals(key)) {
                return entry;
            }
            entry = entry.next;
        }
        return null;
    }

    private void insertEntry(HashNode<K, V> entry) {
        if (load_factor() > MAX_LOAD_FACTOR) {
            resize();
        }
        int hash = fixHash(entry.key.hashCode());
        HashNode<K, V> current = hashTable[hash];
        if (current == null) {
            hashTable[hash] = entry;
        } else {
            while (current.next != null) {
                current = current.next;
            }
            current.next = entry;
        }
        size++;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void resize() {
        HashNode[] newHashTable = new HashNode[hashTable.length + (hashTable.length / 2)];
        HashNode[] oldHashTable = hashTable;
        hashTable = newHashTable;
        for (HashNode<K, V> current : oldHashTable) {
            while (current != null) {
                put(current.key, current.value);
                current = current.next;
            }
        }
    }

    private double load_factor() {
        return ((double) size) / ((double) hashTable.length);
    }

    private static final class HashNode<K, V> extends AbstractPair<K, V> {
        K key;
        V value;
        HashNode<K, V> next;

        HashNode(K key, V value) {
            this(key, value, null);
        }

        HashNode(K key, V value, HashNode<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public K first() {
            return key;
        }

        @Override
        public V second() {
            return value;
        }

    }

    private class KeyIterator extends HashNodeIterator implements Iterator<K> {
        @Override
        public K next() {
            return nextNode().key;
        }
    }

    private class ValueIterator extends HashNodeIterator implements Iterator<V> {
        @Override
        public V next() {
            return nextNode().value;
        }
    }

    private class EntryIterator extends HashNodeIterator implements Iterator<Pair<K, V>> {
        @Override
        public Pair<K, V> next() {
            return nextNode();
        }
    }

    private abstract class HashNodeIterator {
        HashNode<K, V> current;
        HashNode<K, V> next;
        int index;
        int expectedModCount;

        public HashNodeIterator() {
            index = 0;
            expectedModCount = modificationCount;
            next = getNextNode(null);
            current = null;
        }

        public boolean hasNext() {
            return next != null;
        }

        public HashNode<K, V> nextNode() {
            checkForModification();
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            current = next;
            next = getNextNode(next.next);
            return current;
        }

        public void remove() {
            checkForModification();
            if (current == null) {
                throw new IllegalStateException();
            }
            HashTable.this.remove(current.key);
            expectedModCount = modificationCount;
        }

        private HashNode<K, V> getNextNode(HashNode<K, V> nextNode) {
            if (nextNode == null) {
                while (index < hashTable.length && (nextNode = hashTable[index++]) == null) {
                }
            }
            return nextNode;
        }

        private void checkForModification() {
            if (expectedModCount != modificationCount) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
