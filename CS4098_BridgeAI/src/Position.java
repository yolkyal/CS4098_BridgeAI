
public class Position {
	//Essentially an enum but with array-indexable values
	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	
	public static String getName(int pos){
		switch(pos){
			case 0: return "NORTH";
			case 1: return "EAST";
			case 2: return "SOUTH";
			case 3: return "WEST";
			default: return "";
		}
	}
	
	public static int getOpposite(int pos){
		switch(pos){
			case 0: return 2;
			case 1: return 3;
			case 2: return 0;
			case 3: return 1;
			default: return -1;
		}
	}
}
