package bridge_data_parser;

import bridge_data_structures.*;
import java.util.ArrayList;

public class TrainingData {
	Hand[] hands;
	int starting_position;
	Contract ns_best_contract;
	Contract ew_best_contract;
	ArrayList<Card> play_sequence;
	
	public TrainingData(Hand[] hands, int starting_position, Contract ns_best_contract, Contract ew_best_contract,
			ArrayList<Card> play_sequence) {
		this.hands = hands;
		this.starting_position = starting_position;
		this.ns_best_contract = ns_best_contract;
		this.ew_best_contract = ew_best_contract;
		this.play_sequence = play_sequence;
	}
	
	public TrainingData(){
		
	}

	public Hand[] getHands() {
		return hands;
	}

	public void setHands(Hand[] hands) {
		this.hands = hands;
	}

	public int getStarting_position() {
		return starting_position;
	}

	public void setStarting_position(int starting_position) {
		this.starting_position = starting_position;
	}

	public Contract getNs_best_contract() {
		return ns_best_contract;
	}

	public void setNs_best_contract(Contract ns_best_contract) {
		this.ns_best_contract = ns_best_contract;
	}

	public Contract getEw_best_contract() {
		return ew_best_contract;
	}

	public void setEw_best_contract(Contract ew_best_contract) {
		this.ew_best_contract = ew_best_contract;
	}

	public ArrayList<Card> getPlay_sequence() {
		return play_sequence;
	}

	public void setPlay_sequence(ArrayList<Card> play_sequence) {
		this.play_sequence = play_sequence;
	}
	
}
