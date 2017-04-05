package main;

import java.util.ArrayList;

import bridge_data_structures.Card;
import bridge_data_structures.Deck;
import bridge_data_structures.Hand;

public class RDealGenerator {
	
	public static Hand[] generateRDeals(ArrayList<PlayerConstraint> playerConstraints){
		Deck deck = new Deck();
		deck.shuffle();
		Hand[] hands = deck.getHands();
		
		boolean validHands = false;
		
		while(!validHands){
			//Simulated annealing
			switchTwoRandomCards(hands);
			
			validHands = true;
			for(PlayerConstraint pc : playerConstraints){
				int position = pc.getPosition();
				if(!pc.getConstraint().satisfiedBy(hands[position])){
					validHands = false;
				}
			}
		}
		
		return hands;
	}
	
	private static void switchTwoRandomCards(Hand[] hands){
		//Get two unique card indexes
		int rand1 = (int)(Math.random() * 52);
		int rand2 = rand1;
		while(rand2 == rand1){
			rand2 = (int)(Math.random() * 52);
		}
		
		Card card1 = hands[(rand1 / 13)].getCards().get(rand1 % 13);
		Card card2 = hands[(rand2 / 13)].getCards().get(rand2 % 13);
		Card tmp = card1;
		hands[(rand1 / 13)].getCards().set(rand1 % 13, card2);
		hands[(rand2 / 13)].getCards().set(rand2 % 13, tmp);
	}
	
}
