package neural_network;

import bridge_data_structures.*;

public class Vector52 {
	private int[] vector;
	
	public Vector52(Hand hand){
		vector = new int[52];
		
		for(Card card : hand.getCards()){
			int vector_index = getSuitAddition(card.getSuit()) + getCardValueAddition(card.getValue());
			vector[vector_index] = 1;
		}
	}
	
	public int[] getVector(){
		return vector;
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
