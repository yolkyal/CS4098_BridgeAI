package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import bridge_data_parser.BridgeDataParser;
import bridge_data_structures.*;
import neural_network.NeuralNetwork;
import user_io.UserIO;

public class BridgeAI {
	
	public static void main(String[] args) {
		UserIO.open();
		
		NeuralNetwork nn = new NeuralNetwork(4, 4, 1, 4);
		JSONObject nn_json = nn.getJSONObject();
		
		try (FileWriter file = new FileWriter("nn.json")) {
			file.write(nn_json.toString());
			System.out.println("Net saved.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JSONObject j_obj = null;
		File f = new File("nn.json");
		try{
			Scanner scanner = new Scanner(f);
			String json_string = scanner.next();
			scanner.close();	
			j_obj = new JSONObject(json_string);
			System.out.println("Net loaded.");
		}
		catch(FileNotFoundException e){
			System.err.println("File not found: " + e.getMessage());
		}
		catch(JSONException e){
			 System.err.println("JSONException: " + e.getMessage());
		}
		
		NeuralNetwork nn2 = new NeuralNetwork(j_obj);
		
		//BridgeDataParser bdp = new BridgeDataParser("list10.txt");
		//bdp.getTrainingData();
		
		UserIO.close();
	}

}
