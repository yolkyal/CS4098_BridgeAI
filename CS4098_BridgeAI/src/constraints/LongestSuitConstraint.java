package constraints;

import bridge_data_structures.Hand;
import bridge_data_structures.Suit;

public class LongestSuitConstraint extends Constraint {

	Suit longest_suit;
	
	public LongestSuitConstraint(Suit longest_suit){
		this.longest_suit = longest_suit;
	}
	
	@Override
	public boolean satisfiedBy(Hand hand) {
		Suit max_suit = null;
		int max_num = 0;
		boolean multiple_max = false;
		
		for (Suit suit : Suit.values()){
			int num = hand.getNumInSuit(suit);
			if (num > max_num){
				max_suit = suit;
				max_num = num;
				multiple_max = false;
			}
			else if (num == max_num){
				multiple_max = true;
			}
		}
		
		
		return (max_suit == longest_suit) && !multiple_max;
	}

}
