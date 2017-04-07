package bridge_data_parser;

import bridge_data_structures.*;
import java.util.ArrayList;

public class TrainingData {
	Hand[] hands;
	Contract best_contract;
	ArrayList<Card> play_sequence;
	int starting_index;
	int first_hand_position;
	
	public TrainingData(Hand[] hands, int starting_position, Contract best_contract, ArrayList<Card> play_sequence) {
		this.hands = hands;
		this.starting_index = starting_index;
		this.best_contract = best_contract;
		this.play_sequence = play_sequence;
	}
	
	public void print(){
		for(int i = 0; i < hands.length; i++){
			hands[i].display();
		}
		System.out.println("----------------");
		System.out.println(starting_index);
		System.out.println("----------------");
		System.out.println(best_contract.toString());
		System.out.println("----------------");
		for(Card c : play_sequence){
			System.out.println(c.toString());
		}
	}
	
	public TrainingData(){
		
	}

	public Hand[] getHands() {
		return hands;
	}

	public void setHands(Hand[] hands) {
		this.hands = hands;
	}

	public int getStarting_index() {
		return starting_index;
	}

	public void setStarting_index(int starting_index) {
		this.starting_index = starting_index;
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
		
		if(play_sequence.size() == 0) return;
		
		//Set starting position based using first card played
		Card card = play_sequence.get(0);
		for(int i = 0; i < hands.length; i++){
			if(hands[i].contains(card)){
				starting_index = i;
				return;
			}
		}
	}

	public int getFirst_hand_position() {
		return first_hand_position;
	}

	public void setFirst_hand_position(int first_hand_position) {
		this.first_hand_position = first_hand_position;
	}

	
}
