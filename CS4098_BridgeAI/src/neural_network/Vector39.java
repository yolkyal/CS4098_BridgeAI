package neural_network;

import bridge_data_structures.Contract;
import bridge_data_structures.Suit;

public class Vector39 {
	private int[] vector;
	int bid_index;
	int position_index;
	
	public Vector39(){
		vector = new int[39];
		bid_index = 0;
		position_index = 0;
	}
	
	public Vector39(Contract contract){
		vector = new int[39];
		setIndexesAndVector(contract);
 	}
	
	private void setIndexesAndVector(Contract contract){
		bid_index = getSuitAddition(contract.getSuit()) + (contract.getNumber() - 1);
		position_index = 35 + contract.getDeclarerPosition();
		vector[bid_index] = 1;
		vector[position_index] = 1;
	}
	
	public Contract getContract(){
		Suit bid_suit = getSuitFromInt(bid_index / 7);
		int bid_number = bid_index % 7;
		int position = position_index - 28;
		
		return new Contract(bid_number, bid_suit, position);
	}
	
	public void setContract(Contract contract){
		setIndexesAndVector(contract);
	}
	
	private int getSuitAddition(Suit suit){
		if(suit == null) return  28;
		
		switch(suit){
		case CLUB: return 0;
		case DIAMOND: return 7;
		case HEART: return 14;
		case SPADE: return 21;
		default: return -1;
		}
	}
	
	private Suit getSuitFromInt(int x){
		switch(x){
		case 0: return Suit.CLUB;
		case 1: return Suit.DIAMOND;
		case 2: return Suit.HEART;
		case 3: return Suit.SPADE;
		default: return null;
		}
	}
}
