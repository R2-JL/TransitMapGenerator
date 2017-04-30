package Base;

public class TokenTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String input = "A,b, c , \"d,still d\", e";
		Tokeniser tok = new Tokeniser(input);
		while(tok.hasNext()){
			System.out.println("hi");
			System.out.println(tok.next());
		}
	}

}
