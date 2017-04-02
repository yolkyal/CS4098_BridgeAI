package constraints;

import bridge_data_structures.Hand;

public class NumPlayingTricksConstraint extends Constraint{
	
	private int num_playing_tricks;
	
	public NumPlayingTricksConstraint(int num_playing_tricks){
		this.num_playing_tricks = num_playing_tricks;
	}

	@Override
	public boolean satisfiedBy(Hand hand) {
		return hand.getNumPlayingTricks() >= num_playing_tricks;
	}

}
