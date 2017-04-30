package Base;
import java.awt.Color;
import java.util.ArrayList;

public class Line {
	public String name;
	public Color color;
	public ArrayList<Node> nodes;
	public String key;

	public Line() {
		nodes = new ArrayList<Node>();
	}
	
	public String toString(){
		return name;
	}
}
