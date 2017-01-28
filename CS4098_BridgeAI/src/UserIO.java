import java.util.Scanner;

public class UserIO {
	public static Scanner std_in;
	
	public static void open(){
		 std_in = new Scanner(System.in);
	}
	
	public static int getIntegerInput(){
		int x = std_in.nextInt();
		return x;
	}
	
	public static void close(){
		//Only use at program termination
		std_in.close();
	}
}
