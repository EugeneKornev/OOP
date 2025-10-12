import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for AdjacencyListGraph implementation.
 */
class AdjacencyListGraphTest extends AbstractGraphTest {

    /**
     * Creates a new AdjacencyListGraph instance for testing.
     *
     * @return a new AdjacencyListGraph instance
     */
    @Override
    protected Graph createGraph() {
        return new AdjacencyListGraph();
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
        // B and C should still have their edge
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
}