package bridge_data_structures;
import java.util.ArrayList;

public class Hand {
	private ArrayList<Card> cards;
	private int orig_points;
	private boolean orig_balanced;
	
	public Hand(ArrayList<Card> cards){
		this.cards = cards;
		
		orig_points = 0;
		
		for (Card c : cards){
			CardValue cv = c.getValue();
			switch(cv){
			case ACE: orig_points += 1;
			case KING: orig_points += 1;
			case QUEEN: orig_points += 1;
			case JACK: orig_points += 1;
			default: break;
			}
		}
		
		calculatePointsAndBalance();
	}
	
	public Hand(String parser_string){
		/*Set up hand using a string of the form "KJT.987.65.AQ732"
		 * where each char denotes a card and '.' separates the suits
		 * in declining order (Spade to Club). */
		
		cards = new ArrayList<Card>();
		
		Suit suit = Suit.SPADE;
		for(int i = 0; i < parser_string.length(); i++){
			char c = parser_string.charAt(i);
			if(c == '.'){
				suit = next(suit);
				continue;
			}
			else{
				cards.add(new Card(c, suit));
			}
		}
		
		calculatePointsAndBalance();
	}
	
	public void calculatePointsAndBalance(){
		
		//Calculate hand points
		orig_points = 0;
		for (Card c : cards){
			CardValue cv = c.getValue();
			switch(cv){
			case ACE: orig_points += 1;
			case KING: orig_points += 1;
			case QUEEN: orig_points += 1;
			case JACK: orig_points += 1;
			default: break;
			}
		}
		
		//Calculate balance
		boolean found_one_doubleton = false;
		boolean isBalanced = true;
		for (Suit suit1 : Suit.values()){
			int num_in_suit = getNumInSuit(suit1);
			
			if (num_in_suit < 2){
				isBalanced = false;
				break;
			}
			else if (num_in_suit == 2){
				if (found_one_doubleton){
					isBalanced = false;
					break;
				}
				else found_one_doubleton = true;
			}
		}
		orig_balanced = isBalanced;
	}
	
	public boolean contains(Card card){
		for(Card c : cards){
			if(c.getValue() == card.getValue() && c.getSuit() == card.getSuit())
				return true;
		}
		return false;
	}
	
	private Suit next(Suit suit){
		switch(suit){
		case SPADE: return Suit.HEART;
		case HEART: return Suit.DIAMOND;
		case DIAMOND: return Suit.CLUB;
		default: return null;
		}
	}

	public ArrayList<Card> getCards() {
		return cards;
	}

	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}
	
	public boolean containsSuit(Suit suit){
		for(Card c : cards){
			if (c.getSuit() == suit) return true;
		}
		return false;
	}
	
	public ArrayList<Card> getSuitCards(Suit suit){
		ArrayList<Card> result = new ArrayList<Card>();
		for (Card c : cards){
			if (c.getSuit() == suit) result.add(c);
		}
		return result;
	}
	
	public int getNumInSuit(Suit suit){
		ArrayList<Card> cards_in_suit = getSuitCards(suit);
		return cards_in_suit.size();
	}
	
	public Suit getLargestSuit(){
		int max_num = 0;
		Suit max_suit = Suit.SPADE;
		
		for (Suit suit : Suit.values()){
			int num = getNumInSuit(suit);
			if (num > max_num){
				max_num = num;
				max_suit = suit;
			}
		}
		
		return max_suit;
	}
	
	public int getPoints(){
		return orig_points;
	}
	
	public boolean isBalanced(){
		return orig_balanced;
	}
	
	public int getNumPlayingTricks(){
		int total = 0;
		
		boolean ace;
		boolean king;
		boolean queen;
		boolean jack;
		
		for(Suit suit : Suit.values()){
			ace = false;
			king = false;
			queen = false;
			jack = false;
			
			for(Card card : getSuitCards(suit)){
				if (card.getValue() == CardValue.ACE) ace = true;
				else if (card.getValue() == CardValue.KING) king = true;
				else if (card.getValue() == CardValue.QUEEN) queen = true;
				else if (card.getValue() == CardValue.JACK) jack = true;
			}
			
			if (ace && king && queen) total += 3;
			else if (ace && king) total += 2;
			else if (ace) total += 1;
			else if (king && queen) total += 1;
			else if (king && queen && jack) total += 2;
		}
		
		return total;
	}
	
	public void display(){
		for (int i = 0; i < cards.size(); i+=3){
			Card card = cards.get(i);
			System.out.print(i + ". " + card.toString() + " ");
			
			if ((i + 1) < cards.size()){
				card = cards.get(i + 1);
				System.out.print("\t\t" + (i + 1) + ". " + card.toString() + " ");
			}
			
			if ((i + 2) < cards.size()){
				card = cards.get(i + 2);
				System.out.print("\t\t" + (i + 2) + ". " + card.toString() + " ");
			}
			System.out.println();
		}
	}
	
}
