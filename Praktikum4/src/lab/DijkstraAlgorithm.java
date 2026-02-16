package lab;

import java.util.Collections;
import java.util.Comparator;

public class DijkstraAlgorithm {

	@SuppressWarnings("unchecked")
	public static void distanceDijkstraAlgorithm(Vertex n) {
		n.finished = true;
		if (n.edges.size() == 0) {
			return;
		}
		for (Edge k : n.edges) {
			Vertex neighbour = k.destination;
			if (neighbour.visited == false) {
				neighbour.visited = true;
				neighbour.attribute = k.getDistance() + k.source.attribute;
				neighbour.predecessor = k;
			}
			if (neighbour.visited == true) {
				double alternative = k.getDistance() + k.source.attribute;
				if (alternative < neighbour.attribute) {
					neighbour.attribute = alternative;
					neighbour.predecessor = k;
				}
			}
		}
		for (Edge k : n.edges) {
			Vertex neighbour = k.destination;
			Comparator c = new DistanceComparator();
			Collections.sort(n.edges, c);
			if (!neighbour.determineFinishedStatus()) {
				distanceDijkstraAlgorithm(neighbour);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void timeDijkstraAlgorithm(Vertex n) {
		n.finished = true;
		for (Edge k : n.edges) {
			Vertex neighbour = k.destination;
			if (!neighbour.visited) {
				neighbour.visited = true;
				neighbour.attribute = k.source.getWaitingTime() + k.getTravelTime() + k.source.attribute;
				neighbour.predecessor = k;
			}
			if (neighbour.visited) {
				double alternative = k.source.getWaitingTime() + k.getTravelTime() + k.source.attribute;
				if (alternative < neighbour.attribute) {
					neighbour.attribute = alternative;
					neighbour.predecessor = k;
				}
			}
		}
		for (Edge k : n.edges) {
			Comparator t = new TimeComparator();
			Collections.sort(n.edges, t);
			Vertex neighbour = k.destination;
			if (!neighbour.determineFinishedStatus()) {
				timeDijkstraAlgorithm(neighbour);
			}
		}
	}
}
