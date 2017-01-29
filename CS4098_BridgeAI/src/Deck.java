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
	
	public void deal(Player[] players){
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

	public Card[] getCards() {
		return cards;
	}

	public void setCards(Card[] cards) {
		this.cards = cards;
	}
	
}