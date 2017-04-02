package main;

import constraints.Constraint;

public class PlayerConstraint {
	
	int position;
	Constraint constraint;
	
	public PlayerConstraint(int position, Constraint constraint) {
		this.position = position;
		this.constraint = constraint;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Constraint getConstraint() {
		return constraint;
	}

	public void setConstraint(Constraint constraint) {
		this.constraint = constraint;
	}
	
}
