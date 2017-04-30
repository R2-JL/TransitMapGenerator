package Base;

public class Tokeniser {
	private String source;
	private int index;
	
	public Tokeniser(String source){
		this.source = source;
		index = 0;
	}
	
	public String next(){
		int oldIndex = index;
		boolean escaped = false;
		boolean keepGoing = true;
		while(keepGoing){
			if(index >= source.length()){
				//System.out.println("run out");
				return source.substring(oldIndex).replace("\"","").trim();
			}
			char nextChar = source.charAt(index);
			if(nextChar == '"'){
				//System.out.println("quote");
				escaped = !escaped;
			} else if(nextChar == ','){
				//System.out.println("comma");
				if(!escaped){
					keepGoing = false;
				}
			}
			index++;
		}
		
		return source.substring(oldIndex, index - 1).replace("\"","").trim();
	}
	
	public boolean hasNext(){
		return index < source.length();
	}
}
