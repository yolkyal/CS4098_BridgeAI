
public class Match {
	private Player[] players;
	private int turn;
	private Suit trumpSuit;
	private Deck deck;
	
	public Match(){
		
	}
	
	public void runTurn(){
		if (players[turn].isHumanPlayer()){
			//Human player turn
			displayPlayerOptions(players[turn]);
		}
		
	}
	
	private void displayPlayerOptions(Player player){
		player.getHand().print();
	}
	
}
