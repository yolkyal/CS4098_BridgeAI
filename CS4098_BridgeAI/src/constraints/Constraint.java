package constraints;

import bridge_data_structures.Hand;

public abstract class Constraint {
	
	public abstract boolean satisfiedBy(Hand hand);
	
}
