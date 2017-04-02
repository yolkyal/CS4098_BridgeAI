package constraints;

import bridge_data_structures.Hand;
import bridge_data_structures.Suit;

public class NumInSpecifiedSuitConstraint extends Constraint{
	
	private Suit suit;
	private int number;
	
	public NumInSpecifiedSuitConstraint(int number, Suit suit){
		this.suit = suit;
		this.number = number;
	}

	@Override
	public boolean satisfiedBy(Hand hand) {
		
		if (hand.getNumInSuit(suit) >= number) return true;
		
		return false;
	}

}
