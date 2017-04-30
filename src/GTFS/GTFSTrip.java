package GTFS;

import java.util.ArrayList;

public class GTFSTrip implements Comparable<GTFSTrip>{
	public String id;
	public String routeId;
	public String headsign;
	public ArrayList<GTFSStopCall> stops;
	
	public GTFSTrip(){
		stops = new ArrayList<GTFSStopCall>();
	}
	
	public String toString(){
		return id + ": \"" + headsign + "\"";
	}

	@Override
	public int compareTo(GTFSTrip other) {
		return this.stops.size() - other.stops.size();
	}
}
