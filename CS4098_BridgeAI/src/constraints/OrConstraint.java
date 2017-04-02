package constraints;

import bridge_data_structures.Hand;

public class OrConstraint extends Constraint {

	private Constraint c1, c2;
	
	public OrConstraint(Constraint c1, Constraint c2){
		this.c1 = c1;
		this.c2 = c2;
	}
	
	@Override
	public boolean satisfiedBy(Hand hand) {
		
		return (c1.satisfiedBy(hand) || c2.satisfiedBy(hand));
				
	}

}
