package bridge_data_parser;

import bridge_data_structures.*;
import java.util.ArrayList;

public class TrainingData {
	Hand[] hands;
	int starting_position;
	Contract best_contract;
	ArrayList<Card> play_sequence;
	
	public TrainingData(Hand[] hands, int starting_position, Contract best_contract, ArrayList<Card> play_sequence) {
		this.hands = hands;
		this.starting_position = starting_position;
		this.best_contract = best_contract;
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

	public Contract getBest_contract() {
		return best_contract;
	}

	public void setBest_contract(Contract best_contract){
		this.best_contract = best_contract;
	}

	public ArrayList<Card> getPlay_sequence() {
		return play_sequence;
	}

	public void setPlay_sequence(ArrayList<Card> play_sequence) {
		this.play_sequence = play_sequence;
	}
	
}
