package Base;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

public class SystemModel {
	public ArrayList<Line> lines;
	public ArrayList<Node> nodes;
	
	public SystemModel(GTFS.GTFSSystem fromGTFS ){
		lines = new ArrayList<Line>();
		nodes = new ArrayList<Node>();
		
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
		
		for(Station s : stopMap.values()){
			if(s.usingInMap){
				s.Normalize(minLat, minLon, 0, 0, maxLat - minLat, maxLon - minLon, 1000, 1000);
				nodes.add(s);
			}
		}
	}
	
	
	private Color parseColor(String hexCode){
		if(hexCode == null){
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
 