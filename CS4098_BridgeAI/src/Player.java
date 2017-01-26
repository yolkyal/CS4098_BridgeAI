
public class Player {
	private int playerID;
	private Hand hand;
	private boolean humanPlayer;

	public Player(int playerID, Hand hand) {
		this.playerID = playerID;
		this.hand = hand;
	}

	public Hand getHand() {
		return hand;
	}

	public void setHand(Hand hand) {
		this.hand = hand;
	}

	public int getPlayerID() {
		return playerID;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	public boolean isHumanPlayer() {
		return humanPlayer;
	}

	public void setHumanPlayer(boolean humanPlayer) {
		this.humanPlayer = humanPlayer;
	}
	
}
