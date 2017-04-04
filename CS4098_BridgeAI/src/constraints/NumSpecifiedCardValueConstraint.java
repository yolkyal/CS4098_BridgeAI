package constraints;

import bridge_data_structures.Card;
import bridge_data_structures.CardValue;
import bridge_data_structures.Hand;

public class NumSpecifiedCardValueConstraint extends Constraint {

	private int number;
	private CardValue card_value;
	
	public NumSpecifiedCardValueConstraint(int number, CardValue card_value){
		this.number = number;
		this.card_value = card_value;
	}
	
	@Override
	public boolean satisfiedBy(Hand hand) {
		int total = 0;
		for(Card c : hand.getCards()){
			if (c.getValue() == card_value) total+= 1;
		}
		
		return total >= number;
	}

}
