package constraints;

import bridge_data_structures.Hand;

public class PointConstraint extends Constraint{
	
	int lower_bound, upper_bound;
	
	public PointConstraint(int lower_bound, int upper_bound){
		this.lower_bound = lower_bound;
		this.upper_bound = upper_bound;
	}

	@Override
	public boolean satisfiedBy(Hand hand) {
		int hand_points = hand.getPoints();
		
		if (lower_bound <= hand_points && upper_bound >= hand_points)
			return true;
		else return false;
	}

}
