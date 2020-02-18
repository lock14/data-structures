package lock14.datastructures;

import org.junit.Test;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

public abstract class CollectionTest<T> {
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
    }

    @Test
    public void testNullCollectionUsedForInstantiation() {
        Collection<T> collection = fromCollection.apply(null);
        assertTrue(collection.isEmpty());
    }

    private Collection<T> collect(Stream<T> stream) {
        return stream.collect(Collector.of(collectionSupplier,
                                           Collection::add,
                                           (list1, list2) -> {
                                               list1.addAll(list2);
                                               return list1;
                                           }));
    }
}
