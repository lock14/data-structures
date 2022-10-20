package lock14.datastructures.graph;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class GraphsTest {

    @Test
    public void bfsdfs() {
        Graph<Integer> graph = Graph.undirected()
                                    .withEdge(1, 2)
                                    .withEdge(1, 3)
                                    .withEdge(2, 4)
                                    .withEdge(3, 4)
                                    .build();
        System.out.println(Graphs.breadthFirstSearch(graph, 1, 4).constructParentPath(4));
        System.out.println(Graphs.depthFirstSearch(graph, 1, 4).constructParentPath(4));
    }

    @Test
    public void minimumSpanningTreeTest() {
        LabeledGraph<Integer, Integer> graph = LabeledGraph.undirected()
                                                           .withEdge(1, 2, 1)
                                                           .withEdge(1, 3, 4)
                                                           .withEdge(1, 4, 3)
                                                           .withEdge(2, 4, 2)
                                                           .withEdge(3, 4, 5)
                                                           .build();
        System.out.println(graph);
        LabeledGraph<Integer, Integer> mst = Graphs.minimumSpanningTree(graph);
        System.out.println(mst);
        System.out.println(mst.labeledEdges()
                              .stream()
                              .map(LabeledEdge::getLabel)
                              .reduce(0, Integer::sum));
    }

    @Test
    public void dijkstraShortestPathTest() {
        LabeledGraph<String, Integer> graph = LabeledGraph.undirected()
                                                          .withEdge("a", "b", 7)
                                                          .withEdge("a", "c", 3)
                                                          .withEdge("b", "c", 1)
                                                          .withEdge("b", "d", 2)
                                                          .withEdge("b", "e", 6)
                                                          .withEdge("c", "d", 2)
                                                          .withEdge("d", "e", 4)
                                                          .build();
        assertEquals(Arrays.asList("a", "c", "d", "e"), Graphs.dijkstraShortestPathInt(graph, "a", "e"));
    }

    @Test
    public void dijkstraShortestPathTest2() {
        LabeledGraph<String, Integer> graph = LabeledGraph.directed()
                                                          .withEdge("a", "b", 8)
                                                          .withEdge("a", "f", 10)
                                                          .withEdge("b", "c", 4)
                                                          .withEdge("b", "e", 10)
                                                          .withEdge("c", "d", 3)
                                                          .withEdge("d", "e", 25)
                                                          .withEdge("d", "f", 18)
                                                          .withEdge("e", "d", 9)
                                                          .withEdge("e", "g", 7)
                                                          .withEdge("f", "a", 5)
                                                          .withEdge("f", "b", 7)
                                                          .withEdge("f", "c", 3)
                                                          .withEdge("f", "e", 2)
                                                          .withEdge("g", "d", 2)
                                                          .withEdge("g", "h", 3)
                                                          .withEdge("h", "a", 4)
                                                          .withEdge("h", "b", 9)
                                                          .build();
        System.out.println(Graphs.dijkstraShortestPathInt(graph, "g", "c"));
        System.out.println(Graphs.dijkstraAllShortestPathsInt(graph, "g"));
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
        LabeledGraph<String, Integer> graph = LabeledGraph.undirected()
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
        LabeledGraph<String, Integer> tree = Graphs.minimumSpanningTree(graph, "a");
        System.out.println(tree);
        System.out.println(tree.labeledEdges()
                               .stream()
                               .map(LabeledEdge::getLabel)
                               .reduce(0, Integer::sum));
    }
}
