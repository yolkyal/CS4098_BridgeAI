package main;

import java.util.ArrayList;

import bridge_data_structures.Card;
import bridge_data_structures.Deck;
import bridge_data_structures.Hand;

public class RDealGenerator {
	
	public static ArrayList<Hand[]> generateRDeals(ArrayList<PlayerConstraint> player_constraints, int number_of_deals){
		ArrayList<Hand[]> rdeals = new ArrayList<Hand[]>();
		
		for(int i = 0; i < number_of_deals; i++){
			Hand[] rdeal = generateRDeal(player_constraints);
			rdeals.add(rdeal);
		}
		
		return rdeals;
	}
	
	public static Hand[] generateRDeal(ArrayList<PlayerConstraint> playerConstraints){
		Deck deck = new Deck();
		deck.shuffle();
		Hand[] hands = deck.getHands();
		
		boolean validHands = false;
		while(!validHands){
			int num_satisfied = 0;	
			
			//Simulated annealing
			switchTwoRandomCards(hands);
			for(Hand hand : hands){
				hand.calculatePointsAndBalance();
			}
			
			validHands = true;
			for(PlayerConstraint pc : playerConstraints){
				int position = pc.getPosition();
				if(!pc.getConstraint().satisfiedBy(hands[position])){
					validHands = false;
				}
				else{
					num_satisfied += 1;
				}
			}
		}
		
		return hands;
	}
	
	private static void switchTwoRandomCards(Hand[] hands){
		//Get two unique random card indexes
		int rand1 = (int)(Math.random() * 52);
		int rand2 = (int)(Math.random() * 52);
		while(rand2 == rand1){
			rand2 = (int)(Math.random() * 52);
		}
		
		//And swap them
		Card card1 = hands[(rand1 / 13)].getCards().get(rand1 % 13);
		Card card2 = hands[(rand2 / 13)].getCards().get(rand2 % 13);
		Card tmp = card1;
		hands[(rand1 / 13)].getCards().set(rand1 % 13, card2);
		hands[(rand2 / 13)].getCards().set(rand2 % 13, tmp);
	}
	
}
