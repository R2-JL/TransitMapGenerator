package GTFS;

import java.io.FileNotFoundException;

public class ReaderTester {
	public static void main(String[] args){
		GTFSReader read = new GTFSReader("C:\\GTFS\\TriMet");
		try {
			read.readStops();
			read.readLines();
			read.readTrips();
			read.readStopCalls();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("done");
	}
}
