import java.util.Objects;

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
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Edge edge)) {
            return false;
        }
        return Objects.equals(source, edge.source)
                && Objects.equals(destination, edge.destination);
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