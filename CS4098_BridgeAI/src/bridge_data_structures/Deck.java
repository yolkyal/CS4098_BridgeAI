package bridge_data_structures;
import java.util.ArrayList;

public class Deck {
	private Card[] cards;

	public Deck() {
		cards = new Card[52];
		int i = 0;
		for(CardValue cv : CardValue.values()){
			for (Suit s : Suit.values()){
				Card card = new Card(cv, s);
				cards[i] = card;
				i++;
			}
		}
	}
	
	public void shuffle(){
		ArrayList<Card> al_cards = new ArrayList<Card>();
		for(int i = 0; i < cards.length; i++){
			al_cards.add(cards[i]);	
		}
		
		int i = 0;
		while(al_cards.size() > 0){
			int rand = (int)(Math.random() * al_cards.size());
			cards[i] = al_cards.get(rand);
			al_cards.remove(rand);
			i++;
		}
	}
	
	public Hand[] getHands(){
		Hand[] hands = new Hand[4];
		
		for(int i = 0; i < 4; i++){
			ArrayList<Card> h_cards = new ArrayList<Card>();
			for(int j = 0; j < 13; j++){
				h_cards.add(cards[i * 13 + j]);
			}
			hands[i] = new Hand(h_cards);
		}
		
		return hands;
	}

	public Card[] getCards() {
		return cards;
	}

	public void setCards(Card[] cards) {
		this.cards = cards;
	}
	
}