
public class Round {
	private Player[] players;
	private int turn;
	private Suit trumpSuit;
	private Deck deck;
	
	public Round(int numHumanPlayers){
		deck = new Deck();
		players = new Player[4];
	
		for(int i = 0; i < 4; i++){
			players[i] = new Player();
		}
		switch(numHumanPlayers){
			case 4: players[Position.NORTH].setHumanPlayer(true);
			case 3: players[Position.SOUTH].setHumanPlayer(true);
			case 2: players[Position.EAST].setHumanPlayer(true);
			case 1: players[Position.WEST].setHumanPlayer(true);
		}
	}
	
	public void playTrick(){
		
		
	}
	
}
