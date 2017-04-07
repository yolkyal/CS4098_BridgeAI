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
	
	final static int NUM_RDEALS = 100;
	static NeuralNetwork playNeuralNetwork;
	static NeuralNetwork bidNeuralNetwork;
	
	public static void main(String[] args) {
		
		//trainNeuralNetworks();
		
		JSONObject bid_nn_json = null;
		File f = new File("bidNeuralNet.json");
		try{
			Scanner scanner = new Scanner(f);
			String json_string = scanner.next();
			scanner.close();	
			bid_nn_json = new JSONObject(json_string);
			System.out.println("Net loaded.");
		}
		catch(FileNotFoundException e){
			System.err.println("File not found: " + e.getMessage());
		}
		catch(JSONException e){
			 System.err.println("JSONException: " + e.getMessage());
		}
		
		JSONObject play_nn_json = null;
		File f2 = new File("playNeuralNet.json");
		try{
			Scanner scanner = new Scanner(f2);
			String json_string = scanner.next();
			scanner.close();	
			play_nn_json = new JSONObject(json_string);
			System.out.println("Net loaded.");
		}
		catch(FileNotFoundException e){
			System.err.println("File not found: " + e.getMessage());
		}
		catch(JSONException e){
			 System.err.println("JSONException: " + e.getMessage());
		}
		
		bidNeuralNetwork = new NeuralNetwork(bid_nn_json);
		playNeuralNetwork = new NeuralNetwork(play_nn_json);
		
		
		UserIO.open();
		
		Round round = null;
		Contract contract = null;
		while(contract == null){
			round = new Round(1);
			contract = round.runAuction(Position.NORTH);
			
			if(contract == null){
				System.out.println("No contract reached, dealing new hands...");
				System.out.println("------------------");
			}
		}
		round.play(contract);
		
		UserIO.close();
	}
	
	public static void trainNeuralNetworks(){
		//DATA-PARSING---------------------------------------------------------
		BridgeDataParser bdp = new BridgeDataParser("list1000.txt");
		ArrayList<TrainingData> al_td = bdp.getTrainingData();
		
		ArrayList<PlayTrainingRound> al_ptr = new ArrayList<PlayTrainingRound>();
		ArrayList<BidTrainingData> al_btd = new ArrayList<BidTrainingData>();
		for(TrainingData td : al_td){
			al_ptr.add(new PlayTrainingRound(td));
			al_btd.add(new BidTrainingData(td));
		}
		
		//BIDDING-NEURAL-NETWORK-----------------------------------------------
		bidNeuralNetwork = new NeuralNetwork(208, 39, 2, 16);
		
		System.out.println("Bid training starting...");
		int test_number = 1;
		int total_tests = 10001;
		for(BidTrainingData btd : al_btd){
			bidNeuralNetwork.train(btd.getInputVector().getDoubleVector(), 
			btd.getOutputVector().getDoubleVector(), 
			test_number, total_tests);
			test_number++;
		}
		System.out.println("Bid training finished.");
		
		//PLAY-NEURAL-NETWORK--------------------------------------------------
		playNeuralNetwork = new NeuralNetwork(156, 52, 2, 16);
		
		System.out.println("Play training starting...");
		
		total_tests = 37492;
		test_number = 1;
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
		
		
		//JSON-SAVING----------------------------------------------------------
		
		JSONObject bid_nn_json = bidNeuralNetwork.getJSONObject(); 
		
		try (FileWriter file = new FileWriter("bidNeuralNet.json")){
			file.write(bid_nn_json.toString());
		System.out.println("Bidding net saved.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JSONObject play_nn_json = playNeuralNetwork.getJSONObject(); 
		
		try (FileWriter file = new FileWriter("playNeuralNet.json")){
			file.write(play_nn_json.toString());
		System.out.println("Playing net saved.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static Card getPlay(Hand hand, ArrayList<Card> cards_played_this_round, ArrayList<Card> cards_played_this_trick){
		Vector52 v_hand = new Vector52(hand);
		Vector52 round_cards = new Vector52(cards_played_this_round);
		Vector52 trick_cards = new Vector52(cards_played_this_trick);
		
		Vector156 v_input = new Vector156(v_hand, round_cards, trick_cards);
		int v52_card_index = playNeuralNetwork.getBestCardIndex(v_input.getDoubleVector());
		return new Card(v52_card_index);
	}
	
	
	public static Contract getBid(Contract cur_contract, ArrayList<PlayerConstraint> ls_constraints, int position){
		ArrayList<Hand[]> r_hands = RDealGenerator.generateRDeals(ls_constraints, NUM_RDEALS);
		ArrayList<Contract> contracts = new ArrayList<Contract>();
		
		for(Hand[] hands : r_hands){
			Vector52 v1 = new Vector52(hands[0]);
			Vector52 v2 = new Vector52(hands[1]);
			Vector52 v3 = new Vector52(hands[2]);
			Vector52 v4 = new Vector52(hands[3]);
			
			Vector208 input = new Vector208(v1, v2, v3, v4);
			Vector39 v_contract = bidNeuralNetwork.getGreatestContractPair(input.getDoubleVector());
			contracts.add(v_contract.getContract());
		}
		
		boolean spades = false;
		boolean hearts = false;
		boolean diamonds = false;
		boolean clubs = false;
		for(Contract contract : contracts){
			if (contract.getSuit() == Suit.SPADE)
				spades = true;
			else if (contract.getSuit() == Suit.HEART)
				hearts = true;
			else if (contract.getSuit() == Suit.DIAMOND)
				diamonds = true;
			else if (contract.getSuit() == Suit.CLUB);
		}
		
		if (spades && hearts && diamonds && clubs)
			return new Contract(-1, null, 0);
		else if(spades){
			return new Contract(cur_contract.getNumber() + 1, Suit.SPADE, position);
		}
		else if(hearts){
			return new Contract(cur_contract.getNumber() + 1, Suit.HEART, position);
		}
		else if(diamonds){
			return new Contract(cur_contract.getNumber() + 1, Suit.DIAMOND, position);
		}
		else return new Contract(cur_contract.getNumber() + 1, Suit.CLUB, position);
	}

}
