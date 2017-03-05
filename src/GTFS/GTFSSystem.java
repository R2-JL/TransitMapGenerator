package GTFS;

import java.util.ArrayList;
import java.util.HashMap;

public class GTFSSystem {
	public HashMap<String, GTFSStop> stops;
	public HashMap<String, GTFSLine> lines;
	public HashMap<String, GTFSTrip> trips;
	
	public GTFSSystem(){
		stops = new HashMap<String,GTFSStop>();
		lines = new HashMap<String,GTFSLine>();
		trips = new HashMap<String,GTFSTrip>();
	}
	
	public ArrayList<GTFSLine> getLines(){
		return getLines(false);
	}
	
	public ArrayList<GTFSLine> getLines(boolean usedLinesOnly){
		ArrayList<GTFSLine> lineList = new ArrayList<GTFSLine>();
		
		for(GTFSLine l: lines.values()){
			if(!usedLinesOnly || l.usingInMap){
				lineList.add(l);
			}
		}
		
		lineList.sort(null);
		
		return lineList;
	}
	
	public ArrayList<GTFSTrip> getTripsForLine(String lineID){
		ArrayList<GTFSTrip> lineTrips = new ArrayList<GTFSTrip>();
		
		for(GTFSTrip t : trips.values()){
			if(t.routeId.equals(lineID)){
				lineTrips.add(t);
			}
		}
		
		return lineTrips;
	}
	
	
	public ArrayList<String> getStopIDsForLine(String LineID){
		ArrayList<String> stops = new ArrayList<String>();
		
		for( GTFSStopCall s : trips.get(LineID).stops){
			stops.add(s.stopId);
		}
		
		return stops;
	}
}
