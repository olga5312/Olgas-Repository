package lab;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * The class Navigation finds the shortest (and/or) path between points on a map
 * using the Dijkstra algorithm
 */
public class Navigation {
	/**
	 * Return codes: -1 if the source is not on the map -2 if the destination is not
	 * on the map -3 if both source and destination points are not on the map -4 if
	 * no path can be found between source and destination
	 */

	public static final int SOURCE_NOT_FOUND = -1;
	public static final int DESTINATION_NOT_FOUND = -2;
	public static final int SOURCE_DESTINATION_NOT_FOUND = -3;
	public static final int NO_PATH = -4;

	private Graph graph;
	List<Vertex> vertices;
	List<Edge> edges;

	/**
	 * The constructor takes a filename as input, it reads that file and fill the
	 * nodes and edges Lists with corresponding node and edge objects
	 * 
	 * @param filename
	 *            name of the file containing the input map
	 */

	public Navigation(String filename) {
		vertices = new LinkedList<Vertex>();
		edges = new LinkedList<Edge>();
		try {
			BufferedReader buffRead = new BufferedReader(new FileReader(filename));
			String line = buffRead.readLine();
			while ((line = buffRead.readLine()) != null) {
				String[] parts = line.split("->");
				if (line.contains("=") && parts.length == 1) {
					String[] s = line.split("\"");
					String[] data = s[1].split(",");
					String name = data[0];
					int maxV = Integer.parseInt(data[1]);
					Vertex node = new Vertex(name, maxV);
					vertices.add(node);
				}
			}
			buffRead.close();
			buffRead = new BufferedReader(new FileReader(filename));
			while ((line = buffRead.readLine()) != null) {
				String[] parts = line.split("->");
				if (line.contains("=") && parts.length == 2) {
					String[] s = parts[1].split("\"");
					String[] data = s[1].split(",");
					int distance = Integer.parseInt(data[0]);
					int maxV = Integer.parseInt(data[1]);
					String orig = parts[0].trim();
					String[] desti = parts[1].split("\\[");
					String dest = desti[0].trim();
					Vertex origin = findVertex(orig);
					Vertex destination = findVertex(dest);
					Edge k = new Edge(origin, destination, distance, maxV);
					edges.add(k);
					origin.edges.add(k);
				}
			}
			buffRead.close();
		} catch (FileNotFoundException message) {
			message.printStackTrace();
		} catch (NumberFormatException | IOException message) {
			message.printStackTrace();
		}
		graph = new Graph(vertices, edges);
	}

	/**
	 * This methods finds the shortest route (distance) between points A and B on
	 * the map given in the constructor.
	 * 
	 * If a route is found the return value is an object of type ArrayList<String>,
	 * where every element is a String representing one line in the map. The output
	 * map is identical to the input map, apart from that all edges on the shortest
	 * route are marked "bold". It is also possible to output a map where all
	 * shortest paths starting in A are marked bold.
	 * 
	 * The order of the edges as they appear in the output may differ from the
	 * input.
	 * 
	 * @param A
	 *            Source
	 * @param B
	 *            Destinaton
	 * @return returns a map as described above if A or B is not on the map or if
	 *         there is no path between them the original map is to be returned.
	 */

	public Vertex findVertex(String name) {
		for (Vertex vertex : vertices) {
			if (vertex.getName().equals(name)) {
				return vertex;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<String> findShortestRoute(String A, String B) {
		Vertex start = findVertex(A);
		Vertex end = findVertex(B);
		if (start == null || end == null) {
			return print();
		}
		startAlgorithm(start);
		for (Edge k : start.edges) {
			k.destination.attribute = k.getDistance();
			k.destination.visited = true;
			k.destination.predecessor = k;
		}
		Comparator c = new DistanceComparator();
		Collections.sort(start.edges, c);
		for (Edge k : start.edges) {
			DijkstraAlgorithm.distanceDijkstraAlgorithm(k.destination);
		}
		Edge route = end.predecessor;
		while (route != null) {
			route.onRoute = true;
			route = route.source.predecessor;
		}
		return print();
	}

	public ArrayList<String> print() {
		ArrayList<String> resultMap = new ArrayList<String>();
		resultMap.add("Digraph {");
		for (Edge e : graph.getEdges()) {
			resultMap.add(e.toString());
		}
		for (Vertex v : graph.getVertices()) {
			resultMap.add(v.toString());
		}
		resultMap.add("}");
		return resultMap;
	}

	public void initialize() {
		for (Edge e : graph.getEdges()) {
			e.onRoute = false;
		}
		for (Vertex v : graph.getVertices()) {
			v.finished = false;
			v.visited = false;
			v.attribute = Integer.MAX_VALUE;
			v.predecessor = null;
		}
	}

	/**
	 * This methods finds the fastest route (in time) between points A and B on the
	 * map given in the constructor.
	 * 
	 * If a route is found the return value is an object of type ArrayList<String>,
	 * where every element is a String representing one line in the map. The output
	 * map is identical to the input map, apart from that all edges on the shortest
	 * route are marked "bold". It is also possible to output a map where all
	 * shortest paths starting in A are marked bold.
	 * 
	 * The order of the edges as they appear in the output may differ from the
	 * input.
	 * 
	 * @param A
	 *            Source
	 * @param B
	 *            Destinaton
	 * @return returns a map as described above if A or B is not on the map or if
	 *         there is no path between them the original map is to be returned.
	 */

	public void startAlgorithm(Vertex source) {
		initialize();
		source.attribute = 0;
		source.visited = true;
		source.finished = true;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<String> findFastestRoute(String A, String B) {
		Vertex start = findVertex(A);
		Vertex end = findVertex(B);
		if (start == null || end == null) {
			return print();
		}
		startAlgorithm(start);
		for (Edge k : start.edges) {
			k.destination.attribute = k.getTravelTime();
			k.destination.visited = true;
			k.destination.predecessor = k;
		}
		Comparator t = new TimeComparator();
		Collections.sort(start.edges, t);
		for (Edge k : start.edges) {
			DijkstraAlgorithm.timeDijkstraAlgorithm(k.destination);
		}
		Edge route = end.predecessor;
		while (route != null) {
			route.onRoute = true;
			route = route.source.predecessor;
		}
		return print();
	}

	/**
	 * Finds the shortest distance in kilometers between A and B using the Dijkstra
	 * algorithm.
	 * 
	 * @param A
	 *            the start point A
	 * @param B
	 *            the destination point B
	 * @return the shortest distance in kilometers rounded upwards. SOURCE_NOT_FOUND
	 *         if point A is not on the map DESTINATION_NOT_FOUND if point B is not
	 *         on the map SOURCE_DESTINATION_NOT_FOUND if point A and point B are
	 *         not on the map NO_PATH if no path can be found between point A and
	 *         point B
	 */

	public int findShortestDistance(String A, String B) {
		Vertex start = findVertex(A);
		Vertex end = findVertex(B);
		if (start == null && end != null) {
			return -1;
		}
		if (end == null && start != null) {
			return -2;
		}
		if (start == null && end == null) {
			return -3;
		}
		if (A == B) {
			return 0;
		}
		findShortestRoute(A, B);
		if (end.attribute == Integer.MAX_VALUE) {
			return -4;
		}
		return (int) Math.ceil(end.attribute);
	}

	/**
	 * Find the fastest route between A and B using the dijkstra algorithm.
	 * 
	 * @param A
	 *            Source
	 * @param B
	 *            Destination
	 * @return the fastest time in minutes rounded upwards. SOURCE_NOT_FOUND if
	 *         point A is not on the map DESTINATION_NOT_FOUND if point B is not on
	 *         the map SOURCE_DESTINATION_NOT_FOUND if point A and point B are not
	 *         on the map NO_PATH if no path can be found between point A and point
	 *         B
	 */
	public int findFastestTime(String pointA, String pointB) {
		Vertex start = findVertex(pointA);
		Vertex end = findVertex(pointB);
		if (start == null && end != null) {
			return -1;
		}
		if (end == null && start != null) {
			return -2;
		}
		if (start == null && end == null) {
			return -3;
		}
		if (pointA == pointB) {
			return 0;
		}
		findFastestRoute(pointA, pointB);
		if (end.attribute == Integer.MAX_VALUE) {
			return -4;
		}
		return (int) Math.ceil(end.attribute);
	}
}
