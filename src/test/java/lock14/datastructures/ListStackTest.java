package lock14.datastructures;

import lock14.datastructures.impl.ListStack;
import java.util.concurrent.atomic.AtomicInteger;

public class ListStackTest extends CollectionTest<Integer> {

    public ListStackTest() {
        super(ListStack::new, ListStack::new, new AtomicInteger()::getAndIncrement);
    }
}
