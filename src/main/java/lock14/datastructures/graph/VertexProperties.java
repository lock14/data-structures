package lock14.datastructures.graph;

import lock14.datastructures.Pair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Class to store and retrieve properties of vertices. Useful when performing various Graph
 * Algorithms
 *
 * @param <V> the vertex Type
 */
public class VertexProperties<V> {
    public static final String COLOR = "color";
    public static final String DISTANCE = "cost";
    public static final String PARENT = "parent";
    public static final String VISITED = "visited";

    private Class<?> cClass;
    private Class<?> lClass;

    private java.util.Map<V, java.util.Map<String, Object>> properties;

    public VertexProperties() {
        properties = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    public <T> T put(V vertex, String propertyName, T property) {
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

    @SuppressWarnings("unchecked")
    public V getParent(V vertex) {
        Class<? extends V> vClass = (Class<? extends V>) vertex.getClass();
        return get(vertex, PARENT, vClass);
    }

    public V setParent(V vertex, V parent) {
        return put(vertex, PARENT, parent);
    }

    @SuppressWarnings("unchecked")
    public <L> L getDistance(V vertex) {
        return lClass == null ? null : get(vertex, DISTANCE, (Class<? extends L>) lClass);
    }

    public <L> L setDistance(V vertex, L distance) {
        lClass = distance.getClass();
        return put(vertex, DISTANCE, distance);
    }

    @SuppressWarnings("unchecked")
    public <C> C getColor(V vertex) {
        return cClass == null ? null : get(vertex, COLOR, (Class<? extends C>) cClass);
    }

    public <C> C setColor(V vertex, C color) {
        cClass = color.getClass();
        return put(vertex, COLOR, color);
    }

    public void forEachParent(BiConsumer<V, V> consumer) {
        forEachProperty(this::getParent, consumer);
    }

    public <L> void forEachDistance(BiConsumer<V, L> consumer) {
        forEachProperty(this::getDistance, consumer);
    }

    public <C> void forEachColor(BiConsumer<V, C> consumer) {
        forEachProperty(this::getColor, consumer);
    }

    public <T> void forEachProperty(Function<V, T> propertyGetter, BiConsumer<V, T> consumer) {
        properties.keySet()
                  .stream()
                  .map(v -> Pair.of(v, propertyGetter.apply(v)))
                  .filter(pair -> pair.second() != null)
                  .forEach(pair -> consumer.accept(pair.first(), pair.second()));
    }

    public List<V> constructParentPath(V end) {
        LinkedList<V> stack = new LinkedList<>();
        V cur = end;
        while (this.getParent(cur) != null) {
            stack.push(cur);
            cur = this.getParent(cur);
        }
        stack.push(cur);
        return stack;
    }

    public <L> LabeledGraph<V, L> constructParentTree(LabeledGraph<V, L> graph) {
        LabeledGraph<V, L> tree = (LabeledGraph<V, L>) graph.emptyGraph();
        this.forEachParent((v, u) -> tree.addEdge(u, v, graph.label(u, v)));
        return tree;
    }

    private Map<String, Object> getProperties(V vertex) {
        if (!properties.containsKey(vertex)) {
            properties.put(vertex, new HashMap<>());
        }
        return properties.get(vertex);
    }
}
