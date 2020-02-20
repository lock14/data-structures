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
import java.util.function.Predicate;

public final class Graphs {

    // make utility class non-instantiable
    private Graphs() {}

    ///////////////////////////////////////////////////////////////////////////
    // Breadth First Search
    ///////////////////////////////////////////////////////////////////////////

    public static <V> List<V> breadthFirstSearch(Graph<V> graph, V start, V end) {
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
        return constructPath(properties, start, end);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Depth First Search
    ///////////////////////////////////////////////////////////////////////////

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
        return constructPath(dijkstra(graph, plus, v -> Objects.equals(v, end), start, zero), start, end);
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
        return constructTree(graph, dijkstra(graph, plus, v -> false, start, zero), start);
    }

    ///////////////////////////////////////////////////////////////////////////
    // The actual Dijkstra algorithm
    ///////////////////////////////////////////////////////////////////////////

    private static <V, L extends Comparable<? super L>> VertexProperties<V> dijkstra(LabeledGraph<V, L> graph,
                                                                                     BinaryOperator<L> plus,
                                                                                     Predicate<V> stopCondition,
                                                                                     V start,
                                                                                     L zero) {
        @SuppressWarnings("unchecked")
        Class<? extends L> lClass = (Class<? extends L>) zero.getClass();

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
                    L edgeDistance = graph.label(u, v).orElse(zero);
                    L uDistance = properties.getDistance(u, lClass);
                    L newDistance = plus.apply(uDistance, edgeDistance);
                    L oldDistance = properties.getDistance(v, lClass);
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

    ///////////////////////////////////////////////////////////////////////////
    // Utility methods/classes
    ///////////////////////////////////////////////////////////////////////////

    private static <V> List<V> constructPath(VertexProperties<V> properties, V start, V end) {
        LinkedList<V> path = new LinkedList<>();
        if (Objects.equals(start, end) || properties.getParent(end) != null) {
            V cur = end;
            while (!Objects.equals(cur, start)) {
                path.addFirst(cur);
                cur = properties.getParent(cur);
            }
            path.addFirst(start);
        }
        return path;
    }

    // assumes there is a parent entry in VertexProperties for each node in the graph except for start
    private static <V, L extends Comparable<? super L>> LabeledGraph<V, L> constructTree(LabeledGraph<V, L> graph,
                                                                                         VertexProperties<V> properties,
                                                                                         V start) {
        LabeledGraph<V, L> tree = (LabeledGraph<V, L>) graph.emptyGraph();
        graph.vertices()
             .stream()
             .filter(v -> !Objects.equals(v, start))
             .forEach(v -> {
                 V u = properties.getParent(v);
                 tree.addEdge(u, v, graph.label(u, v).orElse(null));
             });
        return tree;
    }

    private static class VertexProperties<V> {
        public static final String COLOR = "color";
        public static final String DISTANCE = "cost";
        public static final String PARENT = "parent";
        public static final String VISITED = "visited";

        private Map<V, Map<String, Object>> properties;

        public VertexProperties() {
            properties = new HashMap<>();
        }

        public <T> T put(V vertex, String propertyName, T property) {
            @SuppressWarnings("unchecked")
            Class<? extends T> tClass = (Class<? extends T>) property.getClass();
            return tClass.cast(getProperties(vertex).put(propertyName, property));
        }

        public <T> T get(V vertex, String propertyName, Class<T> tClass) {
            return tClass.cast(getProperties(vertex).get(propertyName));
        }

        public boolean visited(V vertex) {
            return Objects.equals(get(vertex, VISITED, Boolean.class), Boolean.TRUE);
        }

        public boolean markVisited(V vertex) {
            return Objects.equals(put(vertex, VertexProperties.VISITED, Boolean.TRUE), Boolean.TRUE);
        }

        public V getParent(V vertex) {
            @SuppressWarnings("unchecked")
            Class<? extends V> vClass = (Class<? extends V>) vertex.getClass();
            return get(vertex, PARENT, vClass);
        }

        public V setParent(V vertex, V parent) {
            return put(vertex, PARENT, parent);
        }

        public <L> L getDistance(V vertex, Class<? extends L> lClass) {
            return get(vertex, DISTANCE, lClass);
        }

        public <L> L setDistance(V vertex, L distance) {
            return put(vertex, DISTANCE, distance);
        }

        public <C> C getColor(V vertex, Class<? extends C> cClass) {
            return get(vertex, COLOR, cClass);
        }

        public <C> C setColor(V vertex, C color) {
            return put(vertex, COLOR, color);
        }

        private Map<String, Object> getProperties(V vertex) {
            if (!properties.containsKey(vertex)) {
                properties.put(vertex, new HashMap<>());
            }
            return properties.get(vertex);
        }
    }
}
