import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for AdjacencyMatrixGraph implementation.
 */
class AdjacencyMatrixGraphTest extends AbstractGraphTest {

    /**
     * Creates a new AdjacencyMatrixGraph instance for testing.
     *
     * @return a new AdjacencyMatrixGraph instance
     */
    @Override
    protected Graph createGraph() {
        return new AdjacencyMatrixGraph();
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
}