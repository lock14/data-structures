package lock14.datastructures;

import java.util.Queue;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;
import java.util.Objects;

// TODO: switch all auxiliary data-structures over to my implementations
//       once they are finished.
public final class Graphs {

    // make utility class non-instantiable
    private Graphs() {
    }

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
}
