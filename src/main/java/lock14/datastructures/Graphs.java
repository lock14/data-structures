package lock14.datastructures;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

public final class Graphs {

    // make utility class non-instantiable
    private Graphs() {
    }

    ///////////////////////////////////////////////////////////////////////////
    // Breadth First Search
    ///////////////////////////////////////////////////////////////////////////

    public static <V> List<V> breadthFirstSearch(Graph<V> graph, V start, V end) {
        return dequeSearch(graph, Predicate.isEqual(end), Deque::add, Deque::remove, start).constructParentPath(end);
    }

    public static <V> List<V> breadthFirstSearch2(Graph<V> graph, V start, V end) {
        if (Objects.equals(start, end)) {
            return Collections.emptyList();
        }
        VertexProperties<V> properties = new VertexProperties<>();
        Queue<V> queue = new LinkedList<>();
        properties.markVisited(start);
        queue.add(start);
        while (!queue.isEmpty()) {
            V u = queue.remove();
            if (Objects.equals(u, end)) {
                break;
            }
            for (V v : graph.getAdjacent(u)) {
                if (!properties.visited(v)) {
                    properties.markVisited(v);
                    properties.setParent(v, u);
                    queue.add(v);
                }
            }
        }
        return properties.constructParentPath(end);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Depth First Search
    ///////////////////////////////////////////////////////////////////////////

    public static <V> List<V> depthFirstSearch(Graph<V> graph, V start, V end) {
        return dequeSearch(graph, Predicate.isEqual(end), Deque::push, Deque::pop, start).constructParentPath(end);
    }

    public static <V> VertexProperties<V> depthFirstSearch2(Graph<V> graph, V start, V end) {
        VertexProperties<V> properties = new VertexProperties<>();
        Deque<V> stack = new ArrayDeque<>();
        properties.markVisited(start);
        stack.push(start);
        while (!stack.isEmpty()) {
            V u = stack.pop();
            if (Objects.equals(u, end)) {
                break;
            }
            for (V v : graph.getAdjacent(u)) {
                if (!properties.visited(v)) {
                    properties.markVisited(v);
                    properties.setParent(v, u);
                    stack.push(v);
                }
            }
        }
        return properties;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Deque Search
    ///////////////////////////////////////////////////////////////////////////

    private static <V> VertexProperties<V> dequeSearch(Graph<V> graph, Predicate<V> stopCondition,
                                                       BiConsumer<Deque<V>, V> addConsumer,
                                                       Function<Deque<V>, V> removalFunction, V start) {
        VertexProperties<V> properties = new VertexProperties<>();
        if (!stopCondition.test(start)) {
            Deque<V> deque = new ArrayDeque<>();
            properties.markVisited(start);
            addConsumer.accept(deque, start);
            while (!deque.isEmpty()) {
                V u = removalFunction.apply(deque);
                if (stopCondition.test(u)) {
                    return properties;
                }
                for (V v : graph.getAdjacent(u)) {
                    if (!properties.visited(v)) {
                        properties.markVisited(v);
                        properties.setParent(v, u);
                        addConsumer.accept(deque, v);
                    }
                }
            }
        }
        return properties;
    }
    ///////////////////////////////////////////////////////////////////////////
    // Dijkstra Shortest Path Convenience Methods
    ///////////////////////////////////////////////////////////////////////////

    public static <V> List<V> dijkstraShortestPathByte(LabeledGraph<V, Byte> graph, V start, V end) {
        return dijkstraShortestPath(graph, start, end, (byte) 0, (a, b) -> (byte) (a + b));
    }

    public static <V> List<V> dijkstraShortestPathShort(LabeledGraph<V, Short> graph, V start, V end) {
        return dijkstraShortestPath(graph, start, end, (short) 0, (a, b) -> (short) (a + b));
    }

    public static <V> List<V> dijkstraShortestPathInt(LabeledGraph<V, Integer> graph, V start, V end) {
        return dijkstraShortestPath(graph, start, end, 0, Integer::sum);
    }

    public static <V> List<V> dijkstraShortestPathLong(LabeledGraph<V, Long> graph, V start, V end) {
        return dijkstraShortestPath(graph, start, end, 0L, Long::sum);
    }

    public static <V> List<V> dijkstraShortestPathBigInteger(LabeledGraph<V, BigInteger> graph, V start, V end) {
        return dijkstraShortestPath(graph, start, end, BigInteger.ZERO, BigInteger::add);
    }

    public static <V> List<V> dijkstraShortestPathFloat(LabeledGraph<V, Float> graph, V start, V end) {
        return dijkstraShortestPath(graph, start, end, (float) 0.0, Float::sum);
    }

    public static <V> List<V> dijkstraShortestPathDouble(LabeledGraph<V, Double> graph, V start, V end) {
        return dijkstraShortestPath(graph, start, end, 0.0, Double::sum);
    }

    public static <V> List<V> dijkstraShortestPathBigDecimal(LabeledGraph<V, BigDecimal> graph, V start, V end) {
        return dijkstraShortestPath(graph, start, end, BigDecimal.ZERO, BigDecimal::add);
    }

    public static <V, L extends Comparable<? super L>> List<V> dijkstraShortestPath(LabeledGraph<V, L> graph, V start,
                                                                                    V end, L zero,
                                                                                    BinaryOperator<L> plus) {
        return dijkstra(graph, plus, Predicate.isEqual(end), start, zero).constructParentPath(end);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Dijkstra All Shortest Paths Convenience Methods
    ///////////////////////////////////////////////////////////////////////////

    public static <V> LabeledGraph<V, Byte> dijkstraAllShortestPathsByte(LabeledGraph<V, Byte> graph, V start) {
        return dijkstraAllShortestPaths(graph, start, (byte) 0, (a, b) -> (byte) (a + b));
    }

    public static <V> LabeledGraph<V, Short> dijkstraAllShortestPathsShort(LabeledGraph<V, Short> graph, V start) {
        return dijkstraAllShortestPaths(graph, start, (short) 0, (a, b) -> (short) (a + b));
    }

    public static <V> LabeledGraph<V, Integer> dijkstraAllShortestPathsInt(LabeledGraph<V, Integer> graph, V start) {
        return dijkstraAllShortestPaths(graph, start, 0, Integer::sum);
    }

    public static <V> LabeledGraph<V, Long> dijkstraAllShortestPathsLong(LabeledGraph<V, Long> graph, V start) {
        return dijkstraAllShortestPaths(graph, start, 0L, Long::sum);
    }

    public static <V> LabeledGraph<V, BigInteger> dijkstraAllShortestPathsBigInteger(
            LabeledGraph<V, BigInteger> graph,
            V start) {
        return dijkstraAllShortestPaths(graph, start, BigInteger.ZERO, BigInteger::add);
    }

    public static <V> LabeledGraph<V, Float> dijkstraAllShortestPathsFloat(LabeledGraph<V, Float> graph, V start) {
        return dijkstraAllShortestPaths(graph, start, (float) 0.0, Float::sum);
    }

    public static <V> LabeledGraph<V, Double> dijkstraAllShortestPathsDouble(LabeledGraph<V, Double> graph, V start) {
        return dijkstraAllShortestPaths(graph, start, 0.0, Double::sum);
    }

    public static <V> LabeledGraph<V, BigDecimal> dijkstraAllShortestPathsBigDecimal(
            LabeledGraph<V, BigDecimal> graph,
            V start) {
        return dijkstraAllShortestPaths(graph, start, BigDecimal.ZERO, BigDecimal::add);
    }

    public static <V, L extends Comparable<? super L>> LabeledGraph<V, L> dijkstraAllShortestPaths(
            LabeledGraph<V, L> graph,
            V start, L zero,
            BinaryOperator<L> plus) {
        // we return a tree which contains all shortest paths from start to any other node
        return dijkstra(graph, plus, v -> false, start, zero).constructParentTree(graph);
    }

    ///////////////////////////////////////////////////////////////////////////
    // The actual Dijkstra algorithm
    ///////////////////////////////////////////////////////////////////////////

    private static <V, L extends Comparable<? super L>> VertexProperties<V> dijkstra(LabeledGraph<V, L> graph,
                                                                                     BinaryOperator<L> plus,
                                                                                     Predicate<V> stopCondition,
                                                                                     V start,
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
        Set<V> visited = new HashSet<>();
        Set<V> visiting = new HashSet<>();
        LinkedList<V> stack = new LinkedList<>();
        for (V vertex : graph.vertices()) {
            if (!visited.contains(vertex)) {
                if (!visit(graph, vertex, visited, visiting, stack)) {
                    // visit returns false if there is a cycle
                    return Collections.emptyList();
                }
            }
        }
        return stack;
    }

    private static <V> boolean visit(Graph<V> graph, V start, Set<V> visited, Set<V> visiting, LinkedList<V> stack) {
        if (!visited.contains(start)) {
            if (visiting.contains(start)) {
                // cycle exists: not a DAG
                return false;
            }
            visiting.add(start);
            for (V neighbor : graph.getAdjacentOut(start)) {
                visit(graph, neighbor, visited, visiting, stack);
            }
            visited.add(start);
            stack.push(start);
        }
        return true;
    }
}
