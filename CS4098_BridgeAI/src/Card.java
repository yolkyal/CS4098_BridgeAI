
public class Card {
	private CardValue value;
	private Suit suit;
	
	public Card(CardValue value, Suit suit) {
		this.value = value;
		this.suit = suit;
	}

	public CardValue getValue() {
		return value;
	}

	public void setCardValue(CardValue value) {
		this.value = value;
	}

	public Suit getSuit() {
		return suit;
	}

	public void setSuit(Suit suit) {
		this.suit = suit;
	}
	
	public String toString(){
		String s = value.name() + " " + suit.name();
		return s;
	}
	
}
