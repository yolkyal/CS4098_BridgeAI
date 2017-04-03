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
			
			//Two Clubs
			if(twoClubOpeningBidConditions.satisfiedBy(hand)){
				return new Contract(2, Suit.CLUB, position);
			}
			
			
		}
		
		//Pass
		return new Contract(-1, null, position);
	}
	
	PointConstraint points0to10 = new PointConstraint(0, 10);
	PointConstraint points11to12 = new PointConstraint(11, 12);
	PointConstraint points11to19 = new PointConstraint(11, 19);
	PointConstraint points13to18 = new PointConstraint(13, 18);
	PointConstraint points19to20 = new PointConstraint(19, 20);
	PointConstraint points19OrMore = new PointConstraint(19, 100);
	
	NumInAMajorSuitConstraint sixCardMajorSuit = new NumInAMajorSuitConstraint(6);
	NumInAMajorSuitConstraint fiveCardMajorSuit = new NumInAMajorSuitConstraint(5);
	NumInAMajorSuitConstraint fourCardMajorSuit = new NumInAMajorSuitConstraint(4);
	
	private Contract getResponseTo1NT(Hand hand, int position){
		
		if (balanced.satisfiedBy(hand)){
			
			//Pass
			if (points0to10.satisfiedBy(hand)){
				return new Contract(-1, null, position);
			}
			
			//2NT
			if(points11to12.satisfiedBy(hand)){
				return new Contract(2, null, position);
			}
			
			//3NT
			if(points13to18.satisfiedBy(hand)){
				return new Contract(3, null, position);
			}
			
			//4NT
			if(points19to20.satisfiedBy(hand)){
				return new Contract(4, null, position);
			}
			
		}
		else{
			//Unbalanced
			
			if(points0to10.satisfiedBy(hand)){
				Suit best_suit = hand.getLargestSuit();
				if (best_suit != Suit.CLUB)
					return new Contract(2, best_suit, position);
			}
			
			if(points11to19.satisfiedBy(hand)){
				
				if(sixCardMajorSuit.satisfiedBy(hand)){
					
					//BID GAME
					
					Suit best_suit = hand.getLargestSuit();
					return new Contract(4, best_suit, position);
				}
				
				if (fiveCardMajorSuit.satisfiedBy(hand)){
					Suit best_suit = hand.getLargestSuit();
					return new Contract(3, best_suit, position);
				}
				
				if(fourCardMajorSuit.satisfiedBy(hand)){
					return new Contract(2, Suit.CLUB, position);
				}
			}
			
			if(points19OrMore.satisfiedBy(hand)){
				//Look for a slam after finding a fit
			}
			
			
		}
		
		return new Contract(-1, null, position);
	}
	
	PointConstraint points0to3 = new PointConstraint(0, 3);
	PointConstraint points4to10 = new PointConstraint(4, 10);
	NumInAMinorSuitConstraint longMinor = new NumInAMinorSuitConstraint(6);
	OrConstraint threeNTBiddingConditions = new OrConstraint(balanced, longMinor);

	private Contract getResponseTo2NT(Hand hand, int position){
		
		//Pass
		if(points0to3.satisfiedBy(hand)){
			return new Contract(-1, null, position);
		}
		
		if(points4to10.satisfiedBy(hand)){
			
			if(threeNTBiddingConditions.satisfiedBy(hand)){
				return new Contract(3, null, position);
			}
			
			if(sixCardMajorSuit.satisfiedBy(hand)){
				
				//BID GAME
				
				Suit best_suit = hand.getLargestSuit();
				return new Contract(4, best_suit, position);
			}
			
			if (fiveCardMajorSuit.satisfiedBy(hand)){
				Suit best_suit = hand.getLargestSuit();
				return new Contract(3, best_suit, position);
			}
			
			if(fourCardMajorSuit.satisfiedBy(hand)){
				return new Contract(3, Suit.CLUB, position);
			}
			
		}
		
		if(points11OrMore.satisfiedBy(hand)){
			//Look for a slam after finding a fit
		}
		
		
		return null;
	}
}
