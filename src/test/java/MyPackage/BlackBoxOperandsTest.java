package MyPackage;

public class BlackBoxOperandsTest {
	public static void main(String[] args) {
		double j = 2.0 / 4;
		char let = 'a' ;
		String word = "Hello";

		int i = 1 + 3 - 2 + word.toLowerCase().length() + word.length();
		String greeting = "My name is Chad";
		String scen = word + " " + greeting;

		System.out.println(scen + 1);
		add(1,3);
	}
	
	public static int add(int a, int b) {
		return a + b;
	}

}
