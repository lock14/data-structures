package lock14.datastructures;

/**
 * 
 * @author Brian Bechtel
 *
 * @param <K> - The type of keys maintained by this map
 * @param <V> - Type of mapped value
 */
public interface Map<K, V> {
    
    /**
     * Returns the number of key-value mappings in this map. If the map
     * contains more than Integer.MAX_VALUE elements, returns Integer.MAX_VALUE.
     * 
     * @return the number of key-value mappings in this map
     */
    public int size();
    
    /**
     * Returns true if this map contains no key-value mappings.
     * 
     * @return true if this map contains no key-value mappings
     */
    public boolean isEmpty();
    
    /**
     * Returns true if this map contains a mapping for the specified key.
     * More formally, returns true if and only if this map contains a mapping
     * for a key k such that (key==null ? k==null : key.equals(k)).
     * (There can be at most one such mapping.)
     * 
     * @param key - key whose presence in this map is to be tested
     * @return true if this map contains a mapping for the specified key
     * @throws ClassCastException - if the key is of an inappropriate type for this map (optional)
     * @throws NullPointerException - if the specified key is null and this map does not permit null keys (optional)
     */
    public boolean containsKey(Object key);
    
    /**
     * Returns true if this map maps one or more keys to the specified value.
     * More formally, returns true if and only if this map contains at least one
     * mapping to a value v such that (value==null ? v==null : value.equals(v)).
     * This operation will probably require time linear in the map size for most
     * implementations of the Map interface.
     * 
     * @param value - value whose presence in this map is to be tested
     * @return true if this map maps one or more keys to the specified value
     * @throws ClassCastException - if the value is of an inappropriate type for this map (optional)
     * @throws NullPointerException - if the specified value is null and this map does not permit null keys (optional)
     */
    public boolean containsValue(Object value);
    
    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key. More formally, if this map contains
     * a mapping from a key k to a value v such that (key==null ? k==null : key.equals(k)),
     * then this method returns v; otherwise it returns null.
     * (There can be at most one such mapping.)
     * 
     * If this map permits null values, then a return value of null does not necessarily
     * indicate that the map contains no mapping for the key; it's also possible that the
     * map explicitly maps the key to null. The containsKey operation may be used to
     * distinguish these two cases.
     * 
     * @param key - the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or null if this map contains no mapping for the key
     * @throws ClassCastException - if the key is of an inappropriate type for this map (optional)
     * @throws NullPointerException - if the specified key is null and this map does not permit null keys (optional)
     */
    public V get(Object key);
    
    /**
     * Associates the specified value with the specified key in this map (optional operation).
     * If the map previously contained a mapping for the key, the old value is replaced by the
     * specified value. (A map m is said to contain a mapping for a key k if and only if
     * m.containsKey(k) would return true.)
     * 
     * @param key - key with which the specified value is to be associated
     * @param value - value to be associated with the specified key
     * @return the previous value associated with key, or null if there was no mapping for key.
     *         (A null return can also indicate that the map previously associated null with key,
     *          if the implementation supports null values.)
     *          
     * @throws UnsupportedOperationException - if the put operation is not supported by this map
     * @throws ClassCastException - if the class of the specified key or value prevents it from being stored in this map
     * @throws NullPointerException - if the specified key or value is null and this map does not permit null keys or values
     * @throws IllegalArgumentException - if some property of the specified key or value prevents it from being stored in this map
     */
    public V put(K key, V value);
    
    /**
     * Removes all of the mappings from this map (optional operation)
     * 
     * @throws UnsupportedOperationException - if the clear operation is not supported by this map
     */
    public default void clear() {
        throw new UnsupportedOperationException();
    }
    
}
