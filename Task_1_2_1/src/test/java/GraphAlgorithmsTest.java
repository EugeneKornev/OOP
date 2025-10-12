import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

/**
 * Test class for GraphAlgorithms utility class.
 */
class GraphAlgorithmsTest {

    private Graph graph;

    @BeforeEach
    void setUp() {
        graph = new AdjacencyListGraph();
    }

    @Test
    void testTopologicalSortLinear() {
        // A -> B -> C -> D
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "D");

        List<String> result = GraphAlgorithms.topologicalSort(graph);

        assertEquals(Arrays.asList("A", "B", "C", "D"), result);
    }

    @Test
    void testTopologicalSortDiamond() {
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "D");
        graph.addEdge("C", "D");

        List<String> result = GraphAlgorithms.topologicalSort(graph);

        assertEquals(4, result.size());
        assertEquals("A", result.get(0));
        assertEquals("D", result.get(3));
        assertTrue(result.indexOf("B") < result.indexOf("D"));
        assertTrue(result.indexOf("C") < result.indexOf("D"));
    }

    @Test
    void testTopologicalSortMultipleRoots() {
        // A -> C, B -> C, B -> D
        graph.addEdge("A", "C");
        graph.addEdge("B", "C");
        graph.addEdge("B", "D");

        List<String> result = GraphAlgorithms.topologicalSort(graph);

        assertEquals(4, result.size());
        assertTrue(result.contains("A"));
        assertTrue(result.contains("B"));
        assertTrue(result.contains("C"));
        assertTrue(result.contains("D"));
        assertTrue(result.indexOf("A") < result.indexOf("C"));
        assertTrue(result.indexOf("B") < result.indexOf("C"));
        assertTrue(result.indexOf("B") < result.indexOf("D"));
    }

    @Test
    void testTopologicalSortCycle() {
        // A -> B -> C -> A (cycle)
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "A");

        assertThrows(IllegalArgumentException.class, () -> {
            GraphAlgorithms.topologicalSort(graph);
        });
    }

    @Test
    void testTopologicalSortSelfLoop() {
        // A -> A (self-loop)
        graph.addEdge("A", "A");

        assertThrows(IllegalArgumentException.class, () -> {
            GraphAlgorithms.topologicalSort(graph);
        });
    }

    @Test
    void testTopologicalSortEmptyGraph() {
        List<String> result = GraphAlgorithms.topologicalSort(graph);
        assertTrue(result.isEmpty());
    }

    @Test
    void testTopologicalSortSingleVertex() {
        graph.addVertex("A");

        List<String> result = GraphAlgorithms.topologicalSort(graph);

        assertEquals(1, result.size());
        assertEquals("A", result.get(0));
    }

    @Test
    void testTopologicalSortDisconnected() {
        // Component 1: A -> B
        // Component 2: C -> D
        graph.addEdge("A", "B");
        graph.addEdge("C", "D");

        List<String> result = GraphAlgorithms.topologicalSort(graph);

        assertEquals(4, result.size());
        assertTrue(result.indexOf("A") < result.indexOf("B"));
        assertTrue(result.indexOf("C") < result.indexOf("D"));
    }

    @Test
    void testNullGraph() {
        assertThrows(NullPointerException.class, () -> GraphAlgorithms.topologicalSort(null));
    }
}