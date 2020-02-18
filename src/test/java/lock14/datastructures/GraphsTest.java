package lock14.datastructures;

import lock14.datastructures.impl.SimpleLabledGraph;
import org.junit.Test;

public class GraphsTest {

    @Test
    public void minimumSpanningTreeTest() {
        LabledGraph<Integer, Integer> graph = LabledGraph.undirected()
                                                         .withEdge(1, 2, 1)
                                                         .withEdge(1, 3, 4)
                                                         .withEdge(1, 4, 3)
                                                         .withEdge(2, 4, 2)
                                                         .withEdge(3, 4, 5)
                                                         .build();
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
        LabledGraph<String, Integer> graph = LabledGraph.undirected()
                                                        .withEdge("a", "b", 7)
                                                        .withEdge("a", "c", 3)
                                                        .withEdge("b", "c", 1)
                                                        .withEdge("b", "d", 2)
                                                        .withEdge("b", "e", 6)
                                                        .withEdge("c", "d", 2)
                                                        .withEdge("d", "e", 4)
                                                        .build();
        System.out.println(graph);
        System.out.println(Graphs.dijkstraShortestPathInt(graph, "a", "e"));
    }

    @Test
    public void topologicalSortTest() {
        Graph<Integer> graph = Graph.directed()
                                    .withEdge(5, 11)
                                    .withEdge(7, 11)
                                    .withEdge(7, 8)
                                    .withEdge(3, 8)
                                    .withEdge(3, 10)
                                    .withEdge(11, 2)
                                    .withEdge(11, 9)
                                    .withEdge(11, 10)
                                    .withEdge(8, 9)
                                    .build();
        System.out.println(Graphs.topologicalOrder(graph));
    }

    @Test
    public void test() {
        LabledGraph<String, Integer> graph = LabledGraph.undirected()
                                                        .withEdge("a", "b", 2)
                                                        .withEdge("a", "j", 1)
                                                        .withEdge("a", "d", 3)
                                                        .withEdge("a", "h", 4)
                                                        .withEdge("b", "d", 7)
                                                        .withEdge("b", "e", 5)
                                                        .withEdge("b", "j", 9)
                                                        .withEdge("c", "d", 8)
                                                        .withEdge("c", "f", 4)
                                                        .withEdge("d", "e", 4)
                                                        .withEdge("d", "f", 5)
                                                        .withEdge("e", "g", 8)
                                                        .withEdge("f", "h", 2)
                                                        .withEdge("g", "j", 7)
                                                        .withEdge("h", "i", 5)
                                                        .withEdge("i", "j", 6)
                                                        .build();
        LabledGraph<String, Integer> tree = Graphs.minimumSpanningTree(graph, "a");
        System.out.println(tree);
        System.out.println(tree.labledEdges()
                               .stream()
                               .map(LabledEdge::getLabel)
                               .reduce(0, Integer::sum));
    }
}
