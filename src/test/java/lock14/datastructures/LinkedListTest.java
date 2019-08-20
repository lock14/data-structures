package lock14.datastructures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.Test;
import lock14.datastructures.impl.ArrayList;
import lock14.datastructures.impl.LinkedList;

public class LinkedListTest {

    @Test
    public void emptyListTest() {
        LinkedList<Integer> list = new LinkedList<>();
        assertTrue(list.size() == 0);
        assertFalse(list.iterator().hasNext());
        assertFalse(list.listIterator().hasPrevious());
        assertEquals(list.toString(), "[]");
        assertEquals(list.hashCode(), 1);
        assertEquals(list, new LinkedList<>());
        assertEquals(list, new ArrayList<>());
    }

    @Test
    public void testSize() {
        LinkedList<Integer> list = new LinkedList<>();
        int j = 0;
        assertEquals(j, list.size());
        for (int i = 0; i < 100; i++) {
            list.add(i);
            assertEquals(++j, list.size());
        }
    }

    @Test
    public void traverseIteratorTest() {
        LinkedList<Integer> list = collect(IntStream.range(0, 100));
        int i = 0;
        for (int n : list) {
            assertEquals(n, i++);
        }
    }

    @Test
    public void testToString() {
        LinkedList<Integer> list = new LinkedList<>();
        assertEquals(list.toString(), "[]");
        list = collect(IntStream.range(0, 10));
        assertEquals("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]", list.toString());
    }

    @Test
    public void testIndexOf() {
        LinkedList<Integer> list = collect(IntStream.range(0, 100));
        for (int i = 0; i < list.size(); i++) {
            assertEquals(i, list.indexOf(i));
        }
    }

    @Test
    public void testClear() {
        LinkedList<Integer> list = collect(IntStream.range(0, 100));
        assertEquals(100, list.size());
        assertFalse(list.isEmpty());
        list.clear();
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    @Test
    public void testEquals() {
        LinkedList<Integer> list1 = collect(IntStream.range(0, 100));
        LinkedList<Integer> list2 = collect(IntStream.range(0, 100));
        assertEquals(list1, list2);
        assertNotEquals(list1, new LinkedList<>());
        assertNotEquals(list1, collect(IntStream.range(0, 99)));
    }

    private static LinkedList<Integer> collect(IntStream stream) {
        return collect(stream.boxed());
    }

    private static <E> LinkedList<E> collect(Stream<E> stream) {
        return stream.collect(Collector.of((Supplier<LinkedList<E>>) LinkedList::new,
                                           (list, element) -> list.add(element),
                                           (list1, list2) -> {
                                               list1.addAll(list2);
                                               return list1;
                                           }));
    }
}
