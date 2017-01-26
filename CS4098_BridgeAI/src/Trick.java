import java.util.ArrayList;
import java.util.Scanner;

public class Trick {
	private Player[] players;
	private Player dummy;
	private Suit trump_suit;
	private Suit trick_suit;
	private Player turn_player;
	private int turn_player_position;
	private Card winning_card;
	private Player winning_player;
	
	public Trick(Player[] players, int dummy_position, int leader_position, Suit trump_suit, Suit trick_suit) {
		this.players = players;
		this.dummy = players[dummy_position];
		this.trump_suit = trump_suit;
		this.trick_suit = trick_suit;
		this.turn_player = players[leader_position];
	}

	public void playTurn(){
		Hand turn_hand = turn_player.getHand();
		Card selected_card = null;
		
		if (turn_player.isHumanPlayer()){
			turn_hand.display();
			
			while(selected_card == null){
				System.out.print("Choose a card: ");
				Scanner scanner = new Scanner(System.in);
				int card_index = scanner.nextInt();
				scanner.close();
				
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
				options.addAll(turn_hand.getSuitCards(trump_suit));
;			}
			if (options.isEmpty()) options = turn_hand.getCards();
			
			//Choose random valid card -- this will of course be changed later
			int rand = (int)(Math.random() * options.size());
			selected_card = options.get(rand);	
		}
		
		playCard(selected_card);
		turn_player_position = (turn_player_position + 1) % 4;
		turn_player = players[turn_player_position];
	}
	
	private void playCard(Card card){
		System.out.println(Position.getName(turn_player_position) + " plays " + card.toString());
		
		if (isNewWinningCard(card)){
			winning_card = card;
			winning_player = turn_player;
		}
	}
	
	private boolean isNewWinningCard(Card new_card){
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
	
}
