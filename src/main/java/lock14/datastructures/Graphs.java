package lock14.datastructures;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.BinaryOperator;

// TODO: switch all auxiliary data-structures over to my implementations
// once they are finished.
public final class Graphs {

    // make utility class non-instantiable
    private Graphs() {}

    public static <V> boolean breadthFirstSearch(Graph<V> graph, V start, V end) {
        if (Objects.equals(start, end)) {
            return true;
        }
        Set<V> visited = new HashSet<>();
        Set<V> visiting = new HashSet<>();
        Queue<V> queue = new LinkedList<>();
        visiting.add(start);
        queue.add(start);
        while (!queue.isEmpty()) {
            V u = queue.remove();
            for (V v : graph.getAdjacent(u)) {
                if (!visited.contains(v) && !visiting.contains(v)) {
                    if (Objects.equals(v, end)) {
                        return true;
                    }
                    visiting.add(v);
                    queue.add(v);
                }
            }
            visiting.remove(u);
            visited.add(u);
        }
        return false;
    }

    public static <V> boolean depthFirstSearch(Graph<V> graph, V start, V end) {
        return depthFirstSearch(graph, start, end, new HashSet<>());
    }

    private static <V> boolean depthFirstSearch(Graph<V> graph, V u, V end, Set<V> visited) {
        if (!visited.contains(u)) {
            if (Objects.equals(u, end)) {
                return true;
            }
            visited.add(u);
            for (V v : graph.getAdjacent(u)) {
                if (depthFirstSearch(graph, v, end, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static <V> List<V> dijkstraShortestPathByte(LabledGraph<V, Byte> graph, V start, V end) {
        return dijkstraShortestPath(graph, start, end, (byte) 0, (a, b) -> (byte) (a + b));
    }

    public static <V> List<V> dijkstraShortestPathShort(LabledGraph<V, Short> graph, V start, V end) {
        return dijkstraShortestPath(graph, start, end, (short) 0, (a, b) -> (short) (a + b));
    }

    public static <V> List<V> dijkstraShortestPathInt(LabledGraph<V, Integer> graph, V start, V end) {
        return dijkstraShortestPath(graph, start, end, 0, Integer::sum);
    }

    public static <V> List<V> dijkstraShortestPathLong(LabledGraph<V, Long> graph, V start, V end) {
        return dijkstraShortestPath(graph, start, end, 0L, Long::sum);
    }

    public static <V> List<V> dijkstraShortestPathBigInteger(LabledGraph<V, BigInteger> graph, V start, V end) {
        return dijkstraShortestPath(graph, start, end, BigInteger.ZERO, BigInteger::add);
    }

    public static <V> List<V> dijkstraShortestPathFloat(LabledGraph<V, Float> graph, V start, V end) {
        return dijkstraShortestPath(graph, start, end, (float) 0.0, Float::sum);
    }

    public static <V> List<V> dijkstraShortestPathDouble(LabledGraph<V, Double> graph, V start, V end) {
        return dijkstraShortestPath(graph, start, end, 0.0, Double::sum);
    }

    public static <V> List<V> dijkstraShortestPathBigDecimal(LabledGraph<V, BigDecimal> graph, V start, V end) {
        return dijkstraShortestPath(graph, start, end, BigDecimal.ZERO, BigDecimal::add);
    }

    public static <V, L extends Comparable<? super L>> List<V> dijkstraShortestPath(LabledGraph<V, L> graph, V start,
                                                                                    V end, L zero,
                                                                                    BinaryOperator<L> plus) {
        Set<V> visited = new HashSet<>();
        Map<V, V> previous = new HashMap<>();
        Map<V, L> cost = new HashMap<>();
        Queue<VertexLabel<V, L>> fringe = new PriorityQueue<>(Comparator.comparing(VertexLabel::getLabel));

        cost.put(start, zero);
        fringe.add(vertexLabel(start, zero));
        while (!fringe.isEmpty()) {
            VertexLabel<V, L> vertexLabel = fringe.remove();
            V u = vertexLabel.vertex;
            if (Objects.equals(u, end)) {
                break;
            }
            if (!visited.contains(u)) {
                visited.add(u);
                for (V v : graph.getAdjacent(u)) {
                    L edgeWeight = graph.label(u, v).orElse(zero);
                    L newCost = plus.apply(cost.get(u), edgeWeight);
                    L oldCost = cost.get(v);
                    if (oldCost == null || newCost.compareTo(oldCost) < 0) {
                        // update target cost
                        cost.put(v, newCost);
                        previous.put(v, u);
                        fringe.add(vertexLabel(v, newCost));
                    }
                }
            }
        }
        LinkedList<V> path = new LinkedList<>();
        V cur = end;
        while (!Objects.equals(cur, start)) {
            path.addFirst(cur);
            cur = previous.get(cur);
        }
        path.addFirst(start);
        return path;
    }

    public static <V> LabledGraph<V, Byte> dijkstraAllShortestPathsByte(LabledGraph<V, Byte> graph, V start) {
        return dijkstraAllShortestPaths(graph, start, (byte) 0, (a, b) -> (byte) (a + b));
    }

    public static <V> LabledGraph<V, Short> dijkstraAllShortestPathsShort(LabledGraph<V, Short> graph, V start) {
        return dijkstraAllShortestPaths(graph, start, (short) 0, (a, b) -> (short) (a + b));
    }

    public static <V> LabledGraph<V, Integer> dijkstraAllShortestPathsInt(LabledGraph<V, Integer> graph, V start) {
        return dijkstraAllShortestPaths(graph, start, 0, Integer::sum);
    }

    public static <V> LabledGraph<V, Long> dijkstraAllShortestPathsLong(LabledGraph<V, Long> graph, V start) {
        return dijkstraAllShortestPaths(graph, start, 0L, Long::sum);
    }

    public static <V> LabledGraph<V, BigInteger> dijkstraAllShortestPathsBigInteger(
                                                                                    LabledGraph<V, BigInteger> graph,
                                                                                    V start) {
        return dijkstraAllShortestPaths(graph, start, BigInteger.ZERO, BigInteger::add);
    }

    public static <V> LabledGraph<V, Float> dijkstraAllShortestPathsFloat(LabledGraph<V, Float> graph, V start) {
        return dijkstraAllShortestPaths(graph, start, (float) 0.0, Float::sum);
    }

    public static <V> LabledGraph<V, Double> dijkstraAllShortestPathsDouble(LabledGraph<V, Double> graph, V start) {
        return dijkstraAllShortestPaths(graph, start, 0.0, Double::sum);
    }

    public static <V> LabledGraph<V, BigDecimal> dijkstraAllShortestPathsBigDecimal(
                                                                                    LabledGraph<V, BigDecimal> graph,
                                                                                    V start) {
        return dijkstraAllShortestPaths(graph, start, BigDecimal.ZERO, BigDecimal::add);
    }

    public static <V, L extends Comparable<? super L>> LabledGraph<V, L> dijkstraAllShortestPaths(
                                                                                                  LabledGraph<V, L> graph,
                                                                                                  V start, L zero,
                                                                                                  BinaryOperator<L> plus) {
        Set<V> visited = new HashSet<>();
        Map<V, V> previous = new HashMap<>();
        Map<V, L> cost = new HashMap<>();
        Queue<VertexLabel<V, L>> fringe = new PriorityQueue<>(Comparator.comparing(VertexLabel::getLabel));

        cost.put(start, zero);
        fringe.add(vertexLabel(start, zero));
        while (!fringe.isEmpty()) {
            VertexLabel<V, L> vertexLabel = fringe.remove();
            V u = vertexLabel.vertex;
            if (!visited.contains(u)) {
                visited.add(u);
                for (V v : graph.getAdjacent(u)) {
                    L edgeWeight = graph.label(u, v).orElse(zero);
                    L newCost = plus.apply(cost.get(u), edgeWeight);
                    L oldCost = cost.get(v);
                    if (oldCost == null || newCost.compareTo(oldCost) < 0) {
                        // update target cost
                        cost.put(v, newCost);
                        previous.put(v, u);
                        fringe.add(vertexLabel(v, newCost));
                    }
                }
            }
        }

        // we return a tree which contains all shortest paths from start to any other node
        LabledGraph<V, L> tree = (LabledGraph<V, L>) graph.emptyGraph();
        previous.forEach((v, u) -> tree.addEdge(u, v, graph.label(u, v).orElse(zero)));
        return tree;
    }

    public static <V, L extends Comparable<? super L>> LabledGraph<V, L> minimumSpanningTree(LabledGraph<V, L> graph) {
        if (graph.isDirected()) {
            throw new IllegalArgumentException("cannot construct minimum spanning tree for a directed graph!");
        }

        Set<V> visited = new HashSet<>();
        LabledGraph<V, L> minimumSpanningTree = (LabledGraph<V, L>) graph.emptyGraph();
        PriorityQueue<LabledEdge<V, L>> queue = new PriorityQueue<>(Comparator.comparing(LabledEdge::getLabel));

        V start = graph.vertices().iterator().next();
        visited.add(start);
        queue.addAll(graph.incidentLabledEdges(start));

        while (!queue.isEmpty()) {
            LabledEdge<V, L> edge = queue.remove();
            if (!visited.contains(edge.getU()) || !visited.contains(edge.getV())) {
                visited.add(edge.getU());
                for (LabledEdge<V, L> edge2 : graph.incidentLabledEdges(edge.getV())) {
                    if (!visited.contains(edge2.getV())) {
                        queue.add(edge2);
                    }
                }
                visited.add(edge.getV());
                minimumSpanningTree.addEdge(edge);
            }
        }
        return minimumSpanningTree;
    }

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

    private static <V, L extends Comparable<? super L>> VertexLabel<V, L> vertexLabel(V v, L label) {
        return new VertexLabel<>(v, label);
    }

    private static class VertexLabel<V, L> {
        private V vertex;
        private L label;

        VertexLabel(V vertex, L label) {
            this.vertex = vertex;
            this.label = label;
        }

        public V getVertex() {
            return vertex;
        }

        public L getLabel() {
            return label;
        }
    }
}
