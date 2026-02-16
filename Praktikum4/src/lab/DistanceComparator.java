package lab;

import java.util.Comparator;

public class DistanceComparator implements Comparator<Edge> {

	@Override
	public int compare(Edge A, Edge B) {
		if (A.getDistance() > B.getDistance()) {
			return 1;
		} else {
			if (A.getDistance() < B.getDistance()) {
				return -1;
			} else {
				return 0;
			}
		}

	}

}
