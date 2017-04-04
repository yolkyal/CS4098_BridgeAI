package constraints;

import bridge_data_structures.Hand;
import bridge_data_structures.Suit;

public class BalanceConstraint extends Constraint {
	
	private boolean isBalanced;
	
	public BalanceConstraint(boolean isBalanced){
		this.isBalanced = isBalanced;
	}
	
	@Override
	public boolean satisfiedBy(Hand hand) {
		if(isBalanced){
			return hand.isBalanced();
		}
		else return !hand.isBalanced();
	}
	
}
