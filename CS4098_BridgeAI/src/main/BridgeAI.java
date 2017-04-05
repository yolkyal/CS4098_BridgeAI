package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import bridge_data_parser.BridgeDataParser;
import bridge_data_parser.PlayTrainingData;
import bridge_data_parser.PlayTrainingRound;
import bridge_data_parser.TrainingData;
import bridge_data_structures.*;
import neural_network.NeuralNetwork;
import neural_network.Vector52;
import user_io.UserIO;

public class BridgeAI {
	
	public static void main(String[] args) {
		UserIO.open();
		
		BridgeDataParser bdp = new BridgeDataParser("list10.txt");
		ArrayList<TrainingData> data = bdp.getTrainingData();
		
		PlayTrainingRound ptr = new PlayTrainingRound(data.get(9));
		ptr.print();

		UserIO.close();
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
