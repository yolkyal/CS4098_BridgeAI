package constraints;

import bridge_data_structures.Hand;
import bridge_data_structures.Suit;

public class NumSuitsOfSizeConstraint extends Constraint {
	
	int number;
	int size;
	
	public NumSuitsOfSizeConstraint(int number, int size){
		this.size = size;
		this.number = number;
	}

	@Override
	public boolean satisfiedBy(Hand hand) {
		int total = 0;
		
		for (Suit suit : Suit.values()){
			int num = hand.getNumInSuit(suit);
			if (num >= size) total += 1;
		}
		
		return (total >= number);
	}

}
