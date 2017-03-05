package Base;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

public class SystemModel {
	public ArrayList<Line> lines;
	public ArrayList<Node> nodes;
	
	public SystemModel(GTFS.GTFSSystem fromGTFS ){
		HashMap<String, Line> lineMap = new HashMap<String, Line>();
		HashMap<String, Station> stopMap = new HashMap<String, Station>();
		double minLat = 9999;
		double minLon = 9999;
		double maxLat = -9999;
		double maxLon = -9999;
		
		for(GTFS.GTFSLine gl : fromGTFS.lines.values()){
			if(gl.usingInMap){
				Line ml = new Line();
				if(gl.lName != null){
					ml.name = gl.lName;
				} else ml.name = gl.sName;
				ml.key = gl.id;
				ml.color = parseColor(gl.color);
				
				lineMap.put(gl.id, ml);
			}
		}
		
		for(GTFS.GTFSStop gs : fromGTFS.stops.values()){
			Station ms = new Station();
			ms.name = gs.name;
			ms.key = gs.id;
			ms.lat = gs.lat;
			ms.lon = gs.lon;
			
			stopMap.put(gs.id, ms);
			
			
		}
	}
	
	private Color parseColor(String hexCode){
		if(hexCode == null){
			return Color.BLACK;
		}
		int red = Integer.parseInt(hexCode.substring(0,2), 16);
		int green = Integer.parseInt(hexCode.substring(2,4), 16);
		int blue = Integer.parseInt(hexCode.substring(4,6), 16);
		
		return new Color(red,green,blue);
	}
}
 