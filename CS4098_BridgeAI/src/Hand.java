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
		for (int i = 0; i < cards.size(); i++){
			Card card = cards.get(i);
			System.out.println(i + ". " + card.toString() + " ");
		}
	}
	
}
