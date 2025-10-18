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
}