
public class BridgeAI {
	
	public static void main(String[] args) {
		UserIO.open();
		Round round = new Round(4);
		round.play();
		UserIO.close();
	}

}
