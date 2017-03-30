package bridge_data_structures;

import java.util.ArrayList;

public class Card {
	private CardValue value;
	private Suit suit;
	
	public Card(CardValue value, Suit suit) {
		this.value = value;
		this.suit = suit;
	}
	
	public Card(char c, Suit suit){
		this.value = convertToCardValue(c);
		this.suit = suit;
	}
	
	public Card(char c_suit, char c_value){
		this.value = convertToCardValue(c_value);
		this.suit = convertToSuit(c_suit);
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
	
	private static CardValue convertToCardValue(char c){
		switch(c){
		case 'A': 	return CardValue.ACE;
		case '2': 	return CardValue.TWO;
		case '3':	return CardValue.THREE;
		case '4':	return CardValue.FOUR;
		case '5':	return CardValue.FIVE;
		case '6':	return CardValue.SIX;
		case '7':	return CardValue.SEVEN;
		case '8':	return CardValue.EIGHT;
		case '9':	return CardValue.NINE;
		case 'T':	return CardValue.TEN;
		case 'J':	return CardValue.JACK;
		case 'Q':	return CardValue.QUEEN;
		case 'K':	return CardValue.KING;
		default: return null;
		}
	}
	
	private static Suit convertToSuit(char c){
		switch(c){
		case 'S': return Suit.SPADE;
		case 'H': return Suit.HEART;
		case 'D': return Suit.DIAMOND;
		case 'C': return Suit.CLUB;
		default: return null;
		}
	}
	
	public static ArrayList<Card> parseCardSequence(String cards_string){
		ArrayList<Card> cards = new ArrayList<Card>();
		
		for(int i = 0; i < cards_string.length(); i+=2){
			char c_suit = cards_string.charAt(i);
			char c_value = cards_string.charAt(i + 1);
			Card card = new Card(c_suit, c_value);
			cards.add(card);
		}
		
		return cards;
	}
}
