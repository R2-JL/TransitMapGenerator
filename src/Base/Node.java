package Base;
import java.util.ArrayList;

public abstract class Node implements Comparable<Node>{
	public double x, y;
	public ArrayList<Line> lines;
	public ArrayList<Line> terminatingLines;
	
	public Node(){
		lines = new ArrayList<Line>();
		terminatingLines = new ArrayList<Line>();
	}

	public int compareTo(Node other) {
		if(this.x < other.x){
			return -1;
		} else if (this.x > other.x){
			return 1;
		} else {
			if(this.y < other.y){
				return -1;
			} else if (this.y > other.y){
				return 1;
			} else return 0;
		}
	}
}
