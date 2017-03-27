package main;
import java.util.ArrayList;
import bridge_data_structures.*;
import user_io.UserIO;

public class Trick {
	private Player[] players;
	private int dummy_position;
	private Suit trump_suit;
	private Suit trick_suit;
	private Player turn_player;
	private int turn_player_position;
	private Card winning_card;
	private int winning_player_position;
	private int turns_played;
	
	public Trick(Player[] players, int dummy_position, int leader_position, Suit trump_suit) {
		this.players = players;
		this.dummy_position = dummy_position;
		this.trump_suit = trump_suit;
		this.trick_suit = null; //Not ascertained until first card is played
		this.turn_player = players[leader_position];
		turn_player_position = leader_position;
		turns_played = 0;
		winning_card = null;
		winning_player_position = leader_position;
	}

	public void playTurn(){
		Hand turn_hand = turn_player.getHand();
		Card selected_card = null;
		
		if (turn_player.isHumanPlayer() || turn_player_position == dummy_position && 
				players[Position.getOpposite(turn_player_position)].isHumanPlayer()){
			System.out.println(Position.getName(turn_player_position) + " TURN");
			turn_hand.display();
			
			while(selected_card == null){
				System.out.print("Choose a card: ");
				int card_index = UserIO.getIntegerInput();
				
				if (card_index < 0 || card_index >= turn_hand.getCards().size()){
					System.out.println("Invalid choice.");
					continue;
				}
				selected_card = turn_hand.getCards().get(card_index);
				if (!isValidPlay(selected_card)){
					System.out.println("Illegal choice!");
					selected_card = null;
				}
			}
		}
		else{
			ArrayList<Card> options;
			if (trick_suit == null) options = turn_hand.getCards();
			else{
				options = turn_hand.getSuitCards(trick_suit);
			}
			if (options.isEmpty()) options = turn_hand.getCards();
			
			//Choose random valid card -- this will of course be changed later
			int rand = (int)(Math.random() * options.size());
			selected_card = options.get(rand);	
		}
		
		playCard(selected_card);
		turn_player_position = (turn_player_position + 1) % 4;
		turn_player = players[turn_player_position];
		turns_played++;
	}
	
	@SuppressWarnings("unused")
	private void printAllHands(){
		for(int i = 0; i < players.length; i++){
			System.out.println(i + ": " + Position.getName(i));
			Player player = players[i];
			player.getHand().display();
		}
	}
	
	public boolean isOver(){
		return turns_played == 4;
	}
	
	private void playCard(Card card){
		System.out.println(Position.getName(turn_player_position) + " plays " + card.toString());
		System.out.println();
		
		if (isNewWinningCard(card)){
			winning_card = card;
			winning_player_position = turn_player_position;
			if (trick_suit == null) trick_suit = card.getSuit();
		}
		
		turn_player.getHand().getCards().remove(card);
	}
	
	private boolean isNewWinningCard(Card new_card){
		if (winning_card == null) return true;
		if (new_card.getSuit() == winning_card.getSuit()){
			return new_card.isHigherThan(winning_card);
		}
		else return new_card.getSuit() == trump_suit;
	}
	
	private boolean isValidPlay(Card card){
		if (trick_suit == null) return true;
		Suit card_suit = card.getSuit();
		if (card_suit == trick_suit) return true;
		else return !turn_player.getHand().containsSuit(trick_suit);
	}

	public int getWinningPlayerPosition() {
		return winning_player_position;
	}
	
}