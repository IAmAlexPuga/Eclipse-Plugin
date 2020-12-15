package MyPackage;

public class BlackBoxLoopsTest {

	public static void main(String[] args) {
		
		int arr[] = {1,2,3,45,6,7};
		
		for(int i = 0; i < arr.length; i++) {
			System.out.println(arr[i]);
			
			for(int j : arr) {
				System.out.print(j);
			}
		}
		
		boolean i = true;
		while(i) {
			i = false;
		}
		
		do
		{
			i = true;
		}while (i == false);

	}

}
