package constraints;

import bridge_data_structures.Hand;
import bridge_data_structures.Suit;

public class NumInASuitConstraint extends Constraint{

	private int number;
	
	public NumInASuitConstraint(int number){
		this.number = number;
	}
	
	@Override
	public boolean satisfiedBy(Hand hand) {
		
		for (Suit suit : Suit.values()){
			int num_suit = hand.getNumInSuit(suit);
			if (num_suit >= number) return true;
		}
		
		return false;
	}

}
