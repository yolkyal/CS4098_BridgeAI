package bridge_data_parser;

import neural_network.Vector32;
import neural_network.Vector52;

public class BidTrainingData {
	int starting_position;
	Vector52 hand1;
	Vector52 hand2;
	Vector52 hand3;
	Vector52 hand4;
	Vector32 best_contract;
	
	public BidTrainingData(TrainingData training_data){
		
		
		hand1 = new Vector52();
		hand2 = new Vector52();
		hand3 = new Vector52();
		hand4 = new Vector52();
		best_contract = new Vector32();
	}

	public Vector52 getHand1() {
		return hand1;
	}

	public void setHand1(Vector52 hand1) {
		this.hand1 = hand1;
	}

	public Vector52 getHand2() {
		return hand2;
	}

	public void setHand2(Vector52 hand2) {
		this.hand2 = hand2;
	}

	public Vector52 getHand3() {
		return hand3;
	}

	public void setHand3(Vector52 hand3) {
		this.hand3 = hand3;
	}

	public Vector52 getHand4() {
		return hand4;
	}

	public void setHand4(Vector52 hand4) {
		this.hand4 = hand4;
	}

	public Vector32 getBest_contract() {
		return best_contract;
	}

	public void setBest_contract(Vector32 best_contract) {
		this.best_contract = best_contract;
	}
	
}
