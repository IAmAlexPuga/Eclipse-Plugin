package MyPackage;

public class BlackBoxOperandsTest {
	public static void main(String[] args) {
		double j = 2.0 / 4;
		// remove Uniq ops from getting type var = something
		char let = 'a' ;
		// not catching strings & chars
		String word = "Hello";
		// not catching word.length
		// if token is METHOD_CALL && parent is Operator
		int i = 1 + 3 - 2 + word.toLowerCase().length();

		// include STRING_LITERAL in token types and check for operands
		String greeting = "My name is Chad";
		String scen = word + " " + greeting;
		//String ex = "Lowercased: " + scen.toLowerCase().toString();
		System.out.println(scen + 1);
		add(1,3);
	}
	
	public static int add(int a, int b) {
		return a + b;
	}

}
