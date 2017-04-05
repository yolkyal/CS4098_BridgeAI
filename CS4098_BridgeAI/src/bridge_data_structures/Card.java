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
		if (card == null) return true;
		if(card.getValue() == value) return false;
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
	
	public static CardValue convertToCardValue(char c){
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
	
	public static Suit convertToSuit(char c){
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
	
	public int getVector52Index(){
		return getSuitAddition(suit) + getCardValueAddition(value);
	}
	
	private int getSuitAddition(Suit suit){
		switch(suit){
		case SPADE: return 0;
		case HEART: return 13;
		case DIAMOND: return 26;
		case CLUB: return 39;
		default: return -1;
		}
	}
	
	private int getCardValueAddition(CardValue cv){
		switch(cv){
		case TWO: 	return 12;
		case THREE: return 11;
		case FOUR:	return 10;
		case FIVE:	return 9;
		case SIX:	return 8;
		case SEVEN:	return 7;
		case EIGHT:	return 6;
		case NINE:	return 5;
		case TEN:	return 4;
		case JACK:	return 3;
		case QUEEN:	return 2;
		case KING:	return 1;
		case ACE: return 0;
		default: return -1;
		}
	}
}
