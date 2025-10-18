import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Graph implementation using an adjacency list.
 */
class AdjacencyListGraph implements Graph {
    private final Map<String, List<String>> adjacencyList;

    /**
     * Constructs an empty graph with an empty adjacency list.
     */
    public AdjacencyListGraph() {
        adjacencyList = new HashMap<>();
    }

    /**
     * Adds a vertex to the graph. If the vertex already exists, the operation is ignored.
     *
     * @param vertex the vertex to be added to the graph
     */
    @Override
    public void addVertex(String vertex) {
        adjacencyList.putIfAbsent(vertex, new ArrayList<>());
    }

    /**
     * Removes a vertex from the graph along with all edges incident to it.
     * If the vertex does not exist in the graph, the operation is ignored.
     *
     * @param vertex the vertex to be removed from the graph
     */
    @Override
    public void removeVertex(String vertex) {
        for (List<String> neighbors : adjacencyList.values()) {
            neighbors.remove(vertex);
        }
        adjacencyList.remove(vertex);
    }

    /**
     * Adds a directed edge from the source vertex to the destination vertex.
     * If the edge already exists, the operation is ignored.
     *
     * @param source the source vertex of the edge
     * @param destination the destination vertex of the edge
     */
    @Override
    public void addEdge(String source, String destination) {
        addVertex(source);
        addVertex(destination);
        if (!adjacencyList.get(source).contains(destination)) {
            adjacencyList.get(source).add(destination);
        }
    }

    /**
     * Removes a directed edge between the specified vertices.
     * If the edge does not exist, the operation is ignored.
     *
     * @param source the source vertex of the edge to remove
     * @param destination the destination vertex of the edge to remove
     */
    @Override
    public void removeEdge(String source, String destination) {
        if (adjacencyList.containsKey(source)) {
            adjacencyList.get(source).remove(destination);
        }
    }

    /**
     * Returns a list of all neighbors of a vertex.
     *
     * @param vertex the vertex for which to find neighbors
     * @return a list of neighboring vertices, empty if the vertex has no neighbors
     *         or does not exist in the graph
     */
    @Override
    public List<String> getNeighbors(String vertex) {
        return adjacencyList.getOrDefault(vertex, new ArrayList<>());
    }

    /**
     * Reads the graph from a file and replaces the current graph structure.
     *
     * @param filename the path to the file to read from
     * @throws IOException if an I/O error occurs while reading the file
     * @throws SecurityException if read access to the file is denied
     */
    @Override
    public void readFromFile(String filename) throws IOException {
        Objects.requireNonNull(filename, "Filename cannot be null");
        List<String> lines = Files.readAllLines(Paths.get(filename));
        adjacencyList.clear();

        for (String line : lines) {
            if (line.trim().isEmpty()) {
                continue;
            }
            String[] parts = line.split("\\s+");
            if (parts.length == 2) {
                addEdge(parts[0], parts[1]);
            } else if (parts.length == 1) {
                addVertex(parts[0]);
            }
        }
    }

    /**
     * Returns a set of all vertices currently in the graph.
     *
     * @return a set containing all vertices in the graph, empty if the graph has no vertices
     */
    @Override
    public Set<String> getVertices() {
        return adjacencyList.keySet();
    }

    /**
     * Returns a set of all edges currently in the graph.
     *
     * @return a set containing all edges in the graph, empty if the graph has no edges
     */
    @Override
    public Set<Edge> getEdges() {
        Set<Edge> edges = new HashSet<>();
        for (Map.Entry<String, List<String>> entry : adjacencyList.entrySet()) {
            for (String neighbor : entry.getValue()) {
                edges.add(new Edge(entry.getKey(), neighbor));
            }
        }
        return edges;
    }

    /**
     * Returns the number of vertices currently in the graph.
     *
     * @return the number of vertices in the graph
     */
    @Override
    public int getVertexCount() {
        return adjacencyList.size();
    }

    /**
     * Returns the number of edges currently in the graph.
     *
     * @return the number of edges in the graph
     */
    @Override
    public int getEdgeCount() {
        return (int) adjacencyList.values().stream()
                .mapToInt(List::size)
                .sum();
    }

    /**
     * Checks whether a vertex exists in the graph.
     *
     * @param vertex the vertex to check for existence in the graph
     * @return true if the vertex exists in the graph, false otherwise
     */
    @Override
    public boolean hasVertex(String vertex) {
        return adjacencyList.containsKey(vertex);
    }

    /**
     * Checks whether a directed edge exists from the source vertex to the destination vertex.
     *
     * @param source the source vertex to check
     * @param destination the destination vertex to check
     * @return true if the edge exists, false if the edge doesn't exist or
     *         either vertex doesn't exist in the graph
     */
    @Override
    public boolean hasEdge(String source, String destination) {
        return adjacencyList.containsKey(source)
                && adjacencyList.get(source).contains(destination);
    }

    /**
     * Performs topological sorting of the graph vertices using Kahn's algorithm.
     *
     * @return a list of vertices in topological order (linear ordering)
     * @throws IllegalArgumentException if the graph contains cycles
     */
    @Override
    public List<String> topologicalSort() {
        return GraphAlgorithms.topologicalSort(this);
    }

    /**
     * Compares this graph to the specified object for equality.
     *
     * @param obj the object to compare this graph against
     * @return true if the specified object is a graph with the same vertices and edges,
     *         false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Graph)) {
            return false;
        }
        Graph other = (Graph) obj;
        return this.getVertices().equals(other.getVertices())
                && this.getEdges().equals(other.getEdges());
    }

    /**
     * Returns a string representation of the graph.
     *
     * @return a string representation of the graph
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AdjacencyListGraph:\n");
        for (Map.Entry<String, List<String>> entry : adjacencyList.entrySet()) {
            sb.append(entry.getKey()).append(" -> ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }
}