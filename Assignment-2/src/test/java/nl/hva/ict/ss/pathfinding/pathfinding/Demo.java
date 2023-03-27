package nl.hva.ict.ss.pathfinding.pathfinding;

import nl.hva.ict.ss.pathfinding.tileworld.TileWorldUtil;
import nl.hva.ict.ss.pathfinding.weigthedgraph.EdgeWeightedDigraph;
import org.junit.Test;

public class Demo {

	@Test
	public void test() {
		// Make sure that it is writeable and we know where to look for it
		TileWorldUtil.OUTPUT = "C:/Users/Dominik/Desktop/her s&s/Assignment-2/src/main/resources/output/";
		System.out.printf("ID;Length Dijkstra;Length Floyd;Costs Dijkstra; Costs Floyd\n");
		for (int i = 1; i <= 24; i++) {
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

			// Run Floyd-Warshall
            EdgeWeightedDigraph graafFloyd = new EdgeWeightedDigraph("i" + i);
			FloydWarshall floyd = new FloydWarshall(graafFloyd.createAdjMatrixEdgeWeightedDigraph());
			if (floyd.hasPath(start, finish)) {
				// Show the result
                graafFloyd.tekenPad(floyd.path(start, finish));
				// Save it
                graafFloyd.save("i" + i + "-floyd");
			}
			if (dijkstra.hasPathTo(finish)) {
                            System.out.println("Floyd nodes: "+floyd.getNodes()+" Dijkstra nodes: "+dijkstra.getNodes());
                System.out.printf("i%d;%d;%d;%1.0f;%1.0f\n", i, length(dijkstra.pathTo(finish)), length(floyd.path(start, finish)), dijkstra.distTo(finish), floyd.dist(start, finish));
            } else {
                System.out.printf("i%d;-;-;-;-\n", i);
            }
		}
	}

	private <T> int length(Iterable<T> iterable) {
	    int length = 0;
	    for (T notNeeded : iterable) {
	        length++;
        }
	    return length;
    }

}
