import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a directed edge in a graph from a source vertex to a destination vertex.
 */
class Edge {
    private final String source;
    private final String destination;

    /**
     * Constructs a new directed edge with the specified source and destination vertices.
     *
     * @param source the source vertex of the edge
     * @param destination the destination vertex of the edge
     */
    public Edge(String source, String destination) {
        this.source = source;
        this.destination = destination;
    }

    /**
     * Returns the source vertex of this edge.
     *
     * @return the source vertex,
     */
    public String getSource() {
        return source;
    }

    /**
     * Returns the destination vertex of this edge.
     *
     * @return the destination vertex
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Compares this edge to the specified object for equality.
     *
     * @param obj the object to compare this edge against
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Edge edge)) return false;
        return Objects.equals(source, edge.source) &&
                Objects.equals(destination, edge.destination);
    }

    /**
     * Returns a hash code.
     *
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(source, destination);
    }

    /**
     * Returns a string representation of this edge in the format "source -> destination".
     *
     * @return a string representation of this edge
     */
    @Override
    public String toString() {
        return source + " -> " + destination;
    }
}

/**
 * Interface for representing a directed graph.
 */
public interface Graph {

    /**
     * Adds a vertex to the graph. If the vertex already exists, the operation is ignored.
     *
     * @param vertex the vertex to be added to the graph
     */
    void addVertex(String vertex);

    /**
     * Removes a vertex from the graph along with all edges incident to it.
     * If the vertex does not exist in the graph, the operation is ignored
     *
     * @param vertex the vertex to be removed from the graph
     */
    void removeVertex(String vertex);

    /**
     * Adds a directed edge from the source vertex to the destination vertex.
     * If the edge already exists, the operation is ignored.
     *
     * @param source the source vertex of the edge
     * @param destination the destination vertex of the edge
     */
    void addEdge(String source, String destination);

    /**
     * Removes a directed edge between the specified vertices.
     * If the edge does not exist, the operation is ignored.
     *
     * @param source the source vertex of the edge to remove
     * @param destination the destination vertex of the edge to remove
     */
    void removeEdge(String source, String destination);

    /**
     * Returns a list of all neighbors of a vertex.
     *
     * @param vertex the vertex for which to find neighbors
     * @return a list of neighboring vertices
     */
    List<String> getNeighbors(String vertex);

    /**
     * Reads the graph from a file and replaces the current graph structure.
     *
     * @param filename the path to the file to read from
     * @throws IOException if an I/O error occurs while reading the file
     * @throws SecurityException if read access to the file is denied
     */
    void readFromFile(String filename) throws IOException;

    /**
     * Returns a set of all vertices currently in the graph.
     *
     * @return a set containing all vertices in the graph, empty if the graph has no vertices
     */
    Set<String> getVertices();

    /**
     * Returns a set of all edges currently in the graph.
     *
     * @return a set containing all edges in the graph, empty if the graph has no edges
     */
    Set<Edge> getEdges();

    /**
     * Returns the number of vertices currently in the graph.
     *
     * @return the number of vertices in the graph
     */
    int getVertexCount();

    /**
     * Returns the number of edges currently in the graph.
     *
     * @return the number of edges in the graph
     */
    int getEdgeCount();

    /**
     * Checks whether a vertex exists in the graph.
     *
     * @param vertex the vertex to check for existence in the graph
     * @return true if the vertex exists in the graph, false otherwise
     */
    boolean hasVertex(String vertex);

    /**
     * Checks whether a directed edge exists from the source vertex to the destination vertex.
     *
     * @param source the source vertex to check
     * @param destination the destination vertex to check
     * @return true if the edge exists, false if the edge doesn't exist or
     *         either vertex doesn't exist in the graph
     */
    boolean hasEdge(String source, String destination);

    /**
     * Performs topological sorting of the graph vertices using Kahn's algorithm.
     *
     * @return a list of vertices in topological order (linear ordering)
     * @throws IllegalArgumentException if the graph contains cycles
     */
    List<String> topologicalSort();

    /**
     * Compares this graph to the specified object for equality.
     *
     * @param obj the object to compare this graph against
     * @return true if the specified object is a graph with the same vertices and edges,
     *         false otherwise
     */
    @Override
    boolean equals(Object obj);

    /**
     * Returns a string representation of the graph.
     *
     * @return a string representation of the graph
     */
    @Override
    String toString();
}