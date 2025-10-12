import java.util.*;

/**
 * Utility class containing algorithms for working with graphs.
 */
class GraphAlgorithms {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private GraphAlgorithms() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Performs topological sorting of vertices in a directed acyclic graph (DAG)
     * using Kahn's algorithm.
     *
     * @param graph the graph to sort, must be a directed acyclic graph (DAG)
     * @return a list of vertices in topological order (linear ordering)
     * @throws IllegalArgumentException if the graph contains cycles
     * @throws NullPointerException if graph is null
     */
    public static List<String> topologicalSort(Graph graph) {
        Objects.requireNonNull(graph, "Graph cannot be null");

        Map<String, Integer> inDegree = new HashMap<>();

        for (String vertex : graph.getVertices()) {
            inDegree.put(vertex, 0);
        }

        for (Edge edge : graph.getEdges()) {
            String dest = edge.getDestination();
            inDegree.put(dest, inDegree.get(dest) + 1);
        }

        Queue<String> queue = new LinkedList<>();
        for (Map.Entry<String, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
        }

        List<String> result = new ArrayList<>();

        while (!queue.isEmpty()) {
            String vertex = queue.poll();
            result.add(vertex);

            for (String neighbor : graph.getNeighbors(vertex)) {
                int newInDegree = inDegree.get(neighbor) - 1;
                inDegree.put(neighbor, newInDegree);

                if (newInDegree == 0) {
                    queue.add(neighbor);
                }
            }
        }

        if (result.size() != graph.getVertexCount()) {
            throw new IllegalArgumentException(
                    "Graph has a cycle, topological sort not possible. " +
                            "Processed " + result.size() + " vertices out of " + graph.getVertexCount()
            );
        }

        return result;
    }
}