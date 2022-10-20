package lock14.datastructures;

import lock14.datastructures.impl.SequentialDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class SequentialDequeTest extends CollectionTest<Integer> {

    public SequentialDequeTest() {
        super(SequentialDeque::new, SequentialDeque::new, new AtomicInteger()::getAndIncrement);
    }
}
