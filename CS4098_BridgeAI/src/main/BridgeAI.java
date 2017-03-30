package main;

import bridge_data_parser.BridgeDataParser;
import bridge_data_structures.*;
import user_io.UserIO;

public class BridgeAI {
	
	public static void main(String[] args) {
		UserIO.open();
		
		//Round round = new Round(1);
		//Contract contract = round.runAuction(Position.NORTH);
		//round.play(contract);
		
		BridgeDataParser bdp = new BridgeDataParser("list10.txt");
		bdp.getTrainingData();
		
		UserIO.close();
	}

}
