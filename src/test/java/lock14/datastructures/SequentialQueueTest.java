package lock14.datastructures;

import lock14.datastructures.impl.SequentialQueue;

import java.util.concurrent.atomic.AtomicInteger;

public class SequentialQueueTest extends CollectionTest<Integer> {

    public SequentialQueueTest() {
        super(SequentialQueue::new, SequentialQueue::new, new AtomicInteger()::getAndIncrement);
    }
}
