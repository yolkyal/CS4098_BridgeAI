package bridge_data_parser;

import java.util.ArrayList;

import bridge_data_structures.Card;
import bridge_data_structures.Hand;
import bridge_data_structures.Position;
import bridge_data_structures.Suit;
import neural_network.Vector156;
import neural_network.Vector52;

public class PlayTrainingRound {
	
	TrainingData training_data;
	ArrayList<PlayTrainingData> al_play_training_data;
	int starting_index;
	int first_hand_position;
	
	public PlayTrainingRound(TrainingData training_data){
		this.training_data = training_data;
		calculateListOfPlayTrainingData();
		
		
	}
	
	private ArrayList<PlayTrainingData> calculateListOfPlayTrainingData(){
		al_play_training_data = new ArrayList<PlayTrainingData>();
		
		ArrayList<Card> play_sequence = training_data.getPlay_sequence();
		starting_index = training_data.getStarting_index();
		first_hand_position = training_data.getFirst_hand_position();
		
		//Change hands into vectors
		Hand[] hands = training_data.getHands();
		Vector52[] v_hands = new Vector52[4];
		
		int hand_position = training_data.getFirst_hand_position();
		for(int i = 0; i < 4; i++){
			v_hands[hand_position] = new Vector52(hands[i]);
			hand_position = Position.getLeft(hand_position);
		}
		
		int position = training_data.getFirst_hand_position();
		for(int i = 0; i < training_data.getStarting_index(); i++){
			position = Position.getLeft(position);
		}
		
		//Run through every play instance in this training data
		int trick_turn = 0;
		int winning_position = position;
		Card winning_card = null;
		Suit trick_suit = null;
		Vector52 cards_played_this_round = new Vector52();
		Vector52 cards_played_this_trick = new Vector52();
		for(Card card : play_sequence){
			PlayTrainingData td = new PlayTrainingData();
			
			//Construct play-training data object from each play
			td.setHand(v_hands[position].deepCopy());
			td.setCards_played_this_round(cards_played_this_round.deepCopy());
			td.setCards_played_this_trick(cards_played_this_trick.deepCopy());
			td.setBest_card(new Vector52(card));	
			al_play_training_data.add(td);
			
			v_hands[position].takeCard(card);
			cards_played_this_round.addCard(card);
			cards_played_this_trick.addCard(card);
			
			if(trick_suit == null){
				trick_suit = card.getSuit();
			}
			
			if(card.getSuit() == trick_suit && card.isHigherThan(winning_card)){
				winning_card = card;
				winning_position = position;
			}
			
			trick_turn += 1;
			if(trick_turn == 4){
				position = winning_position;
				cards_played_this_trick = new Vector52();
				winning_card = null;
				trick_suit = null;
				trick_turn = 0;
			}
			else{
				position = Position.getLeft(position);
			}
		}
		return al_play_training_data;
	}
	
	public ArrayList<PlayTrainingData> getListPlayTrainingData(){
		return al_play_training_data;
	}
	
	public void print(){
		System.out.println("Starting index: " + starting_index);
		System.out.println("First hand pos: " + first_hand_position);
		
		for(PlayTrainingData ptd : al_play_training_data){
			ptd.getHand().print();
			ptd.getCards_played_this_round().print();
			ptd.getCards_played_this_trick().print();
			ptd.getBest_card().print();
			System.out.println();
		}
	}
	
}
