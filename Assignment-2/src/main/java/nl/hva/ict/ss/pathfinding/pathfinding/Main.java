package nl.hva.ict.ss.pathfinding.pathfinding;

import nl.hva.ict.ss.pathfinding.tileworld.TileWorldUtil;
import nl.hva.ict.ss.pathfinding.weigthedgraph.EdgeWeightedDigraph;

/**
 * TODO make sure your code is compliant with the HBO-ICT coding conventions!
 * @author ???
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        // TODO Here you can do your experiments.
    	
    	// Please have a good look at the constructors of EdgeWeightedDigraph!
    	
    	// Before you save any images make sure the value of TileWorldUtil.outputDir points to an
    	// existing folder and ands with a '/'!
    	// Example: TileWorldUtil.outputDir = "/Users/nico/output/";
        execute();
    }
    public static void execute() {
        long t1 = 0;
        long t2 = 0;
        long timeSpent = 0;
		// Make sure that it is writeable and we know where to look for it
		TileWorldUtil.OUTPUT = "C:/Users/Dominik/Desktop/her s&s/Assignment-2/src/main/resources/output/";
		System.out.printf("ID;Length Dijkstra;Length Floyd;Costs Dijkstra; Costs Floyd\n");
		for (int i = 1; i <= 24; i++) { // The following images are parsed into the report: 1,3,11,18,22,23,24
                    
                    // needed to measure time taken
                    t1 = System.nanoTime();
                    
			// Read the graph directly from a image
			EdgeWeightedDigraph graafDijkstra = new EdgeWeightedDigraph("i" + i);
			// Get the start and end node
			final int start = graafDijkstra.getStart();
			final int finish = graafDijkstra.getEnd();

			// Run dijkstra
			final Dijkstra dijkstra = new Dijkstra(graafDijkstra, start);
			if (dijkstra.hasPathTo(finish)) {
				// Show the result
				graafDijkstra.tekenPad(dijkstra.pathTo(finish));
				// Save it
				graafDijkstra.save("i" + i + "-dijkstra");
			}
                        
                    t2 = System.nanoTime();
                    timeSpent = t2-t1;
                    System.out.println("Dijkstra Time: " + timeSpent);
                    t1 = System.nanoTime();
                    
			// Run Floyd-Warshall
            EdgeWeightedDigraph graafFloyd = new EdgeWeightedDigraph("i" + i);
			FloydWarshall floyd = new FloydWarshall(graafFloyd.createAdjMatrixEdgeWeightedDigraph());
			if (floyd.hasPath(start, finish)) {
				// Show the result
                graafFloyd.tekenPad(floyd.path(start, finish));
				// Save it
                graafFloyd.save("i" + i + "-floyd");
			}
                        
                        t2 = System.nanoTime();
                        timeSpent = t2-t1;
                        System.out.println("Floyd Time: " + timeSpent);
                        
			if (dijkstra.hasPathTo(finish)) {
                            System.out.println("Visited nodes (Dijkstra): " + dijkstra.getNodes());
                            System.out.println("Visited nodes (Floyd): " + floyd.getNodes());
                            System.out.printf("i%d;%d;%d;%1.0f;%1.0f\n", i, length(dijkstra.pathTo(finish)), length(floyd.path(start, finish)), dijkstra.distTo(finish), floyd.dist(start, finish));
            } else {
                System.out.printf("i%d;-;-;-;-\n", i);
            }
		}
	}

	private static <T> int length(Iterable<T> iterable) {
	    int length = 0;
	    for (T notNeeded : iterable) {
	        length++;
        }
	    return length;
    }
    
    
}
