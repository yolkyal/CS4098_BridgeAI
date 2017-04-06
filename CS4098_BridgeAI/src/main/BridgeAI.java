package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import bridge_data_parser.BidTrainingData;
import bridge_data_parser.BridgeDataParser;
import bridge_data_parser.PlayTrainingData;
import bridge_data_parser.PlayTrainingRound;
import bridge_data_parser.TrainingData;
import bridge_data_structures.*;
import constraints.Constraint;
import neural_network.NeuralNetwork;
import neural_network.Vector156;
import neural_network.Vector208;
import neural_network.Vector39;
import neural_network.Vector52;
import user_io.UserIO;

public class BridgeAI {
	
	final static int NUM_RDEALS = 1000;
	static NeuralNetwork playNeuralNetwork;
	static NeuralNetwork bidNeuralNetwork;
	
	public static void main(String[] args) {
		UserIO.open();
		
		BridgeDataParser bdp = new BridgeDataParser("list1000.txt");
		ArrayList<TrainingData> al_td = bdp.getTrainingData();
		
		ArrayList<PlayTrainingRound> al_ptr = new ArrayList<PlayTrainingRound>();
		ArrayList<BidTrainingData> al_btd = new ArrayList<BidTrainingData>();
		for(TrainingData td : al_td){
			al_ptr.add(new PlayTrainingRound(td));
			al_btd.add(new BidTrainingData(td));
		}
		
		playNeuralNetwork = new NeuralNetwork(156, 52, 2, 16);
		//NeuralNetwork bidNeuralNetwork = new NeuralNetwork(208, 36, 2, 16);
		
		System.out.println("Play training starting...");
		
		int total_tests = 37491;
		int test_number = 0;
		for(PlayTrainingRound ptr : al_ptr){
			ArrayList<PlayTrainingData> al_ptd = ptr.getListPlayTrainingData();
			for(PlayTrainingData ptd : al_ptd){		
				playNeuralNetwork.train(ptd.getInputVector().getDoubleVector(), 
						ptd.getOutputVector().getDoubleVector(), 
						test_number, total_tests);
				
				test_number++;
			}
		}
		System.out.println("Play training completed.");
		
		System.out.println("Play testing starting...");
		int total_rounds = 10;
		test_number = 0;
		for(int i = 0; i < total_rounds; i++){
			PlayTrainingRound ptr = al_ptr.get(i);
			ArrayList<PlayTrainingData> al_ptd = ptr.getListPlayTrainingData();
			for(PlayTrainingData ptd : al_ptd){
				
				double err = playNeuralNetwork.test(ptd.getInputVector().getDoubleVector(), 
						ptd.getOutputVector().getDoubleVector());
				
				System.out.println(test_number + ": " + err);
				
				test_number++;
			}
		}
		System.out.println("Play testing completed.");
		
		

		UserIO.close();
	}
	
	public static Vector52 getPlay(Hand hand, ArrayList<Card> cards_played_this_round, ArrayList<Card> cards_played_this_trick){
		Vector52 v_hand = new Vector52(hand);
		Vector52 round_cards = new Vector52(cards_played_this_round);
		Vector52 trick_cards = new Vector52(cards_played_this_trick);
		
		Vector156 v_input = new Vector156(v_hand, round_cards, trick_cards);
		double[] output = playNeuralNetwork.getOutput(v_input.getDoubleVector());
		Vector52 v_card = new Vector52(output);
		
		return v_card;
	}
	
	public static Contract getBid(Contract cur_contract, ArrayList<PlayerConstraint> ls_constraints){
		ArrayList<Hand[]> r_hands = RDealGenerator.generateRDeals(ls_constraints, NUM_RDEALS);
		ArrayList<Vector39> v_contracts = new ArrayList<Vector39>();
		
		for(Hand[] hands : r_hands){
			Vector52 v1 = new Vector52(hands[0]);
			Vector52 v2 = new Vector52(hands[1]);
			Vector52 v3 = new Vector52(hands[2]);
			Vector52 v4 = new Vector52(hands[3]);
			
			Vector208 input = new Vector208(v1, v2, v3, v4);
			double[] output = bidNeuralNetwork.getOutput(input.getDoubleVector());
			v_contracts.add(new Vector39(output));
		}
		
		//Judgement of which is best...
		
		return cur_contract;
	}
	
//	Hand hand = new Hand("AKQ.432.AK.A543");
//	Vector52 vect52 = new Vector52(hand);
//	int[] vector = vect52.getVector();
//	for(int i = 0; i < vector.length; i++){
//		System.out.print(vector[i]);
//	}
	
//	Deck deck = new Deck();
//	deck.shuffle();
//	Hand[] hands = deck.getHands();
//
//	for(int i = 0; i < 5; i++){
//		for(int j = 0; j < 4; j++){
//			System.out.println("\n" + j);
//			hands[j].display();
//			
//		}
//		for(int k = 0; k < 2; k++){
//			RDealGenerator.switchTwoRandomCards(hands);
//		}
//	}
	
	
//NeuralNetwork nn = new NeuralNetwork(4, 4, 1, 4);
		//JSONObject nn_json = nn.getJSONObject();
		
//try (FileWriter file = new FileWriter("nn.json")) {
//	file.write(nn_json.toString());
//	System.out.println("Net saved.");
//} catch (IOException e) {
//	e.printStackTrace();
//}

//JSONObject j_obj = null;
//File f = new File("nn.json");
//try{
//	Scanner scanner = new Scanner(f);
//	String json_string = scanner.next();
//	scanner.close();	
//	j_obj = new JSONObject(json_string);
//	System.out.println("Net loaded.");
//}
//catch(FileNotFoundException e){
//	System.err.println("File not found: " + e.getMessage());
//}
//catch(JSONException e){
//	 System.err.println("JSONException: " + e.getMessage());
//}

//NeuralNetwork nn2 = new NeuralNetwork(j_obj);

}
