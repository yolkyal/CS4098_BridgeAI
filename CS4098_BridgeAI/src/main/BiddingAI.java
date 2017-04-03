package main;

import java.util.ArrayList;

import bridge_data_structures.*;
import constraints.*;

public class BiddingAI {
	
	BalanceConstraint balanced = new BalanceConstraint(true);
	BalanceConstraint unbalanced = new BalanceConstraint(false);
	
	PointConstraint points0to3 = new PointConstraint(0, 3);
	PointConstraint points0to5 = new  PointConstraint(0, 5);
	PointConstraint points0to7 = new PointConstraint(0, 7);
	PointConstraint points0to10 = new PointConstraint(0, 10);
	PointConstraint points0to15 = new PointConstraint(0, 15);
	PointConstraint points4to10 = new PointConstraint(4, 10);
	PointConstraint points5to8 = new PointConstraint(5, 8);
	PointConstraint points5OrMore = new PointConstraint(5, 100);
	PointConstraint points6to9 = new PointConstraint(6, 9);
	PointConstraint points8to11 = new PointConstraint(8, 11);
	PointConstraint points8OrMore = new PointConstraint(8, 100);
	PointConstraint points9OrMore = new PointConstraint(9, 100);
	PointConstraint points10to12 = new PointConstraint(10, 12);
	PointConstraint points10OrMore = new PointConstraint(10, 100);
	PointConstraint points11OrMore = new PointConstraint(11, 100);
	PointConstraint points11to12 = new PointConstraint(11, 12);
	PointConstraint points11to19 = new PointConstraint(11, 19);
	PointConstraint points12OrMore = new PointConstraint(12, 100);
	PointConstraint points12to14 = new PointConstraint(12, 14);
	PointConstraint points13to15 = new PointConstraint(13, 15);
	PointConstraint points13to18 = new PointConstraint(13, 18);
	PointConstraint points15to19 = new PointConstraint(15, 19);
	PointConstraint points16to22 = new PointConstraint(16, 22);
	PointConstraint points16OrMore = new PointConstraint(16, 100);
	PointConstraint points19to20 = new PointConstraint(19, 20);
	PointConstraint points19OrMore = new PointConstraint(19, 100);
	PointConstraint points20to22 = new PointConstraint(20, 22);
	PointConstraint points23OrMore = new PointConstraint(23, 100);
	
	NumInASuitConstraint fourCardSuit = new NumInASuitConstraint(4);
	NumInASuitConstraint fiveCardSuit = new NumInASuitConstraint(5);
	NumInASuitConstraint sixCardSuit = new NumInASuitConstraint(6);
	NumInASuitConstraint sevenCardSuit = new NumInASuitConstraint(7);
	NumInASuitConstraint eightCardSuit = new NumInASuitConstraint(8);
	NumSuitsOfSizeConstraint two5CardSuits = new NumSuitsOfSizeConstraint(2, 5);
	
	NumInAMajorSuitConstraint fourCardMajorSuit = new NumInAMajorSuitConstraint(4);
	NumInAMajorSuitConstraint fiveCardMajorSuit = new NumInAMajorSuitConstraint(5);
	NumInAMajorSuitConstraint sixCardMajorSuit = new NumInAMajorSuitConstraint(6);
	
	NumInAMinorSuitConstraint longMinor = new NumInAMinorSuitConstraint(6);
	
	NumPlayingTricksConstraint eightPlayingTricks = new NumPlayingTricksConstraint(8);
	NumPlayingTricksConstraint tenPlayingTricks = new NumPlayingTricksConstraint(10);
	
	//Specific bid conditions--------------------------------------------------
	AndConstraint and1 = new AndConstraint(points10OrMore, sixCardSuit);
	AndConstraint and2 = new AndConstraint(points11OrMore, fiveCardSuit);
	AndConstraint and3 = new AndConstraint(points12OrMore, fourCardSuit);
	OrConstraint or1 = new OrConstraint(and1, and2);
	OrConstraint singleSuitBidConditions = new OrConstraint(and3, or1);
	
	OrConstraint or2 = new OrConstraint(sixCardSuit, two5CardSuits);
	AndConstraint and4 = new AndConstraint(points16to22, or2);
	AndConstraint twoSuitBidConditions = new AndConstraint(eightPlayingTricks, and4);
	
	AndConstraint threeSuitBidConditions = new AndConstraint(points6to9, sevenCardSuit);
	AndConstraint fourSuitBidConditions = new AndConstraint(points6to9, eightCardSuit);
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
	
	
	
	
	private Contract getResponseToOneOfASuit(Hand hand, int position, Suit bid_suit){
		
		NumInSpecifiedSuitConstraint fourInThisSuit = new NumInSpecifiedSuitConstraint(4, bid_suit);
		
		//Pass
		if(points0to5.satisfiedBy(hand)){
			return new Contract(-1, null, position);
		}
		
		if((bid_suit == Suit.SPADE || bid_suit == Suit.HEART) && fourInThisSuit.satisfiedBy(hand)){
		
			if(points6to9.satisfiedBy(hand)){
				return new Contract(2, bid_suit, position);
			}
		
			if(points10to12.satisfiedBy(hand)){
				return new Contract(3, bid_suit, position);
			}
		
			if(points13to15.satisfiedBy(hand)){
				return new Contract(4, bid_suit, position);
			}
		
		}
		
		//Bids in a new suit
		Suit best_suit = hand.getLargestSuit();
		
		if (points6to9.satisfiedBy(hand)){
			if (compareSuits(best_suit, bid_suit)){
				return new Contract(1, best_suit, position);
			}
		}
		
		if (fiveCardSuit.satisfiedBy(hand)){
			
			if(points16OrMore.satisfiedBy(hand)){
				return new Contract(3, best_suit, position);
			}
			
			if (points9OrMore.satisfiedBy(hand)){
				return new Contract(2, best_suit, position);
			}
			
		}
		
		//NT bids
		if(points6to9.satisfiedBy(hand)){
			return new Contract(1, null, position);
		}
		
		if(balanced.satisfiedBy(hand)){
			if(points10to12.satisfiedBy(hand)){
				return new Contract(2, null, position);
			}
		
			if(points13to15.satisfiedBy(hand)){
				return new Contract(3, null, position);
			}
		}
		
		return new Contract(-1, null, position);
	}
	
	
	
	private Contract getResponseToTwoOfASuit(Hand hand, int position, Suit bid_suit){
		
		NumInSpecifiedSuitConstraint threeInThisSuit = new NumInSpecifiedSuitConstraint(3, bid_suit);
		AndConstraint threeOfThisSuitConditions = new AndConstraint(points5to8, threeInThisSuit);
		
		//2NT
		if(points0to7.satisfiedBy(hand)){
			return new Contract(2, null, position);
		}
		
		//3 in same suit
		if(threeOfThisSuitConditions.satisfiedBy(hand)){
			return new Contract(3, bid_suit, position);
		}
				
		
		//Bid a different suit
		Suit best_suit = hand.getLargestSuit();
		if(best_suit != bid_suit){
			NumInSpecifiedSuitConstraint fiveInThisSuit = new NumInSpecifiedSuitConstraint(5, best_suit);
			
			if(compareSuits(best_suit, bid_suit)){
				return new Contract(2, best_suit, position);
			}
			else return new Contract(3, best_suit, position);
		}
		
		
		//3NT
		if(balanced.satisfiedBy(hand)){
			if(points8to11.satisfiedBy(hand)){
				return new Contract(3, null, position);
			}
		}
		
		//Pass
		return new Contract(-1, null, position);
		
	}
	
	
	
	private Contract getResponseToOpeningTwoClubs(Hand hand, int position){
		
		if(points0to7.satisfiedBy(hand)){
			return new Contract(2, Suit.DIAMOND, position);
		}
		
		if(sixCardSuit.satisfiedBy(hand)){
			Suit best_suit = hand.getLargestSuit();
			return new Contract(3, best_suit, position);
		}
		
		if(balanced.satisfiedBy(hand) && points8OrMore.satisfiedBy(hand)){
			return new Contract(2, null, position);
		}
		
		if(points8OrMore.satisfiedBy(hand) && fiveCardSuit.satisfiedBy(hand)){
			Suit best_suit = hand.getLargestSuit();
			if(best_suit != Suit.CLUB)
				return new Contract(2, best_suit, position);
		}
		
		return new Contract(-1, null, position);
	}
	
	
	
	private Contract getResponseToThreeSuitOpening(Hand hand, int position, Suit bid_suit){
		
		NumInSpecifiedSuitConstraint threeSupportingCards = new NumInSpecifiedSuitConstraint(3, bid_suit);
		
		if(points0to15.satisfiedBy(hand)){
			if(threeSupportingCards.satisfiedBy(hand)){
				return new Contract(4, bid_suit, position);
			}
			else{
				return new Contract(-1, null, position);
			}
		}
		
		if(points16OrMore.satisfiedBy(hand)){
			//BID GAME
		}
		
		
		return new Contract(-1, null, position);
	}
	
	private static boolean compareSuits(Suit s1, Suit s2){
		switch(s1){
			case CLUB: if (s2 == Suit.DIAMOND) return false;
			case DIAMOND: if (s2 == Suit.HEART) return false;
			case HEART: if (s2 == Suit.SPADE) return false;
			default: return true;
		}
	}
	
	
}
