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
	
	public void print(){
		for (int i = 0; i < cards.size(); i++){
			Card card = cards.get(i);
			System.out.print(i + ". " + card.toString() + " ");
		}
	}
	
}
