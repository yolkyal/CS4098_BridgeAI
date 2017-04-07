package main;

import java.util.ArrayList;
import bridge_data_structures.*;
import user_io.UserIO;

public class Round {
	private Player[] players;
	private Deck deck;
	
	public Round(int numHumanPlayers){
		players = new Player[4];
	
		for(int i = 0; i < 4; i++){
			players[i] = new Player();	//Defaults human_player to false
		}
		switch(numHumanPlayers){
			case 4: players[Position.SOUTH].setHumanPlayer(true);
			case 3: players[Position.WEST].setHumanPlayer(true);
			case 2: players[Position.EAST].setHumanPlayer(true);
			case 1: players[Position.NORTH].setHumanPlayer(true);
		}
		
		deck = new Deck();
		deck.shuffle();
		deal(deck, players);
	}
	
	public void play(Contract contract){
		int tricks_won[] = {0, 0, 0, 0};
		int leader_pos = Position.getLeft(contract.getDeclarerPosition());
		int dummy_pos = Position.getOpposite(contract.getDeclarerPosition());
		ArrayList<Card> cards_played_this_round = new ArrayList<Card>();
		
		for (int trick_num = 0; trick_num < 13; trick_num++){
			displayTricksWon(tricks_won);
			Trick trick = new Trick(players, dummy_pos, leader_pos, contract.getSuit(), cards_played_this_round);
			while(!trick.isOver()){
				trick.playTurn();
			}
			cards_played_this_round.addAll(trick.getCards_played_this_trick());
			int winning_player_pos = trick.getWinningPlayerPosition();
			System.out.println(Position.getName(winning_player_pos) + " wins trick.");
			tricks_won[winning_player_pos]++;
			leader_pos = winning_player_pos;
		}
		System.out.println("\nRound Scores: ");
		displayTricksWon(tricks_won);
		
		if(tricks_won[contract.getDeclarerPosition()] >= (contract.getNumber() + 6)){
			System.out.println("Contract met!");
		}
		else{
			System.out.println("Contract not met!");
		}
		
	}
	
	public Contract runAuction(int dealer_pos){
		int bid_pos = dealer_pos;
		int consec_passes = 0;
		Contract contract = null;
		ArrayList<Contract> ls_bids = new ArrayList<Contract>();
		ArrayList<PlayerConstraint> ls_player_constraints = new ArrayList<PlayerConstraint>();
		
		while(true){
			System.out.println("\n" + Position.getName(bid_pos) + " BID");
			players[bid_pos].getHand().display();
			
			if (contract != null)
				System.out.println("Current contract is " + contract.toString());
			
			Contract bid = null;
			if(players[bid_pos].isHumanPlayer()){
				bid = UserIO.getBidInput(bid_pos, contract);
			}
			else{
				bid = BiddingAI.getBid(bid_pos, players[bid_pos].getHand(), ls_player_constraints, ls_bids, contract);
				if (bid == null){
					bid = BridgeAI.getBid(contract, ls_player_constraints, bid_pos);
				}
			}
			
			int value = bid.getNumber();
			if (value == -1){
				System.out.println(Position.getName(bid_pos) + " passes.");
				consec_passes++;
				bid_pos = Position.getLeft(bid_pos);
				ls_bids.add(bid);
				ls_player_constraints.add(BiddingAI.getConstraintFromLastBid(ls_bids, bid_pos));
			}
			else{	
				if (bid.isGreaterThan(contract)){
					contract = bid;
					consec_passes = 1;
					
					if (bid.getSuit() != null){
						System.out.println(Position.getName(bid_pos) + " bids " + 
							bid.getNumber() + " "+ bid.getSuit().name());
					}
					else{
						System.out.println(Position.getName(bid_pos) + " bids " + 
								bid.getNumber() + " "+ "NT");
					}
					
					bid_pos = Position.getLeft(bid_pos);
					ls_bids.add(bid);
				}
				else{
					System.out.println("This bid is not greater than the working contract.\n");
				}
			}	
			if (consec_passes == 4) break;
		}
		
		if(contract != null){
			System.out.println("Final Contract: " + contract.toString());
		}
		
		return contract;
	}
	
	public void deal(Deck deck, Player[] players){
		Card[] cards = deck.getCards();
		for (int i = 0; i < players.length; i++){
			Player player = players[i];
			ArrayList<Card> newHand = new ArrayList<Card>();
			for (int j = i * 13; j < (i + 1) * 13; j++){
				newHand.add(cards[j]);
			}
			player.setHand(new Hand(newHand));
			//System.out.println(player.getHand().getCards().size());
		}
	}
	
	private void displayTricksWon(int[] tricks_won){
		System.out.println();
		for(int i = 0; i < 4; i++){
			System.out.println(Position.getName(i) + ": " + tricks_won[i] + " TRICKS");
		}
		System.out.println();
	}
	
}
