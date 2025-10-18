import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

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

        assertEquals(2, neighborsA.size());
        assertTrue(neighborsA.contains("B"));
        assertTrue(neighborsA.contains("C"));

        List<String> neighborsB = graph.getNeighbors("B");

        assertEquals(1, neighborsB.size());
        assertTrue(neighborsB.contains("C"));

        List<String> neighborsC = graph.getNeighbors("C");

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
        List<String> lines = Arrays.asList("A B", "B C", "C D", "A C", "E", "F");
        Files.write(testFile, lines);

        graph.readFromFile(testFile.toString());

        assertEquals(6, graph.getVertexCount());
        assertEquals(4, graph.getEdgeCount());
        assertTrue(graph.hasEdge("A", "B"));
        assertTrue(graph.hasEdge("B", "C"));
        assertTrue(graph.hasEdge("C", "D"));
        assertTrue(graph.hasEdge("A", "C"));
        assertTrue(graph.hasVertex("E"));;
        assertTrue(graph.hasVertex("F"));;
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

    @Test
    void testVertexRemovalEfficiency() {
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "A");
        graph.addEdge("D", "A");

        graph.removeVertex("A");

        assertFalse(graph.hasVertex("A"));
        assertFalse(graph.hasEdge("A", "B"));
        assertFalse(graph.hasEdge("C", "A"));
        assertFalse(graph.hasEdge("D", "A"));
        assertTrue(graph.hasEdge("B", "C"));
    }

    @Test
    void testDuplicateEdgePrevention() {
        graph.addEdge("A", "B");
        graph.addEdge("A", "B");
        graph.addEdge("A", "B");

        assertEquals(1, graph.getEdgeCount());
        assertEquals(2, graph.getVertexCount());
    }


    @Test
    void testEdgeRemoval() {
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "A");

        assertEquals(3, graph.getEdgeCount());

        graph.removeEdge("B", "C");

        assertEquals(2, graph.getEdgeCount());
        assertFalse(graph.hasEdge("B", "C"));
        assertTrue(graph.hasEdge("A", "B"));
        assertTrue(graph.hasEdge("C", "A"));
    }

    @Test
    void testComplexEdgeScenarios() {
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "C");
        graph.addEdge("C", "D");
        graph.addEdge("D", "A");

        assertEquals(4, graph.getVertexCount());
        assertEquals(5, graph.getEdgeCount());

        assertThrows(IllegalArgumentException.class, () -> graph.topologicalSort());
    }

    @Test
    void testLargeGraph() {
        for (int i = 0; i < 10; i++) {
            graph.addVertex("V" + i);
        }

        for (int i = 0; i < 9; i++) {
            graph.addEdge("V" + i, "V" + (i + 1));
        }

        assertEquals(10, graph.getVertexCount());
        assertEquals(9, graph.getEdgeCount());
        assertTrue(graph.hasEdge("V0", "V1"));
        assertTrue(graph.hasEdge("V8", "V9"));
    }

    @Test
    void testMatrixEdgeCases() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");

        assertEquals(2, graph.getVertexCount());
        assertEquals(1, graph.getEdgeCount());

        graph.removeVertex("A");

        assertEquals(1, graph.getVertexCount());
        assertEquals(0, graph.getEdgeCount());
        assertFalse(graph.hasEdge("A", "B"));
    }

    @Test
    void testComplexMiddleVertexRemoval() {
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "D");
        graph.addEdge("A", "C");
        graph.addEdge("B", "D");
        graph.addEdge("C", "A");

        assertEquals(4, graph.getVertexCount());
        assertEquals(6, graph.getEdgeCount());

        graph.removeVertex("C");

        assertEquals(3, graph.getVertexCount());
        assertEquals(2, graph.getEdgeCount());

        assertTrue(graph.hasEdge("A", "B"));
        assertTrue(graph.hasEdge("B", "D"));
        assertFalse(graph.hasEdge("A", "C"));
        assertFalse(graph.hasEdge("B", "C"));
        assertFalse(graph.hasEdge("C", "D"));
        assertFalse(graph.hasEdge("C", "A"));

        assertEquals(List.of("B"), graph.getNeighbors("A"));
        assertEquals(List.of("D"), graph.getNeighbors("B"));
        assertEquals(List.of(), graph.getNeighbors("D"));
    }

    @Test
    void testEqualsImplementations() {
        List<Graph> implementations = Arrays.asList(
                new AdjacencyListGraph(),
                new AdjacencyMatrixGraph(),
                new IncidenceMatrixGraph()
        );
        for (int i = 0; i < implementations.size(); i++) {
            for (int j = 0 ; j < implementations.size(); j++) {
                Graph graph1 = implementations.get(i);
                Graph graph2 = implementations.get(j);

                graph1.addEdge("A", "B");
                graph1.addEdge("A", "C");
                graph1.addEdge("A", "D");
                graph1.addEdge("B", "C");
                graph1.addEdge("B", "D");
                graph1.addEdge("C", "D");

                graph2.addEdge("A", "B");
                graph2.addEdge("A", "C");
                graph2.addEdge("A", "D");
                graph2.addEdge("B", "C");
                graph2.addEdge("B", "D");
                graph2.addEdge("C", "D");

                assertEquals(graph1, graph2);
            }
        }

    }
}