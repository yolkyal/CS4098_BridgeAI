package user_io;
import java.util.Scanner;

import bridge_data_structures.Contract;
import bridge_data_structures.Suit;

public class UserIO {
	public static Scanner std_in;
	
	public static void open(){
		 std_in = new Scanner(System.in);
	}
	
	public static int getIntegerInput(){
		int x = std_in.nextInt();
		return x;
	}
	
	public static Contract getBidInput(int player_position, Contract cur_contract){
		int value;
		int suit_num = 4;
		
		while(true){
			System.out.print("Enter value (1 to 7), -1 pass: ");
			value = std_in.nextInt();
			if (value >= -1 && value <= 7) break;
			System.out.println("Invalid, please re-enter.");
		}
		
		while (value > -1){
			System.out.print("Enter suit (0 Spade to 4 NT): ");
			suit_num = std_in.nextInt();
			if (suit_num >= 0 && suit_num <= 4) break;
			System.out.println("Invalid, please re-enter.");
		}
		
		Suit suit;
		switch(suit_num){
			case 0: suit = Suit.SPADE;
					break;
			case 1: suit = Suit.HEART;
					break;
			case 2: suit = Suit.DIAMOND;
					break;
			case 3: suit = Suit.CLUB;
					break;
			default: suit = null;
		}
		
		return new Contract(value, suit, player_position);
	}
	
	public static void close(){
		//Only use at program termination
		std_in.close();
	}
}
