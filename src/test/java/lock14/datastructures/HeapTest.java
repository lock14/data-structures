package lock14.datastructures;

import lock14.datastructures.impl.Heap;

import java.util.concurrent.atomic.AtomicInteger;

public class HeapTest extends CollectionTest<Integer> {
    public HeapTest() {
        super(Heap::new, Heap::new, new AtomicInteger()::getAndIncrement);
    }
}
