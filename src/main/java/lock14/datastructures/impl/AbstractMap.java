package lock14.datastructures.impl;

import lock14.datastructures.Collection;
import lock14.datastructures.Map;
import lock14.datastructures.Pair;
import lock14.datastructures.Set;
import java.util.Iterator;

public abstract class AbstractMap<K, V> implements Map<K, V> {

    @Override
    public Set<K> keySet() {
        return new AbstractSet<K>() {
            private Set<Pair<K, V>> entrySet = entrySet();

            @Override
            public Iterator<K> iterator() {
                return new Iterator<K>() {
                    private Iterator<Pair<K, V>> entrySetItr = entrySet.iterator();

                    @Override
                    public boolean hasNext() {
                        return entrySetItr.hasNext();
                    }

                    @Override
                    public K next() {
                        return entrySetItr.next().first();
                    }
                };
            }

            @Override
            public int size() {
                return entrySet.size();
            }

            @Override
            public void clear() {
                entrySet.clear();
            }

            @Override
            public boolean contains(Object key) {
                return AbstractMap.this.containsKey(key);
            }
        };
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        for (Pair<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.first(), entry.second());
        }
    }

    public Collection<V> values() {
        throw new UnsupportedOperationException();
    }

    // TODO: equals, hashCode, toString
}
