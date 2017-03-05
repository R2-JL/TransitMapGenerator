package GTFS;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class GTFSReader {
	private String folderPath;
	private Scanner fileScan;
	private Scanner lineScan;
	private File f;
	private GTFSSystem sys;
	public boolean doDeLinkStations = true;
	
	public GTFSReader(String path){
		folderPath = path;
		sys = new GTFSSystem();
	}
	
	public void readStops() throws FileNotFoundException{
		int indexId = -1;
		int indexName = -1;
		int indexLat = -1;
		int indexLon = -1;
		int indexIsStation = -1;
		int indexParentStation = -1;
		
		f = new File(folderPath + "\\stops.txt");
		fileScan = new Scanner(f);
		
		//set up token order
		lineScan = new Scanner(fileScan.nextLine());
		lineScan.useDelimiter(",");
		for(int i = 0; lineScan.hasNext();i++){
			String tok = lineScan.next();
			if(tok.equalsIgnoreCase("stop_id")){
				indexId = i;
			}
			else if(tok.equalsIgnoreCase("stop_name")){
				indexName = i;
			}
			else if(tok.equalsIgnoreCase("stop_lat")){
				indexLat = i;
			}
			else if(tok.equalsIgnoreCase("stop_lon")){
				indexLon = i;
			}
			else if(tok.equalsIgnoreCase("location_type")){
				indexIsStation = i;
			}
			else if(tok.equalsIgnoreCase("parent_station")){
				indexParentStation = i;
			}
		}
		
		//read entries
		while(fileScan.hasNextLine()){
			lineScan = new Scanner(fileScan.nextLine());
			lineScan.useDelimiter(",");
			
			GTFSStop s = new GTFSStop();
			for(int i = 0; lineScan.hasNext(); i++){
				String tok = lineScan.next();
				if(i == indexId){
					s.id = tok;
				}
				else if(i == indexName){
					s.name = tok;
				}
				else if(i == indexLat){
					s.lat = Double.parseDouble(tok);
				}
				else if(i == indexLon){
					s.lon = Double.parseDouble(tok);
				}
				else if(i == indexIsStation){
					if(tok.contains("1")){
						s.isStation = true;
					}
				}
				else if(i == indexParentStation){
					s.parentStation = tok;
				}
			}
			sys.stops.put(s.id, s);
		}
		fileScan.close();
	}
	
	public void readLines() throws FileNotFoundException{
		int indexId = -1;
		int indexSName = -1;
		int indexLName = -1;
		int indexColor = -1;
		
		f = new File(folderPath + "\\routes.txt");
		fileScan = new Scanner(f);
		
		//set up token order
		lineScan = new Scanner(fileScan.nextLine());
		lineScan.useDelimiter(",");
		for(int i = 0; lineScan.hasNext();i++){
			String tok = lineScan.next();
			if(tok.equalsIgnoreCase("route_id")){
				indexId = i;
			}
			else if(tok.equalsIgnoreCase("route_short_name")){
				indexSName = i;
			}
			else if(tok.equalsIgnoreCase("route_long_name")){
				indexLName = i;
			}
			else if(tok.equalsIgnoreCase("route_color")){
				indexColor = i;
			}
		}
		
		//read entries
		while(fileScan.hasNextLine()){
			lineScan = new Scanner(fileScan.nextLine());
			lineScan.useDelimiter(",");
			
			GTFSLine l = new GTFSLine();
			for(int i = 0; lineScan.hasNext(); i++){
				String tok = lineScan.next();
				if(i == indexId){
					l.id = tok;
				}
				else if(i == indexSName){
					l.sName = tok;
				}
				else if(i == indexLName){
					l.lName = tok;
				}
				else if(i == indexColor){
					l.color = tok;
				}
			}
			sys.lines.put(l.id, l);
		}
		fileScan.close();
	}
	
	public void readTrips() throws FileNotFoundException{
		int indexId = -1;
		int indexRoute = -1;
		int indexHeadsign = -1;
		
		f = new File(folderPath + "\\trips.txt");
		fileScan = new Scanner(f);
		
		//set up token order
		lineScan = new Scanner(fileScan.nextLine());
		lineScan.useDelimiter(",");
		for(int i = 0; lineScan.hasNext();i++){
			String tok = lineScan.next();
			if(tok.equalsIgnoreCase("route_id")){
				indexRoute = i;
			}
			else if(tok.equalsIgnoreCase("trip_id")){
				indexId = i;
			}
			else if(tok.equalsIgnoreCase("trip_headsign")){
				indexHeadsign = i;
			}
		}
		
		//read entries
		while(fileScan.hasNextLine()){
			lineScan = new Scanner(fileScan.nextLine());
			lineScan.useDelimiter(",");
			
			GTFSTrip t = new GTFSTrip();
			for(int i = 0; lineScan.hasNext(); i++){
				String tok = lineScan.next();
				if(i == indexId){
					t.id = tok;
				}
				else if(i == indexRoute){
					t.routeId = tok;
				}
				else if(i == indexHeadsign){
					t.headsign = tok;
				}
			}
			sys.trips.put(t.id, t);
		}
		fileScan.close();
	}
	
	public void readStopCalls() throws FileNotFoundException{
		int indexSeq = -1;
		int indexStop= -1;
		int indexTrip = -1;
		ArrayList<GTFSStopCall> calls = new ArrayList<GTFSStopCall>();
		
		f = new File(folderPath + "\\stop_times.txt");
		fileScan = new Scanner(f);
		
		//set up token order
		lineScan = new Scanner(fileScan.nextLine());
		lineScan.useDelimiter(",");
		for(int i = 0; lineScan.hasNext();i++){
			String tok = lineScan.next();
			if(tok.equalsIgnoreCase("trip_id")){
				indexTrip = i;
			}
			else if(tok.equalsIgnoreCase("stop_id")){
				indexStop = i;
			}
			else if(tok.equalsIgnoreCase("stop_sequence")){
				indexSeq = i;
			}
		}
		
		//read entries
		while(fileScan.hasNextLine()){
			lineScan = new Scanner(fileScan.nextLine());
			lineScan.useDelimiter(",");
			
			GTFSStopCall c = new GTFSStopCall();
			for(int i = 0; lineScan.hasNext(); i++){
				String tok = lineScan.next();
				if(i == indexTrip){
					c.tripId = tok;
				}
				else if(i == indexStop){
					c.stopId = tok;
				}
				else if(i == indexSeq){
					c.stopSeq = Integer.parseInt(tok);
				}
			}
			calls.add(c);
		}
		fileScan.close();
		
		if(doDeLinkStations){
			deLinkStations(calls);
		}
		for(GTFSStopCall c : calls){
			sys.trips.get(c.tripId).stops.add(c);
		}
		for(GTFSTrip t : sys.trips.values()){
			t.stops.sort(null);
		}
	}
	
	public GTFSSystem getSystem(){
			return sys;
	}
	
	private void deLinkStations(ArrayList<GTFSStopCall> calls){
		for(GTFSStopCall sc : calls){
			GTFSStop stop = sys.stops.get(sc.stopId);
			if(stop.parentStation != null && stop.parentStation.compareTo("") != 0){
				sc.stopId = stop.parentStation;
			}
		}
	}
}
