package lab;

import java.util.LinkedList;
import java.util.List;

public class Vertex {

	private final String name;
	private final int waitingTime;
	boolean visited;
	boolean finished;
	double attribute;
	Edge predecessor;
	List<Edge> edges;

	public Vertex(String name, int waitingTime) {
		edges = new LinkedList<Edge>();
		this.name = name;
		this.waitingTime = waitingTime;
	}

	public String getName() {
		return name;
	}

	public int getWaitingTime() {
		return waitingTime;
	}

	public boolean determineFinishedStatus() {
		return finished;
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(this.getName());
		s.append(" [label=\"");
		s.append(this.getName());
		s.append(",");
		s.append(this.getWaitingTime());
		s.append("\"];");
		return s.toString();
	}
}
