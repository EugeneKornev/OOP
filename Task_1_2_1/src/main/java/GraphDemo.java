import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Demonstration class for testing graph functionality.
 */
public class GraphDemo {

    /**
     * Main method demonstrating graph functionality.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        try {
            String filename = "graph.txt";
            List<String> lines = Arrays.asList(
                    "A B", "A C", "B D", "C D", "D E"
            );
            Files.write(Paths.get(filename), lines);

            Graph[] graphs = {
                new AdjacencyMatrixGraph(),
                new IncidenceMatrixGraph(),
                new AdjacencyListGraph()
            };

            for (Graph graph : graphs) {
                System.out.println("Testing " + graph.getClass().getSimpleName());

                graph.readFromFile(filename);

                System.out.println("Graph structure:");
                System.out.println(graph);

                System.out.println("Topological sort: " + graph.topologicalSort());
                System.out.println("Vertex count: " + graph.getVertexCount());
                System.out.println("Edge count: " + graph.getEdgeCount());
                System.out.println("Neighbors of A: " + graph.getNeighbors("A"));
                System.out.println("Has edge A->B: " + graph.hasEdge("A", "B"));
                System.out.println("Has edge B->A: " + graph.hasEdge("B", "A"));
                System.out.println("-------------------\n");
            }

            boolean allEqual = graphs[0].equals(graphs[1]) && graphs[1].equals(graphs[2]);
            System.out.println("All graphs are equal: " + allEqual);

        } catch (IOException e) {
            System.err.println("Error during demo execution: " + e.getMessage());
        }
    }
}