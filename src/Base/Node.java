package Base;
import java.util.ArrayList;

public abstract class Node {
	public double x, y;
	public ArrayList<Line> lines;
	
	public Node(){
		lines = new ArrayList<Line>();
	}
}
