package lock14.datastructures;

import org.junit.Test;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public abstract class CollectionTest<T> {
    public static final int TEST_SIZE = 100000;
    public static final String TO_STRING_PATTERN = "\\[(.+, )*(.+)?\\]";
    public static final Random RNG = new Random();

    Supplier<Collection<T>> collectionSupplier;
    Function<Collection<T>, Collection<T>> fromCollection;
    Supplier<T> tSupplier;

    public CollectionTest(Supplier<Collection<T>> collectionSupplier,
                          Function<Collection<T>, Collection<T>> fromCollection,
                          Supplier<T> tSupplier) {
        this.collectionSupplier = collectionSupplier;
        this.fromCollection = fromCollection;
        this.tSupplier = tSupplier;
    }

    @Test
    public void testNewCollectionIsEmpty() {
        Collection<T> collection = collectionSupplier.get();
        assertTrue(collection.isEmpty());
        assertTrue(collection.toString().matches(TO_STRING_PATTERN));
    }

    @Test
    public void testNullCollectionUsedForInstantiation() {
        Collection<T> collection = fromCollection.apply(null);
        assertTrue(collection.isEmpty());
        assertTrue(collection.toString().matches(TO_STRING_PATTERN));
    }

    @Test
    public void testToString() {
        Collection<T> collection = collect(Stream.generate(tSupplier).limit(RNG.nextInt(TEST_SIZE)));
        assertTrue(collection.toString().matches(TO_STRING_PATTERN));
    }

    @Test
    public void testSize() {
        int size = RNG.nextInt(TEST_SIZE);
        Collection<T> collection = collect(Stream.generate(tSupplier).limit(size));
        assertEquals(size, collection.size());
        assertFalse(collection.isEmpty());
    }

    @Test
    public void testClear() {
        int size = RNG.nextInt(TEST_SIZE);
        Collection<T> collection = collect(Stream.generate(tSupplier).limit(size));
        assertEquals(size, collection.size());
        assertFalse(collection.isEmpty());
        collection.clear();
        assertEquals(0, collection.size());
        assertTrue(collection.isEmpty());
    }

    @Test
    public void testIterator() {
        Set<T> items = Stream.generate(tSupplier)
                             .limit(RNG.nextInt(TEST_SIZE))
                             .collect(Collectors.toSet());
        Collection<T> collection = collect(items.stream());
        for (T item : collection) {
            assertTrue(items.contains(item));
            assertTrue(items.remove(item));
        }
        assertTrue(items.isEmpty());
    }

    private Collection<T> collect(Stream<T> stream) {
        return stream.collect(Collector.of(collectionSupplier,
                                           Collection::add,
                                           (collection1, collection2) -> {
                                               collection1.addAll(collection2);
                                               return collection1;
                                           }));
    }
}
