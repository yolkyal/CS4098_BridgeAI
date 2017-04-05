package bridge_data_parser;

import neural_network.Vector156;
import neural_network.Vector52;

public class PlayTrainingData {
	Vector52 hand;
	Vector52 cards_played_this_round;
	Vector52 cards_played_this_trick;
	Vector52 best_card;
	
	public PlayTrainingData(){
		hand = new Vector52();
		cards_played_this_round = new Vector52();
		cards_played_this_trick = new Vector52();
		best_card = new Vector52();
	}

	public Vector52 getHand() {
		return hand;
	}
	
	public Vector156 getInputVector(){
		return new Vector156(hand, cards_played_this_round, cards_played_this_trick);
	}
	
	public Vector52 getOutputVector(){
		return best_card;
	}

	public void setHand(Vector52 hand) {
		this.hand = hand;
	}

	public Vector52 getCards_played_this_round() {
		return cards_played_this_round;
	}

	public void setCards_played_this_round(Vector52 cards_played_this_round) {
		this.cards_played_this_round = cards_played_this_round;
	}

	public Vector52 getCards_played_this_trick() {
		return cards_played_this_trick;
	}

	public void setCards_played_this_trick(Vector52 cards_played_this_trick) {
		this.cards_played_this_trick = cards_played_this_trick;
	}

	public Vector52 getBest_card() {
		return best_card;
	}

	public void setBest_card(Vector52 best_card) {
		this.best_card = best_card;
	}

}
