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
}