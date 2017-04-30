package Base;

import java.util.ArrayList;

public class Segment implements Comparable<Segment> {
	public Node start, end;
	public ArrayList<Line> lines;
	
	public Segment(Node a, Node b){

		if(a.compareTo(b) < 0){
			start = a;
			end = b;
		} else {
			start = b;
			end = a;
		}
		lines = new ArrayList<Line>();
	}
	
	public int compareTo(Segment other){
		if(this.start.compareTo(other.start) == 0)
			return this.end.compareTo(other.end);
		else return this.start.compareTo(other.start);
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder("SEGMENT: S=");
		sb.append(start.toString());
		sb.append("; E=");
		sb.append(end.toString());
		sb.append("; L=");
		for(Line l : lines){
			sb.append(l.toString());
			sb.append(", ");
		}
		return sb.toString();
	}
}
