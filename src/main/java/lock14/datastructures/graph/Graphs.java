package lock14.datastructures.graph;

import lock14.datastructures.Pair;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class Graphs {

    // make utility class non-instantiable
    private Graphs() {
    }

    ///////////////////////////////////////////////////////////////////////////
    // Breadth First Search
    ///////////////////////////////////////////////////////////////////////////

    public static <V> VertexProperties<V> breadthFirstSearch(Graph<V> graph, V start, V end) {
        VertexProperties<V> properties = new VertexProperties<>();
        Queue<V> queue = new ArrayDeque<>();
        properties.markVisited(start);
        queue.add(start);
        while (!queue.isEmpty()) {
            V u = queue.remove();
            for (V v : graph.getAdjacent(u)) {
                if (!properties.visited(v)) {
                    properties.setParent(v, u);
                    properties.markVisited(v);
                    if (Objects.equals(u, end)) {
                        return properties;
                    }
                    queue.add(v);
                }
            }
        }
        return properties;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Depth First Search
    ///////////////////////////////////////////////////////////////////////////

    public static <V> VertexProperties<V> depthFirstSearch(Graph<V> graph, V start, V end) {
        VertexProperties<V> properties = new VertexProperties<>();
        Deque<V> stack = new ArrayDeque<>();
        stack.push(start);
        while (!stack.isEmpty()) {
            V u = stack.pop();
            properties.markVisited(u);
            if (Objects.equals(u, end)) {
                return properties;
            }
            for (V v : graph.getAdjacent(u)) {
                if (!properties.visited(v)) {
                    properties.setParent(v, u);
                    stack.push(v);
                }
            }
        }
        return properties;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Dijkstra Shortest Path Convenience Methods
    ///////////////////////////////////////////////////////////////////////////

    public static <V> List<V> dijkstraShortestPathByte(LabeledGraph<V, Byte> graph, V start, V end) {
        return dijkstraShortestPath(graph, start, end, (a, b) -> (byte) (a + b), (byte) 0);
    }

    public static <V> List<V> dijkstraShortestPathShort(LabeledGraph<V, Short> graph, V start, V end) {
        return dijkstraShortestPath(graph, start, end, (a, b) -> (short) (a + b), (short) 0);
    }

    public static <V> List<V> dijkstraShortestPathInt(LabeledGraph<V, Integer> graph, V start, V end) {
        return dijkstraShortestPath(graph, start, end, Integer::sum, 0);
    }

    public static <V> List<V> dijkstraShortestPathLong(LabeledGraph<V, Long> graph, V start, V end) {
        return dijkstraShortestPath(graph, start, end, Long::sum, 0L);
    }

    public static <V> List<V> dijkstraShortestPathBigInteger(LabeledGraph<V, BigInteger> graph, V start, V end) {
        return dijkstraShortestPath(graph, start, end, BigInteger::add, BigInteger.ZERO);
    }

    public static <V> List<V> dijkstraShortestPathFloat(LabeledGraph<V, Float> graph, V start, V end) {
        return dijkstraShortestPath(graph, start, end, Float::sum, (float) 0.0);
    }

    public static <V> List<V> dijkstraShortestPathDouble(LabeledGraph<V, Double> graph, V start, V end) {
        return dijkstraShortestPath(graph, start, end, Double::sum, 0.0);
    }

    public static <V> List<V> dijkstraShortestPathBigDecimal(LabeledGraph<V, BigDecimal> graph, V start, V end) {
        return dijkstraShortestPath(graph, start, end, BigDecimal::add, BigDecimal.ZERO);
    }

    public static <V, L extends Comparable<? super L>> List<V> dijkstraShortestPath(LabeledGraph<V, L> graph, V start,
                                                                                    V end, BinaryOperator<L> plus,
                                                                                    L zero) {
        return dijkstra(graph, start, Predicate.isEqual(end), plus, zero).constructParentPath(end);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Dijkstra All Shortest Paths Convenience Methods
    ///////////////////////////////////////////////////////////////////////////

    public static <V> LabeledGraph<V, Byte> dijkstraAllShortestPathsByte(LabeledGraph<V, Byte> graph, V start) {
        return dijkstraAllShortestPaths(graph, start, (a, b) -> (byte) (a + b), (byte) 0);
    }

    public static <V> LabeledGraph<V, Short> dijkstraAllShortestPathsShort(LabeledGraph<V, Short> graph, V start) {
        return dijkstraAllShortestPaths(graph, start, (a, b) -> (short) (a + b), (short) 0);
    }

    public static <V> LabeledGraph<V, Integer> dijkstraAllShortestPathsInt(LabeledGraph<V, Integer> graph, V start) {
        return dijkstraAllShortestPaths(graph, start, Integer::sum, 0);
    }

    public static <V> LabeledGraph<V, Long> dijkstraAllShortestPathsLong(LabeledGraph<V, Long> graph, V start) {
        return dijkstraAllShortestPaths(graph, start, Long::sum, 0L);
    }

    public static <V> LabeledGraph<V, BigInteger> dijkstraAllShortestPathsBigInteger(
            LabeledGraph<V, BigInteger> graph,
            V start) {
        return dijkstraAllShortestPaths(graph, start, BigInteger::add, BigInteger.ZERO);
    }

    public static <V> LabeledGraph<V, Float> dijkstraAllShortestPathsFloat(LabeledGraph<V, Float> graph, V start) {
        return dijkstraAllShortestPaths(graph, start, Float::sum, (float) 0.0);
    }

    public static <V> LabeledGraph<V, Double> dijkstraAllShortestPathsDouble(LabeledGraph<V, Double> graph, V start) {
        return dijkstraAllShortestPaths(graph, start, Double::sum, 0.0);
    }

    public static <V> LabeledGraph<V, BigDecimal> dijkstraAllShortestPathsBigDecimal(
            LabeledGraph<V, BigDecimal> graph,
            V start) {
        return dijkstraAllShortestPaths(graph, start, BigDecimal::add, BigDecimal.ZERO);
    }

    public static <V, L extends Comparable<? super L>> LabeledGraph<V, L> dijkstraAllShortestPaths(
            LabeledGraph<V, L> graph,
            V start, BinaryOperator<L> plus, L zero) {
        // we return a tree which contains all shortest paths from start to any other node
        return dijkstra(graph, start, v -> false, plus, zero).constructParentTree(graph);
    }

    ///////////////////////////////////////////////////////////////////////////
    // The actual Dijkstra algorithm
    ///////////////////////////////////////////////////////////////////////////

    private static <V, L extends Comparable<? super L>> VertexProperties<V> dijkstra(LabeledGraph<V, L> graph,
                                                                                     V start,
                                                                                     Predicate<V> stopCondition,
                                                                                     BinaryOperator<L> plus,
                                                                                     L zero) {
        VertexProperties<V> properties = new VertexProperties<>();
        Queue<Pair<V, L>> fringe = new PriorityQueue<>(Comparator.comparing(Pair::second));

        properties.setDistance(start, zero);
        fringe.add(Pair.of(start, zero));
        while (!fringe.isEmpty()) {
            Pair<V, L> pair = fringe.remove();
            V u = pair.first();
            if (stopCondition.test(u)) {
                return properties;
            }
            if (!properties.visited(u)) {
                properties.markVisited(u);
                for (V v : graph.getAdjacent(u)) {
                    L edgeDistance = graph.label(u, v);
                    L uDistance = properties.getDistance(u);
                    L newDistance = plus.apply(uDistance, edgeDistance);
                    L oldDistance = properties.getDistance(v);
                    if (oldDistance == null || newDistance.compareTo(oldDistance) < 0) {
                        // update target distance
                        properties.setDistance(v, newDistance);
                        properties.setParent(v, u);
                        fringe.add(Pair.of(v, newDistance));
                    }
                }
            }
        }
        return properties;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Minimum Spanning Tree
    ///////////////////////////////////////////////////////////////////////////

    public static <V, L extends Comparable<? super L>> LabeledGraph<V, L> minimumSpanningTree(
            LabeledGraph<V, L> graph) {
        return minimumSpanningTree(graph, graph.vertices().iterator().next());
    }

    public static <V, L extends Comparable<? super L>> LabeledGraph<V, L> minimumSpanningTree(LabeledGraph<V, L> graph,
                                                                                              V start) {
        if (graph.isDirected()) {
            throw new IllegalArgumentException("cannot construct minimum spanning tree for a directed graph!");
        }

        VertexProperties<V> properties = new VertexProperties<>();
        LabeledGraph<V, L> minimumSpanningTree = (LabeledGraph<V, L>) graph.emptyGraph();
        PriorityQueue<LabeledEdge<V, L>> queue = new PriorityQueue<>(Comparator.comparing(LabeledEdge::getLabel));
        properties.markVisited(start);
        queue.addAll(graph.incidentLabeledEdges(start));

        while (!queue.isEmpty()) {
            LabeledEdge<V, L> edge = queue.remove();
            if (!properties.visited(edge.getU()) || !properties.visited(edge.getV())) {
                properties.markVisited(edge.getU());
                for (LabeledEdge<V, L> edge2 : graph.incidentLabeledEdges(edge.getV())) {
                    if (!properties.visited(edge2.getV())) {
                        queue.add(edge2);
                    }
                }
                properties.markVisited(edge.getV());
                minimumSpanningTree.addEdge(edge);
            }
        }
        return minimumSpanningTree;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Topological Sort
    ///////////////////////////////////////////////////////////////////////////

    public static <V> List<V> topologicalOrder(Graph<V> graph) {
        if (!graph.isDirected()) {
            throw new IllegalArgumentException("Cannot compute topological ordering of an undirected graph");
        }
        Set<V> visiting = new HashSet<>(((graph.vertices().size() *  4) / 3) + 1);
        Set<V> visited = new HashSet<>(((graph.vertices().size() *  4) / 3) + 1);
        Deque<V> stack = new ArrayDeque<>(graph.vertices().size());
        for (V vertex : graph.vertices()) {
            if (dfs(graph, vertex, visited, visiting,  v -> stack.push(v))) {
                // dfs returns true if there is a cycle
                return Collections.emptyList();
            }
        }
        return new ArrayList<>(stack);
    }

    private static <V> boolean dfs(Graph<V> graph, V vertex, Set<V> visited, Set<V> visiting, Consumer<V> visitedConsumer) {
        if (!visited.contains(vertex)) {
            if (visiting.contains(vertex)) {
                // cycle exists: not a DAG
                return true;
            }
            visiting.add(vertex);
            for (V neighbor : graph.getAdjacentOut(vertex)) {
                if (dfs(graph, neighbor, visited, visiting, stack)) {
                    return true;
                }
            }
            visiting.remove(vertex);
            visited.add(vertex);
            visitedConsumer.accept(vertex);
        }
        return false;
    }
}
