package lock14.datastructures;

import lock14.datastructures.impl.SimpleGraph;
import lock14.datastructures.impl.SimpleLabledGraph;
import org.junit.Test;

public class GraphsTest {

    @Test
    public void minimumSpanningTreeTest() {
        LabledGraph<Integer, Integer> graph = new SimpleLabledGraph<>(false);
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 3, 4);
        graph.addEdge(1, 4, 3);
        graph.addEdge(2, 4, 2);
        graph.addEdge(3, 4, 5);
        System.out.println(graph);
        LabledGraph<Integer, Integer> mst = Graphs.minimumSpanningTree(graph);
        System.out.println(mst);
        System.out.println(mst.labledEdges()
                              .stream()
                              .map(LabledEdge::getLabel)
                              .reduce(0, Integer::sum));
    }

    @Test
    public void djikstraShortestPathTest() {
        LabledGraph<String, Integer> graph = new SimpleLabledGraph<>(false);
        graph.addEdge("a", "b", 7);
        graph.addEdge("a", "c", 3);
        graph.addEdge("b", "c", 1);
        graph.addEdge("b", "d", 2);
        graph.addEdge("b", "e", 6);
        graph.addEdge("c", "d", 2);
        graph.addEdge("d", "e", 4);
        System.out.println(graph);
        System.out.println(Graphs.dijkstraShortestPathInt(graph, "a", "e"));
    }

    @Test
    public void topologicalSortTest() {
        Graph<Integer> graph = new SimpleGraph<>(true);
        graph.addEdge(5, 11);
        graph.addEdge(7, 11);
        graph.addEdge(7, 8);
        graph.addEdge(3, 8);
        graph.addEdge(3, 10);
        graph.addEdge(11, 2);
        graph.addEdge(11, 9);
        graph.addEdge(11, 10);
        graph.addEdge(8, 9);
        System.out.println(Graphs.topologicalOrder(graph));
    }
}
