package lock14.datastructures.impl;

import lock14.datastructures.Map;
import lock14.datastructures.Pair;
import lock14.datastructures.Set;
import java.util.Iterator;

public class HashTable<K, V> implements Map<K, V> {
    public static final int DEFAULT_SIZE = 100;
    private static final double MAX_LOAD_FACTOR = 0.7;

    private HashNode<?, ?>[] hashTable;
    private int size;

    public HashTable() {
        this(DEFAULT_SIZE);
    }

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
            insertEntry(new HashNode<K, V>(key, value));
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

    @SuppressWarnings({"unchecked", "rawtypes"})
    public V remove(Object key) {
        int hash = fixHash(key.hashCode());
        HashNode prev = null;
        HashNode<?, ?> entry = hashTable[hash];
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
        for (int i = 0; i < hashTable.length; i++) {
            hashTable[i] = null;
        }
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public Set<K> keySet() {
        // TODO : implement this
        throw new UnsupportedOperationException();
    }

    private class KeySet extends AbstractSet<K> {

        @Override
        public Iterator<K> iterator() {
            return null;
        }

        @Override
        public int size() {
            return HashTable.this.size();
        }
    }

    public Set<Pair<K, V>> entrySet() {
        // TODO : implement this
        throw new UnsupportedOperationException();
    }

    private class EntrySet extends AbstractSet<K> {

        @Override
        public Iterator<K> iterator() {
            return null;
        }

        @Override
        public int size() {
            return HashTable.this.size();
        }
    }

    private int fixHash(int hash) {
        return (hash & 0x7FFFFFFF) % hashTable.length;
    }

    @SuppressWarnings("unchecked")
    private HashNode<K, V> getEntry(Object key) {
        // we want null pointer to be thrown if
        // key is null
        int hash = fixHash(key.hashCode());
        HashNode<?, ?> entry = hashTable[hash];
        while (entry != null) {
            if (entry.key.equals(key)) {
                return (HashNode<K, V>) entry;
            }
            entry = entry.next;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private void insertEntry(HashNode<K, V> entry) {
        if (load_factor() > MAX_LOAD_FACTOR) {
            resize();
        }
        int hash = fixHash(entry.key.hashCode());
        HashNode<K, V> current = (HashNode<K, V>) hashTable[hash];
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

    private static final class HashNode<K, V> implements Pair<K, V> {
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
}
