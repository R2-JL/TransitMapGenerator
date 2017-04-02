package Base;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.*;
import java.util.Scanner;

public class ZipTest {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		ZipFile z = new ZipFile("C:\\GTFS\\BART.zip");
		InputStream s = z.getInputStream(z.getEntry("stops.txt"));
		Scanner scan = new Scanner(s);
		System.out.println(scan.nextLine());
	}

}
