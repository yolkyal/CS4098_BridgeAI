package bridge_data_parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import bridge_data_structures.Card;
import bridge_data_structures.Contract;
import bridge_data_structures.Hand;
import bridge_data_structures.Position;

public class BridgeDataParser {
	
	private String filename;
	
	public BridgeDataParser(String filename){
		this.filename = filename;
	}
	
	public ArrayList<TrainingData> getTrainingData(){
		Scanner scanner = null;
		String line;
		try {
			scanner = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			e.printStackTrace();
		}
		
		ArrayList<TrainingData> al_training_data = new ArrayList<TrainingData>();
		scanner.nextLine(); //Skip the first line...
		
		//while(scanner.hasNextLine()){		
			TrainingData td = new TrainingData();
			
			//Cards in each player's hand
			line = scanner.nextLine();
			String[] hands_string = line.split("\"");
			hands_string = hands_string[1].split(" ");
			
			Hand[] hands = new Hand[4];
			for(int i = 0; i < hands_string.length; i++){
				String s_hand = hands_string[i];
				
				//Conversion of N to NORTH etc.
				if (s_hand.contains(":")) td.setStarting_position(Position.getFromChar(s_hand.charAt(0)));
				
				hands[i] = new Hand(s_hand);
			}
			td.setHands(hands);
			
			scanner.nextLine();
			scanner.nextLine();
			scanner.nextLine();
			
			//Double dummy valuation of best resultant contract
			line = scanner.nextLine();
			String[] valuations = line.split("\"");
			valuations = valuations[4].split("-");
			Contract contract = new Contract(valuations);
			
			//Play sequence of this round
			line = scanner.nextLine();
			String[] play_string = line.split("\"");
			ArrayList<Card> cards = Card.parseCardSequence(play_string[1]);
			
			scanner.nextLine();
		//}
			
		scanner.close();
		
		return null;
	}
	
}
