package Base;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class SystemModel {
	public ArrayList<Line> lines;
	public ArrayList<Node> nodes;
	public ArrayList<Segment> segments;
	
	public static int X_SIZE = 1000;
	public static int Y_SIZE = 1000;
	
	public SystemModel(GTFS.GTFSSystem fromGTFS ){
		lines = new ArrayList<Line>();
		nodes = new ArrayList<Node>();
		segments = new ArrayList<Segment>();
		
		HashMap<String, Station> stopMap = new HashMap<String, Station>();
		double minLat = 9999;
		double minLon = 9999;
		double maxLat = -9999;
		double maxLon = -9999;
		
		
		
		for(GTFS.GTFSStop gs : fromGTFS.stops.values()){
			Station ms = new Station();
			ms.name = gs.name;
			ms.key = gs.id;
			ms.lat = gs.lat;
			ms.lon = gs.lon;
			ms.usingInMap = false;
			
			stopMap.put(gs.id, ms);
		}
		
		for(GTFS.GTFSLine gl : fromGTFS.lines.values()){
			if(gl.usingInMap){
				Line ml = new Line();
				if(gl.lName != null){
					ml.name = gl.lName;
				} else ml.name = gl.sName;
				ml.key = gl.id;
				ml.color = parseColor(gl.color);
				
				lines.add(ml);
				
				//get representative trip
				GTFS.GTFSTrip t = gl.representativeTrip;
				
				//process stops
				for(GTFS.GTFSStopCall sc : t.stops){
					Station ms = stopMap.get(sc.stopId);
					ms.usingInMap = true;
					ms.lines.add(ml);
					ml.nodes.add(ms);
				}
			}
		}
		
		for(Station s : stopMap.values()){
			if(s.usingInMap){
				if(s.lat < minLat){
					minLat = s.lat;
				}
				if(s.lat > maxLat){
					maxLat = s.lat;
				}
				if(s.lon < minLon){
					minLon = s.lon;
				}
				if(s.lon > maxLon){
					maxLon = s.lon;
				}
			}
		}
		
		//Note termini
		
		for(Line l : lines){
			l.nodes.get(0).terminatingLines.add(l);
			if(l.nodes.size() > 1){
				//Worth checking, but if it's not what's wrong with your transit system?
				l.nodes.get(l.nodes.size() - 1).terminatingLines.add(l);
			}
		}
		
		//Normalize position
		
		for(Station s : stopMap.values()){
			if(s.usingInMap){
				s.Normalize(minLat, minLon, 0, 0, maxLat - minLat, maxLon - minLon, X_SIZE, Y_SIZE);
				nodes.add(s);
			}
		}
		
		//Generate segments
		
		ArrayList<Segment> tempSegments = new ArrayList<Segment>();
		
		for(Line l : lines){
			for(int i = 0; i < l.nodes.size() - 1; i++){
				Segment seg = new Segment(l.nodes.get(i), l.nodes.get(i + 1));
				seg.lines.add(l);
				tempSegments.add(seg);
			}
		}
		
		Collections.sort(tempSegments);

		//Collate segments
		
		int firstMatch = 0;
		for(int i = 0; i <= tempSegments.size(); i++){
//			if(i == tempSegments.size()){
//				//TODO add logic
//			}
			if(i < tempSegments.size() && tempSegments.get(firstMatch).compareTo(tempSegments.get(i)) == 0){
				continue;
			} else {
				for(int j = firstMatch + 1; j < i; j++){
					tempSegments.get(firstMatch).lines.add(tempSegments.get(j).lines.get(0));
				}
				segments.add(tempSegments.get(firstMatch));
				firstMatch = i;
			}
		}
	}
	
	
	private Color parseColor(String hexCode){
		if(hexCode == null){
			return Color.BLACK;
		}
		if(hexCode.equals("")){
			return Color.BLACK;
		}
		try{
			int red = Integer.parseInt(hexCode.substring(0,2), 16);
			int green = Integer.parseInt(hexCode.substring(2,4), 16);
			int blue = Integer.parseInt(hexCode.substring(4,6), 16);
			
			return new Color(red,green,blue);
		}
		catch(NumberFormatException e){
			return Color.BLACK;
		}
	}
}
 