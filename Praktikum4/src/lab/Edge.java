package lab;

public class Edge {

	final Vertex source;
	final Vertex destination;
	private final int distance;
	private final int speed;
	protected boolean onRoute;

	public Edge(Vertex source, Vertex destination, int distance, int speed) {
		this.source = source;
		this.destination = destination;
		this.distance = distance;
		this.speed = speed;
		onRoute = false;
	}

	public Vertex getSource() {
		return source;
	}

	public Vertex getDestination() {
		return destination;
	}

	public int getDistance() {
		return distance;
	}

	public int getSpeed() {
		return speed;
	}

	// public double getTravelTime() {
	// return distance / speed;
	// }

	public double getTravelTime() {
		return (1000 * 3.6 * (double) distance) / ((double) speed) / 60;
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(source);
		s.append(" -> ");
		s.append(destination);
		s.append(" [label=\"");
		s.append(distance);
		s.append(",");
		s.append(speed);
		s.append("\"");
		s.append("]");
		if (onRoute) {
			s.append(" [style=bold];");
		} else {
			s.append(";");
		}
		return s.toString();
	}

}
