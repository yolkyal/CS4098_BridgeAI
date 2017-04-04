package main;

import java.util.ArrayList;

import bridge_data_structures.*;
import constraints.*;

public class BiddingAI {
	
	static BalanceConstraint balanced = new BalanceConstraint(true);
	static BalanceConstraint unbalanced = new BalanceConstraint(false);
	
	static PointConstraint points0to3 = new PointConstraint(0, 3);
	static PointConstraint points0to5 = new  PointConstraint(0, 5);
	static PointConstraint points0to7 = new PointConstraint(0, 7);
	static PointConstraint points0to10 = new PointConstraint(0, 10);
	static PointConstraint points0to15 = new PointConstraint(0, 15);
	static PointConstraint points4to10 = new PointConstraint(4, 10);
	static PointConstraint points5to8 = new PointConstraint(5, 8);
	static PointConstraint points5OrMore = new PointConstraint(5, 100);
	static PointConstraint points6to9 = new PointConstraint(6, 9);
	static PointConstraint points8to11 = new PointConstraint(8, 11);
	static PointConstraint points8OrMore = new PointConstraint(8, 100);
	static PointConstraint points9OrMore = new PointConstraint(9, 100);
	static PointConstraint points10to12 = new PointConstraint(10, 12);
	static PointConstraint points10OrMore = new PointConstraint(10, 100);
	static PointConstraint points11OrMore = new PointConstraint(11, 100);
	static PointConstraint points11to12 = new PointConstraint(11, 12);
	static PointConstraint points11to15 = new PointConstraint(11, 15);
	static PointConstraint points11to19 = new PointConstraint(11, 19);
	static PointConstraint points12OrMore = new PointConstraint(12, 100);
	static PointConstraint points12to14 = new PointConstraint(12, 14);
	static PointConstraint points13to15 = new PointConstraint(13, 15);
	static PointConstraint points13to18 = new PointConstraint(13, 18);
	static PointConstraint points14OrMore = new PointConstraint(4, 100);
	static PointConstraint points15to19 = new PointConstraint(15, 19);
	static PointConstraint points16to18 = new PointConstraint(16, 18);
	static PointConstraint points16to22 = new PointConstraint(16, 22);
	static PointConstraint points16OrMore = new PointConstraint(16, 100);
	static PointConstraint points19to20 = new PointConstraint(19, 20);
	static PointConstraint points19OrMore = new PointConstraint(19, 100);
	static PointConstraint points20to22 = new PointConstraint(20, 22);
	static PointConstraint points22OrMore = new PointConstraint(22, 100);
	static PointConstraint points23OrMore = new PointConstraint(23, 100);
	static PointConstraint points23to24 = new PointConstraint(23, 24);
	
	static NumInASuitConstraint fourCardSuit = new NumInASuitConstraint(4);
	static NumInASuitConstraint fiveCardSuit = new NumInASuitConstraint(5);
	static NumInASuitConstraint sixCardSuit = new NumInASuitConstraint(6);
	static NumInASuitConstraint sevenCardSuit = new NumInASuitConstraint(7);
	static NumInASuitConstraint eightCardSuit = new NumInASuitConstraint(8);
	static NumSuitsOfSizeConstraint two5CardSuits = new NumSuitsOfSizeConstraint(2, 5);
	
	static NumInAMajorSuitConstraint fourCardMajorSuit = new NumInAMajorSuitConstraint(4);
	static NumInAMajorSuitConstraint fiveCardMajorSuit = new NumInAMajorSuitConstraint(5);
	static NumInAMajorSuitConstraint sixCardMajorSuit = new NumInAMajorSuitConstraint(6);
	
	static NumInAMinorSuitConstraint longMinor = new NumInAMinorSuitConstraint(6);
	
	static NumPlayingTricksConstraint eightPlayingTricks = new NumPlayingTricksConstraint(8);
	static NumPlayingTricksConstraint tenPlayingTricks = new NumPlayingTricksConstraint(10);
	
	//Specific bid conditions--------------------------------------------------
	static AndConstraint and1 = new AndConstraint(points10OrMore, sixCardSuit);
	static AndConstraint and2 = new AndConstraint(points11OrMore, fiveCardSuit);
	static AndConstraint and3 = new AndConstraint(points12OrMore, fourCardSuit);
	static OrConstraint or1 = new OrConstraint(and1, and2);
	static OrConstraint singleSuitBidConditions = new OrConstraint(and3, or1);
	
	static OrConstraint or2 = new OrConstraint(sixCardSuit, two5CardSuits);
	static AndConstraint and4 = new AndConstraint(points16to22, or2);
	static AndConstraint twoSuitBidConditions = new AndConstraint(eightPlayingTricks, and4);
	
	static AndConstraint threeSuitBidConditions = new AndConstraint(points6to9, sevenCardSuit);
	static AndConstraint fourSuitBidConditions = new AndConstraint(points6to9, eightCardSuit);
	static AndConstraint twoClubOpeningBidConditions = new AndConstraint(points23OrMore, tenPlayingTricks);
	
	static OrConstraint threeNTBiddingConditions = new OrConstraint(balanced, longMinor);
	
	public static Contract getBid(int position, Hand hand, ArrayList<PlayerConstraint> 
	ls_player_constraints, ArrayList<Contract> ls_bids){
		
		//OPENING BID
		if(ls_bids.isEmpty()){
			return getOpeningBid(hand, position);
		}
		
		//FIRST OPPONENT BID
		if(ls_bids.size() == 1){
			//Overcall, double or pass
			return new Contract(-1, null, position);
		}
		
		//RESPONDING BIDS
		if(ls_bids.size() == 2){
			Contract opening_bid = ls_bids.get(0);
			Contract opponent1_bid = ls_bids.get(1);
			
			//If first opponent passed
			if(opponent1_bid.getNumber()== -1){
				
				if(opening_bid.getNumber() == 1){
					
					if (opening_bid.getSuit() == null){
						return getResponseTo1NT(hand, position);
					}
					else{
						return getResponseToOneOfASuit(hand, position, opening_bid.getSuit());
					}
				}
				else if(opening_bid.getNumber() == 2){
					
					if(opening_bid.getSuit() == null){
						return getResponseTo2NT(hand, position);
					}
					else if(opening_bid.getSuit() == Suit.CLUB){
						return getResponseToOpeningTwoClubs(hand, position);
					}
					else{
						return getResponseToTwoOfASuit(hand, position, opening_bid.getSuit());
					}
				}
				else if(opening_bid.getNumber() == 3){
					if(opening_bid.getSuit() != null){
						return getResponseToThreeOfASuit(hand, position, opening_bid.getSuit());
					}
				}
			}
			//If first opponent overcalled or doubled
			else{
				
			}
			
			//Pass
			return new Contract(-1, null, position);
		}
		
		//SECOND OPPONENT BID
		if(ls_bids.size() == 3){
			//Overcall, double or pass
		}
		
		//OPENING REBIDS
		if(ls_bids.size() == 4){
			Contract opening_bid = ls_bids.get(0);
			Contract response = ls_bids.get(2);		
			
			if(opening_bid.getNumber() == 1){
				if(opening_bid.getSuit() == null){
					return openingRebidAfter1NT(hand, position, response);
				}
				else{
					return openingRebidAfterOneOfASuit(hand, position, opening_bid, response);
				}
			}
			else if(opening_bid.getNumber() == 2){
				
				if(opening_bid.getSuit() == null){
					return openingRebidAfter2NT(hand, position, response);
				}
				else if(opening_bid.getSuit() == Suit.CLUB){
					return openingRebidAfterTwoOfClubs(hand, position, response);
				}
				else{
					return openingRebidAfterTwoOfASuit(hand, position, opening_bid, response);
				}
			}
			else if(opening_bid.getNumber() == 3){
				if(opening_bid.getSuit() != null){
					return openingRebidAfterThreeOfASuit(hand, position, opening_bid, response);
				}
			}
		}
		
		if(ls_bids.size() % 2 == 1){
			return new Contract(-1, null, position);
		}
		else{
			//AI BID
		}
		
		return null;	
	}

//OPENING-BIDS-----------------------------------------------------------------
	
	private static Contract getOpeningBid(Hand hand, int position){
		
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
	
//RESPONSE-BIDS----------------------------------------------------------------
	
	
//RESPONSES--------------------------------------------------------------------
	
	private static Contract getResponseTo1NT(Hand hand, int position){
		
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
	

	private static Contract getResponseTo2NT(Hand hand, int position){
		
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
	
	
	private static Contract getResponseToOneOfASuit(Hand hand, int position, Suit bid_suit){
		
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
	
	
	private static Contract getResponseToTwoOfASuit(Hand hand, int position, Suit bid_suit){
		
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
	
	
	private static Contract getResponseToOpeningTwoClubs(Hand hand, int position){
		
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
	
	
	private static Contract getResponseToThreeOfASuit(Hand hand, int position, Suit bid_suit){
		
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
	
//OPENING-REBIDS---------------------------------------------------------------
	
	
	
	private static Contract openingRebidAfter1NT(Hand hand, int position, Contract response){
		int response_number = response.getNumber();
		Suit response_suit = response.getSuit();
		
		if(response_number == 2 && response_suit == null){
			if(points14OrMore.satisfiedBy(hand)){
				return new Contract(3, null, position);
			}
		}
		
		if(response_number == 4 && response_suit == null){
			if(points14OrMore.satisfiedBy(hand)){
				return new Contract(6, null, position);
			}
		}
		
		if(response_number == 2 && response_suit != null && response_suit != Suit.CLUB){
			return new Contract(-1, null, position);
		}
		
		if(response_number == 4 && (response_suit == Suit.HEART || response_suit == Suit.SPADE)){
			return new Contract(-1, null, position);
		}
		
		if(response_number == 3){
			if(response_suit == Suit.SPADE){
				MinMaxInSpecifiedSuitConstraint threeOrFourSpades = 
						new MinMaxInSpecifiedSuitConstraint(3, 4, Suit.SPADE);
				
				if (threeOrFourSpades.satisfiedBy(hand)){
					//BID GAME
				}				
			}
			
			if(response_suit == Suit.HEART){
				MinMaxInSpecifiedSuitConstraint threeOrFourHearts = 
						new MinMaxInSpecifiedSuitConstraint(3, 4, Suit.HEART);
				
				if (threeOrFourHearts.satisfiedBy(hand)){
					//BID GAME
				}				
			}
			
			return new Contract(3, null, position);
		}
		
		
		return new Contract(-1, null, position);
	}
	
	
	private static Contract openingRebidAfter2NT(Hand hand, int position, Contract response){
		int response_number = response.getNumber();
		Suit response_suit = response.getSuit();
		
		if(response_number == 4 && (response_suit == Suit.HEART || response_suit == Suit.SPADE)){
			return new Contract(-1, null, position);
		}
		
		if(response_number == 3){
			if(response_suit == Suit.SPADE){
				MinMaxInSpecifiedSuitConstraint threeOrFourSpades = 
						new MinMaxInSpecifiedSuitConstraint(3, 4, Suit.SPADE);
				
				if (threeOrFourSpades.satisfiedBy(hand)){
					//BID GAME
				}				
			}
			
			if(response_suit == Suit.HEART){
				MinMaxInSpecifiedSuitConstraint threeOrFourHearts = 
						new MinMaxInSpecifiedSuitConstraint(3, 4, Suit.HEART);
				
				if (threeOrFourHearts.satisfiedBy(hand)){
					//BID GAME
				}				
			}
			
			return new Contract(3, null, position);
		}
		
		if(response_number == 4 && response_suit == null){
			if(points22OrMore.satisfiedBy(hand)){
				return new Contract(6, null, position);
			}
		}

		return new Contract(-1, null, position);
	}
	
	
	private static Contract openingRebidAfterOneOfASuit(Hand hand, int position, Contract original, Contract response){
		int response_number = response.getNumber();
		Suit response_suit = response.getSuit();
		
		if(response_number == -1){
			return new Contract(-1, null, position);
		}
		
		int hand_points = hand.getPoints();
		int partner_points = 0;
		//Look at constraints on partner's hand to assess points
		
		NumInSpecifiedSuitConstraint fourInThisSuit = 
				new NumInSpecifiedSuitConstraint(4, response_suit);
		
			
		if(response_suit != original.getSuit()){
			Suit original_suit = original.getSuit();
				
			if(points11to15.satisfiedBy(hand)){	
			
				NumInSpecifiedSuitConstraint fiveInOriginalSuit = 
						new NumInSpecifiedSuitConstraint(5, original_suit);
				
				//Simple raise of response suit
				if (fourInThisSuit.satisfiedBy(hand)){
					return new Contract(response_number + 1, response_suit, position);
				}
				
				//Rebid of original suit (except over response NT)
				if (response_suit != null){
					if (fiveInOriginalSuit.satisfiedBy(hand)){
						if(compareSuits(original_suit, response_suit)){
							return new Contract(response_number, original_suit, position);
						}
						else{
							return new Contract(response_number + 1, original_suit, position);
						}
					}
				}
				//Rebid over response NT
				else{
					NumInSpecifiedSuitConstraint sixInOriginalSuit = 
							new NumInSpecifiedSuitConstraint(6, original_suit);
					
					if(sixInOriginalSuit.satisfiedBy(hand)){
						return new Contract(response_number + 1, original_suit, position);
					}
				}	
			}
			
			//Jump support of response suit
			if(points16to18.satisfiedBy(hand)){
				if (fourInThisSuit.satisfiedBy(hand)){
					return new Contract(response_number + 2, response_suit, position);
				}
			}
			
			if(points19OrMore.satisfiedBy(hand)){
				if(fourInThisSuit.satisfiedBy(hand)){
					//BID GAME
				}
				//3NT
				else{
					return new Contract(3, null, position);
				}
			}
		}

		return new Contract(-1, null, position);
	}
	
	
	private static Contract openingRebidAfterTwoOfASuit(Hand hand, int position, Contract original, Contract response){

		//BID AI
		
		return new Contract(-1, null, position);
	}
	
	
	private static Contract openingRebidAfterTwoOfClubs(Hand hand, int position, Contract response){
		int response_number = response.getNumber();
		Suit response_suit = response.getSuit();
		
		if(response_number == 2 && response_suit == Suit.DIAMOND){
			if(points23to24.satisfiedBy(hand)){
				return new Contract(2, null, position);
			}
		}
		

		return new Contract(-1, null, position);
	}
	
	
	private static Contract openingRebidAfterThreeOfASuit(Hand hand, int position, Contract original, Contract response){
		Suit response_suit = response.getSuit();
		Suit original_suit = original.getSuit();
		
		if(response_suit == original_suit){
			return new Contract(-1, null, position);
		}
		else{
			//AI BID
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