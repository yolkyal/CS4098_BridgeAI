
public class Round {
	private Player[] players;
	private Deck deck;
	
	public Round(int numHumanPlayers){
		players = new Player[4];
	
		for(int i = 0; i < 4; i++){
			players[i] = new Player();	//Defaults human_player to false
		}
		switch(numHumanPlayers){
			case 4: players[Position.WEST].setHumanPlayer(true);
			case 3: players[Position.EAST].setHumanPlayer(true);
			case 2: players[Position.SOUTH].setHumanPlayer(true);
			case 1: players[Position.NORTH].setHumanPlayer(true);
		}
		
		deck = new Deck();
		deck.shuffle();
		deck.deal(players);
	}
	
	public void play(Contract contract){
		int tricks_won[] = {0, 0, 0, 0};
		int leader_pos = contract.getDeclarerPosition();
		int dummy_pos = Position.getOpposite(contract.getDeclarerPosition());
		
		for (int trick_num = 0; trick_num < 13; trick_num++){
			displayTricksWon(tricks_won);
			Trick trick = new Trick(players, dummy_pos, leader_pos, contract.getSuit());
			while(!trick.isOver()){
				trick.playTurn();
			}
			int winning_player_pos = trick.getWinningPlayerPosition();
			System.out.println(Position.getName(winning_player_pos) + " wins trick.");
			tricks_won[winning_player_pos]++;
			leader_pos = winning_player_pos;
		}
		System.out.println("\nRound Scores: ");
		displayTricksWon(tricks_won);
	}
	
	public Contract runAuction(int dealer_pos){
		int bid_pos = dealer_pos;
		
		int consec_passes = 0;
		Contract last_bid = null;
		while(true){
			System.out.println("\n" + Position.getName(bid_pos) + " BID");
			players[bid_pos].getHand().display();
			Contract contract = UserIO.getBidInput(bid_pos);
			
			int value = contract.getNumber();
			if (value == -1) consec_passes++;
			else{ 
				last_bid = contract;
				consec_passes = 1;
				System.out.println(Position.getName(bid_pos) + " bids " + 
				contract.getNumber() + " "+ contract.getSuit().name());
			}
			
			bid_pos = Position.getLeft(bid_pos);
			if (consec_passes == 4) break;
		}
		System.out.println("Final Contract: " + last_bid.toString());
		
		return last_bid;
	}
	
	private void displayTricksWon(int[] tricks_won){
		System.out.println();
		for(int i = 0; i < 4; i++){
			System.out.println(Position.getName(i) + ": " + tricks_won[i] + " TRICKS");
		}
		System.out.println();
	}
	
}
