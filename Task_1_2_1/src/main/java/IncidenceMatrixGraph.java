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
 * Graph implementation using an incidence matrix.
 */
class IncidenceMatrixGraph implements Graph {
    private final Map<String, Integer> vertexIndex;
    private final List<String> vertices;
    private final List<Edge> edges;
    private int[][] matrix;

    /**
     * Constructs an empty graph with a zero-sized incidence matrix.
     */
    public IncidenceMatrixGraph() {
        vertexIndex = new HashMap<>();
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        matrix = new int[0][0];
    }

    /**
     * Adds a vertex to the graph. If the vertex already exists, the operation is ignored.
     *
     * @param vertex the vertex to be added to the graph
     */
    @Override
    public void addVertex(String vertex) {
        if (!vertexIndex.containsKey(vertex)) {
            vertexIndex.put(vertex, vertices.size());
            vertices.add(vertex);
            resizeMatrix();
        }
    }

    /**
     * Removes a vertex from the graph along with all edges incident to it.
     *
     * @param vertex the vertex to be removed from the graph
     */
    @Override
    public void removeVertex(String vertex) {
        if (vertexIndex.containsKey(vertex)) {
            int index = vertexIndex.get(vertex);

            List<Edge> edgesToRemove = new ArrayList<>();
            for (Edge edge : edges) {
                if (edge.getSource().equals(vertex) || edge.getDestination().equals(vertex)) {
                    edgesToRemove.add(edge);
                }
            }
            for (Edge edge : edgesToRemove) {
                removeEdge(edge.getSource(), edge.getDestination());
            }

            vertexIndex.remove(vertex);
            vertices.remove(index);

            vertexIndex.clear();
            for (int i = 0; i < vertices.size(); i++) {
                vertexIndex.put(vertices.get(i), i);
            }
            resizeMatrix();
        }
    }

    /**
     * Resizes the incidence matrix when vertices or edges are added or removed.
     */
    private void resizeMatrix() {
        int vertexCount = vertices.size();
        int edgeCount = edges.size();
        int[][] newMatrix = new int[vertexCount][edgeCount];

        for (int i = 0; i < Math.min(vertexCount, matrix.length); i++) {
            System.arraycopy(matrix[i], 0, newMatrix[i], 0, Math.min(edgeCount, matrix[i].length));
        }

        for (int j = 0; j < edgeCount; j++) {
            Edge edge = edges.get(j);
            int srcIndex = vertexIndex.get(edge.getSource());
            int destIndex = vertexIndex.get(edge.getDestination());
            newMatrix[srcIndex][j] = 1;
            newMatrix[destIndex][j] = -1;
        }

        matrix = newMatrix;
    }

    /**
     * Adds a directed edge from the source vertex to the destination vertex.
     *
     * @param source the source vertex of the edge
     * @param destination the destination vertex of the edge
     */
    @Override
    public void addEdge(String source, String destination) {
        addVertex(source);
        addVertex(destination);

        Edge edge = new Edge(source, destination);
        if (!edges.contains(edge)) {
            edges.add(edge);
            resizeMatrix();
        }
    }

    /**
     * Removes a directed edge between the specified vertices.
     *
     * @param source the source vertex of the edge to remove
     * @param destination the destination vertex of the edge to remove
     */
    @Override
    public void removeEdge(String source, String destination) {
        Edge edge = new Edge(source, destination);
        if (edges.remove(edge)) {
            resizeMatrix();
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
        List<String> neighbors = new ArrayList<>();
        if (vertexIndex.containsKey(vertex)) {
            int vertexIdx = vertexIndex.get(vertex);
            for (int j = 0; j < edges.size(); j++) {
                if (matrix[vertexIdx][j] == 1) {
                    Edge edge = edges.get(j);
                    neighbors.add(edge.getDestination());
                }
            }
        }
        return neighbors;
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
        vertexIndex.clear();
        vertices.clear();
        edges.clear();

        for (String line : lines) {
            String[] parts = line.split("\\s+");
            if (parts.length == 2) {
                addEdge(parts[0], parts[1]);
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
        return new HashSet<>(vertices);
    }

    /**
     * Returns a set of all edges currently in the graph.
     *
     * @return a set containing all edges in the graph, empty if the graph has no edges
     */
    @Override
    public Set<Edge> getEdges() {
        return new HashSet<>(edges);
    }

    /**
     * Returns the number of vertices currently in the graph.
     *
     * @return the number of vertices in the graph
     */
    @Override
    public int getVertexCount() {
        return vertices.size();
    }

    /**
     * Returns the number of edges currently in the graph.
     *
     * @return the number of edges in the graph
     */
    @Override
    public int getEdgeCount() {
        return edges.size();
    }

    /**
     * Checks whether a vertex exists in the graph.
     *
     * @param vertex the vertex to check for existence in the graph
     * @return true if the vertex exists in the graph, false otherwise
     */
    @Override
    public boolean hasVertex(String vertex) {
        return vertexIndex.containsKey(vertex);
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
        return edges.contains(new Edge(source, destination));
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
     * Compares this graph to the specified object for equality.y.
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
        sb.append("IncidenceMatrixGraph:\n");
        sb.append("Vertices: ").append(vertices).append("\n");
        sb.append("Edges: ").append(edges).append("\n");
        return sb.toString();
    }
}