package constraints;

import bridge_data_structures.Card;
import bridge_data_structures.CardValue;
import bridge_data_structures.Hand;

public class SpecifiedCardValueConstraint extends Constraint {

	private CardValue card_value;
	
	public SpecifiedCardValueConstraint(CardValue card_value){
		this.card_value = card_value;
	}
	
	@Override
	public boolean satisfiedBy(Hand hand) {
		
		for(Card c : hand.getCards()){
			if (c.getValue() == card_value) return true;
		}
		
		return false;
	}

}
