
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
	
	public boolean isHigherThan(Card card){
		//Returns whether given parameter card has a higher value
		switch(this.value){
		case TWO: 	if (card.getValue() == CardValue.THREE) return false;
		case THREE: if (card.getValue() == CardValue.FOUR) return false;
		case FOUR:	if (card.getValue() == CardValue.FIVE) return false;
		case FIVE:	if (card.getValue() == CardValue.SIX) return false;
		case SIX:	if (card.getValue() == CardValue.SEVEN) return false;
		case SEVEN:	if (card.getValue() == CardValue.EIGHT) return false;
		case EIGHT:	if (card.getValue() == CardValue.NINE) return false;
		case NINE:	if (card.getValue() == CardValue.TEN) return false;
		case TEN:	if (card.getValue() == CardValue.JACK) return false;
		case JACK:	if (card.getValue() == CardValue.QUEEN) return false;
		case QUEEN:	if (card.getValue() == CardValue.KING) return false;
		case KING:	if (card.getValue() == CardValue.ACE) return false;
		default: return true;
		}
	}
	
}
