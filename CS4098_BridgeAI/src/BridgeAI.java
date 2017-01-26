
public class BridgeAI {
	
	public static void main(String[] args) {
		Deck deck = new Deck();
		Card[] cards = deck.getCards();
		for(int i = 0; i < cards.length; i++){
			System.out.println(cards[i].toString());
		}
	}

}
