package GTFS;

public class GTFSStopCall implements Comparable<GTFSStopCall> {
	
	public String tripId;
	public String stopId;
	public int stopSeq;
	
	@Override
	public int compareTo(GTFSStopCall other) {
		return this.stopSeq - other.stopSeq;
	}
	
}
