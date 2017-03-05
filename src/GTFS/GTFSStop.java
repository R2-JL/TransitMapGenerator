package GTFS;

public class GTFSStop {
	public String id;
	public String name;
	public double lat;
	public double lon;
	public boolean isStation = false;
	public String parentStation;
	public String toString(){
		return name;
	}
}
