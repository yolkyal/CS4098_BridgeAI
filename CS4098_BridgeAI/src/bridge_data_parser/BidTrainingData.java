package bridge_data_parser;

import bridge_data_structures.Hand;
import bridge_data_structures.Position;
import neural_network.Vector208;
import neural_network.Vector39;
import neural_network.Vector52;

public class BidTrainingData {
	int starting_position;
	Vector52[] v_hands;
	Vector39 best_contract;
	
	public BidTrainingData(TrainingData training_data){
		
		//Change hands into vectors
		Hand[] hands = training_data.getHands();
		v_hands = new Vector52[4];
		
		int hand_position = training_data.getFirst_hand_position();
		for(int i = 0; i < 4; i++){
			v_hands[hand_position] = new Vector52(hands[i]);
			hand_position = Position.getLeft(hand_position);
		}
		
		best_contract = new Vector39(training_data.getBest_contract());
	}
	
	public Vector208 getInputVector(){
		return new Vector208(v_hands[0], v_hands[1], v_hands[2], v_hands[3]);
	}
	
	public Vector39 getOutputVector(){
		return best_contract;
	}

	public Vector39 getBest_contract() {
		return best_contract;
	}

	public void setBest_contract(Vector39 best_contract) {
		this.best_contract = best_contract;
	}
	
}
