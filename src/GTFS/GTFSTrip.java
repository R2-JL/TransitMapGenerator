package GTFS;

import java.util.ArrayList;

public class GTFSTrip {
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
}
