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
		boolean found_one_doubleton = false;
		
		for (Suit suit : Suit.values()){
			int num_in_suit = hand.getNumInSuit(suit);
			
			if (num_in_suit < 2) return !isBalanced;
			else if (num_in_suit == 2){
				if (found_one_doubleton) return !isBalanced;
				else found_one_doubleton = true;
			}
		}
		
		return isBalanced;
	}
	
}
