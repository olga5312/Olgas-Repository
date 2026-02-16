package lab;

import java.util.ArrayList;
import java.util.List;

public class Graph {

	private final List<Vertex> vertices;
	private final List<Edge> edges;

	public Graph(List<Vertex> vertices, List<Edge> edges) {
		this.vertices = vertices;
		this.edges = edges;
	}

	public List<Vertex> getVertices() {
		return vertices;
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public Vertex findVertex(String name) {
		for (Vertex vertex : vertices) {
			if (vertex.getName().equals(name)) {
				return vertex;
			}
		}
		return null;
	}
}
