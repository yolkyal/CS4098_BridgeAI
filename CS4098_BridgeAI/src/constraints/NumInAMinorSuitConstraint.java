package constraints;

import bridge_data_structures.Hand;
import bridge_data_structures.Suit;

public class NumInAMinorSuitConstraint extends Constraint{
	
	private int number;
	
	public NumInAMinorSuitConstraint(int number){
		this.number = number;
	}

	@Override
	public boolean satisfiedBy(Hand hand) {
		if (hand.getNumInSuit(Suit.DIAMOND) >= number) return true;
		if (hand.getNumInSuit(Suit.CLUB) >= number) return true;
		
		return false;
	}
}
