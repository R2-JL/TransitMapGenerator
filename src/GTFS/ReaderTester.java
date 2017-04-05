package GTFS;

import java.io.File;
import java.io.IOException;

public class ReaderTester {
	public static void main(String[] args){
		GTFSReader read;
		try {
			read = new GTFSReader(new File("C:\\GTFS\\BART.zip"));
			read.readStops();
			read.readLines();
			read.readTrips();
			read.readStopCalls();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("done");
	}
}
