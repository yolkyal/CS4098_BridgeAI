package main;

import bridge_data_structures.Hand;

public class Player {
	private int playerID;
	private Hand hand;
	private boolean humanPlayer;

	public Player(){
		humanPlayer = false;
	}
	
	public Player(int playerID, boolean humanPlayer) {
		this.playerID = playerID;
		this.humanPlayer = humanPlayer;
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
