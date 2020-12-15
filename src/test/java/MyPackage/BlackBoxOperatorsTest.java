package MyPackage;

// make sure * is not an op
import java.util.*;

public class BlackBoxOperatorsTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int i = add(2, 5);
		int j = add(i, 2);

	}
	
	
	public static int add(int a, int b) {
		return a + b;
	}

}
