package Base;
import java.util.ArrayList;

public class Station extends Node {
	public double lat, lon;
	public String name;
	public String key;
	public boolean usingInMap;
	

	public Station() {
		super();
	}

	public void Normalize(double minLat, double minLon, double minX, double minY, double latRange, double lonRange,
			double xRange, double yRange) {
		x = (lat - minLat) * (xRange / latRange);
		y = (lon - minLon) * (yRange / lonRange);
	}
}
