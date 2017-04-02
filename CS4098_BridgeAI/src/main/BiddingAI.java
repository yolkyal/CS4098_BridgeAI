package main;

import java.util.ArrayList;

import bridge_data_structures.*;
import constraints.*;

public class BiddingAI {
	
	BalanceConstraint balanced = new BalanceConstraint(true);
	BalanceConstraint unbalanced = new BalanceConstraint(false);
	PointConstraint points12to14 = new PointConstraint(12, 14);
	PointConstraint points15to19 = new PointConstraint(15, 19);
	PointConstraint points20to22 = new PointConstraint(20, 22);
	
	PointConstraint points10OrMore = new PointConstraint(10, 100);
	PointConstraint points11OrMore = new PointConstraint(11, 100);
	PointConstraint points12OrMore = new PointConstraint(12, 100);
	NumInASuitConstraint fourCardSuit = new NumInASuitConstraint(4);
	NumInASuitConstraint fiveCardSuit = new NumInASuitConstraint(5);
	NumInASuitConstraint sixCardSuit = new NumInASuitConstraint(6);
	AndConstraint and1 = new AndConstraint(points10OrMore, sixCardSuit);
	AndConstraint and2 = new AndConstraint(points11OrMore, fiveCardSuit);
	AndConstraint and3 = new AndConstraint(points12OrMore, fourCardSuit);
	OrConstraint or1 = new OrConstraint(and1, and2);
	OrConstraint singleSuitBidConditions = new OrConstraint(and3, or1);
	
	PointConstraint points16to22 = new PointConstraint(16, 22);
	NumPlayingTricksConstraint eightPlayingTricks = new NumPlayingTricksConstraint(8);
	NumSuitsOfSizeConstraint two5CardSuits = new NumSuitsOfSizeConstraint(2, 5);
	OrConstraint or2 = new OrConstraint(sixCardSuit, two5CardSuits);
	AndConstraint and4 = new AndConstraint(points16to22, or2);
	AndConstraint twoSuitBidConditions = new AndConstraint(eightPlayingTricks, and4);
	
	PointConstraint points6to9 = new PointConstraint(6, 9);
	NumInASuitConstraint sevenCardSuit = new NumInASuitConstraint(7);
	AndConstraint threeSuitBidConditions = new AndConstraint(points6to9, sevenCardSuit);
	
	NumInASuitConstraint eightCardSuit = new NumInASuitConstraint(8);
	AndConstraint fourSuitBidConditions = new AndConstraint(points6to9, eightCardSuit);
	
	PointConstraint points23OrMore = new PointConstraint(23, 100);
	NumPlayingTricksConstraint tenPlayingTricks = new NumPlayingTricksConstraint(10);
	AndConstraint twoClubOpeningBidConditions = new AndConstraint(points23OrMore, tenPlayingTricks);
	
	
	public static Contract getBid(int position, Hand hand, ArrayList<PlayerConstraint> ls_player_constraints){
		
		
		
		return null;	
	}
	
	private Contract getOpeningBid(Hand hand, int position){
		
		if (balanced.satisfiedBy(hand)){
			
			//1NT
			if(points12to14.satisfiedBy(hand)){
				return new Contract(1, null, position);
			}
			
			if(points15to19.satisfiedBy(hand)){
				//Consider whether we want this...
			}
			
			//2NT
			if(points20to22.satisfiedBy(hand)){
				return new Contract(2, null, position);
			}
			
		}
		else{
			//Unbalanced
			
			//Two-of-a-suit
			if(twoSuitBidConditions.satisfiedBy(hand)){
				Suit best_suit = hand.getLargestSuit();
				return new Contract(2, best_suit, position);
			}
			
			//One-of-a-suit
			if(singleSuitBidConditions.satisfiedBy(hand)){
				Suit best_suit = hand.getLargestSuit();
				return new Contract(1, best_suit, position);
			}
			
			//Three-of-a-suit
			if(threeSuitBidConditions.satisfiedBy(hand)){
				Suit best_suit = hand.getLargestSuit();
				return new Contract(3, best_suit, position);
			}
			
			//Four-of-a-suit
			if(fourSuitBidConditions.satisfiedBy(hand)){
				Suit best_suit = hand.getLargestSuit();
				return new Contract(4, best_suit, position);
			}
			
			if(twoClubOpeningBidConditions.satisfiedBy(hand)){
				return new Contract(2, Suit.CLUB, position);
			}
			
			
		}
		
		return null;
	}
	
}
