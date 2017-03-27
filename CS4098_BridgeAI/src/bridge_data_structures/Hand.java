package bridge_data_structures;
import java.util.ArrayList;

public class Hand {
	private ArrayList<Card> cards;
	
	public Hand(ArrayList<Card> cards){
		this.cards = cards;
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
