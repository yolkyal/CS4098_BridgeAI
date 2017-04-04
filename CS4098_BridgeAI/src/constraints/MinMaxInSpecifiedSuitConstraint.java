package constraints;

import bridge_data_structures.Hand;
import bridge_data_structures.Suit;

public class MinMaxInSpecifiedSuitConstraint extends Constraint{

	private int min;
	private int max;
	private Suit suit;
	
	public MinMaxInSpecifiedSuitConstraint(int min, int max, Suit suit){
		this.min = min;
		this.max = max;
		this.suit = suit;
	}
	
	@Override
	public boolean satisfiedBy(Hand hand) {

		int num_in_suit = hand.getNumInSuit(suit);
		
		return (num_in_suit >= min && num_in_suit <= max);
	}

}
