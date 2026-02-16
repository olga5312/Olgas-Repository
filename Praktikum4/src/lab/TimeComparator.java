package lab;

import java.util.Comparator;

public class TimeComparator implements Comparator<Edge> {

	@Override
	public int compare(Edge A, Edge B) {
		if (A.getTravelTime() > B.getTravelTime()) {
			return 1;
		} else {
			if (A.getTravelTime() < B.getTravelTime()) {
				return -1;
			} else {
				return 0;
			}
		}

	}
}
