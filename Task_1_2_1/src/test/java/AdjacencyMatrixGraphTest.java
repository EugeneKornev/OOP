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
}