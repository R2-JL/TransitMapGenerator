package GTFS;

import java.util.ArrayList;

public class GTFSLine implements Comparable<GTFSLine> {
	public String id;
	public String sName;
	public String lName;
	public String color;
	
	public boolean usingInMap = false;
	public GTFSTrip representativeTrip;
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(id);
		sb.append(": ");
		if(lName != null && !lName.equals("")){
			sb.append(lName);
		} else sb.append(sName);
		
		return sb.toString();
	}
	
	public int compareTo(GTFSLine other){
		return id.compareTo(other.id);
	}

}
