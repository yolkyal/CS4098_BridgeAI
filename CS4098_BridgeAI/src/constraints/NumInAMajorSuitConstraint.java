package constraints;

import bridge_data_structures.Hand;
import bridge_data_structures.Suit;

public class NumInAMajorSuitConstraint extends Constraint {
	
	int number;
	
	public NumInAMajorSuitConstraint(int number){
		this.number = number;
	}

	@Override
	public boolean satisfiedBy(Hand hand) {
		if (hand.getNumInSuit(Suit.SPADE) >= number) return true;
		if (hand.getNumInSuit(Suit.HEART) >= number) return true;
		
		return false;
	}

}
