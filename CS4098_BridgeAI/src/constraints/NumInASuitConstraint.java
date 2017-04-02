package constraints;

import bridge_data_structures.Hand;
import bridge_data_structures.Suit;

public class NumInASuitConstraint extends Constraint{

	private int min, max;
	
	public NumInASuitConstraint(int min, int max){
		this.min = min;
		this.max = max;
	}
	
	@Override
	public boolean satisfiedBy(Hand hand) {
		
		for (Suit suit : Suit.values()){
			int num_suit = hand.getNumInSuit(suit);
			if (num_suit >= min && num_suit <= max) return true;
		}
		
		return false;
	}

}
