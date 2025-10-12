import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Abstract base class for testing Graph implementations.
 */
abstract class AbstractGraphTest {

    protected Graph graph;

    /**
     * Creates a new instance of the Graph implementation to be tested.
     *
     * @return a new Graph instance for testing
     */
    protected abstract Graph createGraph();

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        graph = createGraph();
    }

    @Test
    void testAddVertex() {
        graph.addVertex("A");
        graph.addVertex("B");

        assertTrue(graph.hasVertex("A"));
        assertTrue(graph.hasVertex("B"));
        assertFalse(graph.hasVertex("C"));
        assertEquals(2, graph.getVertexCount());
    }

    @Test
    void testAddDuplicateVertex() {
        graph.addVertex("A");
        graph.addVertex("A");

        assertEquals(1, graph.getVertexCount());
        assertTrue(graph.hasVertex("A"));
    }

    @Test
    void testRemoveVertex() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");

        graph.removeVertex("B");

        assertTrue(graph.hasVertex("A"));
        assertFalse(graph.hasVertex("B"));
        assertTrue(graph.hasVertex("C"));
        assertEquals(2, graph.getVertexCount());
    }

    @Test
    void testRemoveVertexWithEdges() {
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("A", "C");

        graph.removeVertex("B");

        assertFalse(graph.hasEdge("A", "B"));
        assertFalse(graph.hasEdge("B", "C"));
        assertTrue(graph.hasEdge("A", "C"));
        assertEquals(2, graph.getVertexCount());
    }

    @Test
    void testAddEdge() {
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");

        assertTrue(graph.hasEdge("A", "B"));
        assertTrue(graph.hasEdge("B", "C"));
        assertFalse(graph.hasEdge("A", "C"));
        assertEquals(3, graph.getVertexCount());
        assertEquals(2, graph.getEdgeCount());
    }

    @Test
    void testAddDuplicateEdge() {
        graph.addEdge("A", "B");
        graph.addEdge("A", "B");

        assertTrue(graph.hasEdge("A", "B"));
        assertEquals(1, graph.getEdgeCount());
    }

    @Test
    void testRemoveEdge() {
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");

        graph.removeEdge("A", "B");

        assertFalse(graph.hasEdge("A", "B"));
        assertTrue(graph.hasEdge("B", "C"));
        assertEquals(1, graph.getEdgeCount());
        assertEquals(3, graph.getVertexCount());
    }

    @Test
    void testGetNeighbors() {
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "C");

        List<String> neighborsA = graph.getNeighbors("A");
        List<String> neighborsB = graph.getNeighbors("B");
        List<String> neighborsC = graph.getNeighbors("C");

        assertEquals(2, neighborsA.size());
        assertTrue(neighborsA.contains("B"));
        assertTrue(neighborsA.contains("C"));

        assertEquals(1, neighborsB.size());
        assertTrue(neighborsB.contains("C"));

        assertEquals(0, neighborsC.size());
    }

    @Test
    void testGetNeighborsNonExistentVertex() {
        List<String> neighbors = graph.getNeighbors("X");
        assertNotNull(neighbors);
        assertTrue(neighbors.isEmpty());
    }

    @Test
    void testReadFromFile() throws IOException {
        Path testFile = tempDir.resolve("test_graph.txt");
        List<String> lines = Arrays.asList("A B", "B C", "C D", "A C");
        Files.write(testFile, lines);

        graph.readFromFile(testFile.toString());

        assertEquals(4, graph.getVertexCount());
        assertEquals(4, graph.getEdgeCount());
        assertTrue(graph.hasEdge("A", "B"));
        assertTrue(graph.hasEdge("B", "C"));
        assertTrue(graph.hasEdge("C", "D"));
        assertTrue(graph.hasEdge("A", "C"));
    }

    @Test
    void testReadFromEmptyFile() throws IOException {
        Path emptyFile = tempDir.resolve("empty.txt");
        Files.createFile(emptyFile);

        graph.readFromFile(emptyFile.toString());

        assertEquals(0, graph.getVertexCount());
        assertEquals(0, graph.getEdgeCount());
    }

    @Test
    void testGetVertices() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");

        Set<String> vertices = graph.getVertices();

        assertEquals(3, vertices.size());
        assertTrue(vertices.contains("A"));
        assertTrue(vertices.contains("B"));
        assertTrue(vertices.contains("C"));
    }

    @Test
    void testGetEdges() {
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");

        Set<Edge> edges = graph.getEdges();

        assertEquals(2, edges.size());
        assertTrue(edges.contains(new Edge("A", "B")));
        assertTrue(edges.contains(new Edge("B", "C")));
    }

    @Test
    void testGraphEquality() {
        Graph graph1 = createGraph();
        Graph graph2 = createGraph();

        graph1.addEdge("A", "B");
        graph1.addEdge("B", "C");

        graph2.addEdge("A", "B");
        graph2.addEdge("B", "C");

        assertEquals(graph1, graph2);
        assertEquals(graph2, graph1);
    }

    @Test
    void testGraphInequalityDifferentEdges() {
        Graph graph1 = createGraph();
        Graph graph2 = createGraph();

        graph1.addEdge("A", "B");
        graph1.addEdge("B", "C");

        graph2.addEdge("A", "B");
        graph2.addEdge("C", "D");

        assertNotEquals(graph1, graph2);
    }

    @Test
    void testGraphInequalityDifferentVertices() {
        Graph graph1 = createGraph();
        Graph graph2 = createGraph();

        graph1.addEdge("A", "B");
        graph1.addEdge("B", "C");

        graph2.addEdge("A", "B");
        graph2.addEdge("B", "C");
        graph2.addVertex("D");

        assertNotEquals(graph1, graph2);
    }

    @Test
    void testTopologicalSort() {
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("A", "C");

        List<String> sorted = graph.topologicalSort();

        assertEquals(3, sorted.size());
        assertTrue(sorted.indexOf("A") < sorted.indexOf("B"));
        assertTrue(sorted.indexOf("B") < sorted.indexOf("C"));
        assertTrue(sorted.indexOf("A") < sorted.indexOf("C"));
    }

    @Test
    void testTopologicalSortSingleVertex() {
        graph.addVertex("A");

        List<String> sorted = graph.topologicalSort();

        assertEquals(1, sorted.size());
        assertEquals("A", sorted.get(0));
    }

    @Test
    void testTopologicalSortDisconnected() {
        graph.addEdge("A", "B");
        graph.addVertex("C");
        graph.addVertex("D");

        List<String> sorted = graph.topologicalSort();

        assertEquals(4, sorted.size());
        assertTrue(sorted.contains("A"));
        assertTrue(sorted.contains("B"));
        assertTrue(sorted.contains("C"));
        assertTrue(sorted.contains("D"));
        assertTrue(sorted.indexOf("A") < sorted.indexOf("B"));
    }

    @Test
    void testToString() {
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");

        assertNotNull(graph.toString());
        assertFalse(graph.toString().isEmpty());
    }
}