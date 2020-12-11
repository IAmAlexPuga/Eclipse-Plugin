package MyPackage;

public class BlackBoxExprTest {
	
	public static void main(String[] args) {
		int i = 1 + 3 - 2;
		
		
		if(i > 4) {
			System.out.println("i bigger 4");
		}
		
		int total = sum(i-1, 4);
	}
	
	
	public static int sum(int a, int b) {
		return a + b;
	}

}
