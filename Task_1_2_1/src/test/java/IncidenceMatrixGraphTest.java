import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for IncidenceMatrixGraph implementation.

 */
class IncidenceMatrixGraphTest extends AbstractGraphTest {

    /**
     * Creates a new IncidenceMatrixGraph instance for testing.
     *
     * @return a new IncidenceMatrixGraph instance
     */
    @Override
    protected Graph createGraph() {
        return new IncidenceMatrixGraph();
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
}