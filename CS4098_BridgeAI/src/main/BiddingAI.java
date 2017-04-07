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
	static PointConstraint points9to16 = new PointConstraint(9, 16);
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
	static OrConstraint twoClubOpeningBidConditions = new OrConstraint(points23OrMore, tenPlayingTricks);
	
	static OrConstraint threeNTBiddingConditions = new OrConstraint(balanced, longMinor);
	
	public static Contract getBid(int position, Hand hand, ArrayList<PlayerConstraint> 
	ls_player_constraints, ArrayList<Contract> ls_bids, Contract cur_contract){
		
		//We are bidding without competition...
		if(ls_bids.size() % 2 == 1) return new Contract(-1, null, position);
		
		//OPENING BID
		if(ls_bids.isEmpty()){
			return getOpeningBid(hand, position);
		}
		
		//RESPONDING BIDS
		if(ls_bids.size() == 2){
			
			Contract opening_bid = ls_bids.get(0);
				
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
			
			//Pass
			return new Contract(-1, null, position);
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
					return openingRebidAfterTwoClubs(hand, position, response);
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
		
		if(ls_bids.size() > 4){
			return BridgeAI.getBid(cur_contract, ls_player_constraints, position);
		}
		
		return null;	
	}
	
	public static PlayerConstraint getConstraintFromLastBid(ArrayList<Contract> 
	ls_bids, int position){
		
		if(ls_bids.size() == 1){
			Constraint constraint = getOpeningBidConstraint(ls_bids.get(0));
			return new PlayerConstraint(position, constraint);
		}
		else if(ls_bids.size() == 3){
			Contract opening_bid = ls_bids.get(0);
			
			if(opening_bid.getNumber() == 1 && opening_bid.getSuit() == null){
				Constraint constraint = getResponseTo1NTConstraint(ls_bids.get(2));
				return new PlayerConstraint(position, constraint);
			}
			else if(opening_bid.getNumber() == 1 && opening_bid.getSuit() != null){
				Constraint constraint = getResponseToOneOfASuitConstraint(opening_bid, ls_bids.get(2));
				return new PlayerConstraint(position, constraint);
			}
			else if(opening_bid.getNumber() == 2 && opening_bid.getSuit() == null){
				Constraint constraint = getResponseTo2NTConstraint(ls_bids.get(2));
				return new PlayerConstraint(position, constraint);
			}
			else if(opening_bid.getNumber() == 2 && opening_bid.getSuit() == Suit.CLUB){
				Constraint constraint = getResponseToOpeningTwoClubsConstraint(ls_bids.get(2));
				return new PlayerConstraint(position, constraint);
			}
			else if(opening_bid.getNumber() == 2){
				Constraint constraint = getResponseToTwoOfASuitConstraint(opening_bid, ls_bids.get(2));
				return new PlayerConstraint(position, constraint);
			}
			else if(opening_bid.getNumber() == 3){
				Constraint constraint = getResponseToThreeOfASuitConstraint(opening_bid, ls_bids.get(2));
				return new PlayerConstraint(position, constraint);
			}
		}
		else if(ls_bids.size() == 5){
			Contract opening_bid = ls_bids.get(0);
			Contract response_bid = ls_bids.get(2);
			Contract opener_rebid = ls_bids.get(4);
			
			if(opening_bid.getNumber() == 1 && opening_bid.getSuit() == null){
				Constraint constraint = openingRebidAfter1NTConstraint(response_bid, opener_rebid);
				return new PlayerConstraint(position, constraint);
			}
			else if(opening_bid.getNumber() == 1 && opening_bid.getSuit() != null){
				Constraint constraint = openingRebidAfterOneOfASuitConstraint
						(opening_bid, response_bid, opener_rebid);
				return new PlayerConstraint(position, constraint);
			}
			else if(opening_bid.getNumber() == 2 && opening_bid.getSuit() == null){
				Constraint constraint = openingRebidAfter2NTConstraint(response_bid, opener_rebid);
				return new PlayerConstraint(position, constraint);
			}
			else if(opening_bid.getNumber() == 2 && opening_bid.getSuit() == Suit.CLUB){
				Constraint constraint = openingRebidAfterTwoClubsConstraint(response_bid, opener_rebid);
				return new PlayerConstraint(position, constraint);
			}
			else if(opening_bid.getNumber() == 3){
				Constraint constraint = getResponseToThreeOfASuitConstraint(opening_bid, response_bid);
				return new PlayerConstraint(position, constraint);
			}
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
				Suit best_suit = hand.getLargestSuit();
				return new Contract(1, best_suit, position);
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
	
	private static Constraint getOpeningBidConstraint(Contract bid){
		
		//NT BID
		if(bid.getSuit() == null){
			if(bid.getNumber() == 1){
				return new AndConstraint(balanced, points12to14);
			}
		
			if(bid.getNumber() == 2){
				return new AndConstraint(balanced, points20to22);
			}
		}
		else{
			if(bid.getNumber() == 1){
				AndConstraint constraint1 = new AndConstraint(unbalanced, 
						singleSuitBidConditions);
				AndConstraint constraint2 = new AndConstraint(balanced, points15to19);
				OrConstraint constraint = new OrConstraint(constraint1, constraint2);
				return constraint;
			}
			else if(bid.getNumber() == 2){
				
				if(bid.getSuit() != Suit.CLUB){
					return new AndConstraint(unbalanced, twoSuitBidConditions);
				}
				else{
					return new AndConstraint(unbalanced, twoClubOpeningBidConditions);
				}
			}
			else if(bid.getNumber() == 3){
				return new AndConstraint(unbalanced, threeSuitBidConditions);
			}
			else if(bid.getNumber() == 4){
				return new AndConstraint(unbalanced, fourSuitBidConditions);
			}
		}
		
		//No retrievable information
		return null;
		
	}
	
//RESPONSE-BIDS----------------------------------------------------------------
	
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
				
				//Bid game
				if(sixCardMajorSuit.satisfiedBy(hand)){
					
					Suit best_suit = hand.getLargestSuit();
					int game_bid = gameBidForSuit(best_suit);
					
					return new Contract(game_bid, best_suit, position);
				}
				else if (fiveCardMajorSuit.satisfiedBy(hand)){
					Suit best_suit = hand.getLargestSuit();
					return new Contract(3, best_suit, position);
				}
				else if(fourCardMajorSuit.satisfiedBy(hand)){
					return new Contract(2, Suit.CLUB, position);
				}
			}
			
			if(points19OrMore.satisfiedBy(hand)){
				//BID AI
				return null;
			}
		}
		return new Contract(-1, null, position);
	}
	
	private static Constraint getResponseTo1NTConstraint(Contract bid){
		
		if(bid.getNumber() == -1){
			return new AndConstraint(balanced, points0to10);
		}
		
		if(bid.getSuit() == null){
		
			if(bid.getNumber() == 2){
				return new AndConstraint(balanced, points11to12);
			}
			else if(bid.getNumber() == 3){
				return new AndConstraint(balanced, points13to18);
			}
			else if(bid.getNumber() == 4){
				return new AndConstraint(balanced, points19to20);
			}
		}
		else{
			if(bid.getNumber() == 2 && bid.getSuit() != Suit.CLUB){
				return new AndConstraint(unbalanced, points0to10);
			}
			else if(bid.getNumber() == 2 && bid.getSuit() == Suit.CLUB){
				AndConstraint and1 = new AndConstraint(unbalanced, fourCardMajorSuit);
				return new AndConstraint(and1, points11OrMore);
			}
			else if(bid.getNumber() == 3 && bid.getSuit() != Suit.DIAMOND
					&& bid.getSuit() != Suit.CLUB){
				AndConstraint and1 = new AndConstraint(unbalanced, fiveCardMajorSuit);
				return new AndConstraint(and1, points11OrMore);
			}
			else if(bid.getNumber() == 4 && bid.getSuit() != Suit.DIAMOND
					&& bid.getSuit() != Suit.CLUB){
				AndConstraint and1 = new AndConstraint(unbalanced, sixCardMajorSuit);
				return new AndConstraint(and1, points11OrMore);
			}
		}
		
		return null;
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
			
			//Bid game
			if(sixCardMajorSuit.satisfiedBy(hand)){
				Suit best_suit = hand.getLargestSuit();
				int game_bid = gameBidForSuit(best_suit);
				return new Contract(game_bid, best_suit, position);
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
	
	private static Constraint getResponseTo2NTConstraint(Contract bid){
		
		if(bid.getNumber() == -1){
			return points0to3;
		}
		
		if(bid.getNumber() == 2 && bid.getSuit() != Suit.CLUB){
			return new AndConstraint(points4to10, points0to10);
		}
		else if(bid.getNumber() == 2 && bid.getSuit() == Suit.CLUB){
			return new AndConstraint(points4to10, fourCardMajorSuit);
		}
		else if(bid.getNumber() == 3 && bid.getSuit() != Suit.DIAMOND
				& bid.getSuit() != Suit.CLUB){
			return new AndConstraint(points4to10, fiveCardMajorSuit);
		}
		else if(bid.getNumber() == 4 && bid.getSuit() != Suit.DIAMOND
				& bid.getSuit() != Suit.CLUB){
			return new AndConstraint(points4to10, sixCardMajorSuit);
		}
		
		if(bid.getNumber() == 4 && bid.getSuit() == null){
			return new AndConstraint(points11to12, balanced);
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
			if (points9to16.satisfiedBy(hand)){
				return new Contract(2, best_suit, position);
			}
			else if(points16OrMore.satisfiedBy(hand)){
				return new Contract(3, best_suit, position);
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
	
	private static Constraint getResponseToOneOfASuitConstraint(Contract opening_bid, Contract bid){
		
		NumInSpecifiedSuitConstraint fourInBidSuit = 
				new NumInSpecifiedSuitConstraint(4, bid.getSuit());
		NumInSpecifiedSuitConstraint fiveInBidSuit = 
				new NumInSpecifiedSuitConstraint(5, bid.getSuit());
		
		if(bid.getNumber() == -1){
			return points0to5;
		}
		
		if(opening_bid.getSuit() == bid.getSuit()){	
			if(bid.getNumber() == 2){
				return new AndConstraint(fourInBidSuit, points6to9);
			}
			else if(bid.getNumber() == 3){
				return new AndConstraint(fourInBidSuit, points10to12);
			}
			else if(bid.getNumber() == 4){
				return new AndConstraint(fourInBidSuit, points13to15);
			}
		}
		else{
			if(bid.getNumber() == 1){
				return new AndConstraint(fourInBidSuit, points6to9);
			}
			else if(bid.getNumber() == 2){
				return new AndConstraint(fiveInBidSuit, points9to16);
			}
			else if(bid.getNumber() == 3){
				return new AndConstraint(fiveInBidSuit, points16OrMore);
			}
		}
		
		if(bid.getSuit() == null){
			if(bid.getNumber() == 1){
				return points6to9;
			}
			else if(bid.getNumber() == 2){
				return new AndConstraint(balanced, points10to12);
			}
			else if(bid.getNumber() == 3){
				return new AndConstraint(balanced, points13to15);
			}
		}
		
		return null;
	}
	
	private static Contract getResponseToTwoOfASuit(Hand hand, int position, Suit bid_suit){
		
		//2NT
		if(points0to7.satisfiedBy(hand)){
			return new Contract(2, null, position);
		}
		
		//3 in same suit
		NumInSpecifiedSuitConstraint threeInThisSuit = new NumInSpecifiedSuitConstraint(3, bid_suit);
		SpecifiedCardValueConstraint hasAce = new SpecifiedCardValueConstraint(CardValue.ACE);
		if(threeInThisSuit.satisfiedBy(hand)){
			
			if(!hasAce.satisfiedBy(hand)){
				return new Contract(3, bid_suit, position);
			}
			else{
				//Bid game
				int game_bid = gameBidForSuit(bid_suit);
				return new Contract(game_bid, bid_suit, position);
			}
		}
				
		//Bid a different suit
		Suit best_suit = hand.getLargestSuit();
		if(best_suit != bid_suit){
			NumInSpecifiedSuitConstraint fiveInThisSuit = new NumInSpecifiedSuitConstraint(5, best_suit);
			if(fiveInThisSuit.satisfiedBy(hand)){
				if(compareSuits(best_suit, bid_suit)){
					return new Contract(2, best_suit, position);
				}
				else return new Contract(3, best_suit, position);
			}
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
	
	private static Constraint getResponseToTwoOfASuitConstraint(Contract opening_bid, Contract bid){
		
		if(bid.getNumber() == 2 && bid.getSuit() == null){
			return points0to7;
		}
		
		NumInSpecifiedSuitConstraint threeInThisSuit = new NumInSpecifiedSuitConstraint(3, bid.getSuit());
		SpecifiedCardValueConstraint hasAce = new SpecifiedCardValueConstraint(CardValue.ACE);
		if(opening_bid.getSuit() == bid.getSuit()){
			if(bid.getNumber() == 3){
				
				return new AndConstraint(points8OrMore, threeInThisSuit);
			}
			else if (bid.getNumber() > 3){
				//Game bid
				AndConstraint and1 = new AndConstraint(threeInThisSuit, hasAce);
				return new AndConstraint(and1, points8OrMore);
			}
		}
		else{
			NumInSpecifiedSuitConstraint fiveInThisSuit = new 
					NumInSpecifiedSuitConstraint(5, bid.getSuit());
			return new AndConstraint(points8OrMore, fiveInThisSuit);
		}
		
		if(bid.getNumber() == 3 && bid.getSuit() == null){
			return new AndConstraint(points8to11, balanced);
		}	
		
		return null;
	}
	
	private static Contract getResponseToOpeningTwoClubs(Hand hand, int position){
		
		if(points0to7.satisfiedBy(hand)){
			return new Contract(2, Suit.DIAMOND, position);
		}
		
		if(sixCardSuit.satisfiedBy(hand)){
			Suit best_suit = hand.getLargestSuit();
			return new Contract(3, best_suit, position);
		}
		
		if(balanced.satisfiedBy(hand)){
			return new Contract(2, null, position);
		}
		
		if(fiveCardSuit.satisfiedBy(hand)){
			Suit best_suit = hand.getLargestSuit();
			if(best_suit != Suit.CLUB)
				return new Contract(2, best_suit, position);
		}
		
		return new Contract(-1, null, position);
	}
	
	private static Constraint getResponseToOpeningTwoClubsConstraint(Contract bid){
		
		if(bid.getNumber() == 2 && bid.getSuit() == Suit.DIAMOND){
			return points0to7;
		}
		
		if(bid.getNumber() == 3){
			NumInSpecifiedSuitConstraint sixInThisSuit = new 
					NumInSpecifiedSuitConstraint(6, bid.getSuit());
			return new AndConstraint(sixInThisSuit, points8OrMore);
		}
		
		if(bid.getNumber() == 2 && bid.getSuit() == null){
			return new AndConstraint(balanced, points8OrMore);
		}
		
		if(bid.getNumber() == 2){
			NumInSpecifiedSuitConstraint fiveInThisSuit = new 
					NumInSpecifiedSuitConstraint(5, bid.getSuit());
			return new AndConstraint(fiveInThisSuit, points8OrMore);
		}
		
		return null;
	}
	
	private static Contract getResponseToThreeOfASuit(Hand hand, int position, Suit bid_suit){
		
		NumInSpecifiedSuitConstraint threeInThisSuit= new NumInSpecifiedSuitConstraint(3, bid_suit);
		
		if(points0to15.satisfiedBy(hand)){
			if(threeInThisSuit.satisfiedBy(hand)){
				return new Contract(4, bid_suit, position);
			}
			else{
				return new Contract(-1, null, position);
			}
		}
		
		if(points16OrMore.satisfiedBy(hand)){
			//Bid game
			int game_bid = gameBidForSuit(bid_suit);
			return new Contract(game_bid, bid_suit, position);
		}
		
		return new Contract(-1, null, position);
	}
	
	private static Constraint getResponseToThreeOfASuitConstraint(Contract opening_bid, Contract bid){
		
		NumInSpecifiedSuitConstraint threeInThisSuit = 
				new NumInSpecifiedSuitConstraint(3, opening_bid.getSuit());
		MinMaxInSpecifiedSuitConstraint lessThanThreeInThisSuit = 
				new MinMaxInSpecifiedSuitConstraint(0,2, opening_bid.getSuit());
		
		if(bid.getNumber() == -1){
			return new AndConstraint(lessThanThreeInThisSuit, points0to15);
		}
		else{
			if(opening_bid.getSuit() == bid.getSuit()){
				int game_bid = gameBidForSuit(bid.getSuit());
				if(bid.getNumber() == 4){
					AndConstraint and1 = new AndConstraint(threeInThisSuit, points0to15);
					return new OrConstraint(and1, points16OrMore);
				}
				else if(bid.getNumber() == 5){
					return points16OrMore;
				}
			}
		}

		return null;
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
		
		if(response_number == 2 && response_suit == Suit.CLUB){
			NumInSpecifiedSuitConstraint fourHearts = new 
					NumInSpecifiedSuitConstraint(4, Suit.HEART);
			NumInSpecifiedSuitConstraint fourSpades = new 
					NumInSpecifiedSuitConstraint(4, Suit.SPADE);
			
			if(fourSpades.satisfiedBy(hand)){
				return new Contract(2, Suit.SPADE, position);
			}
			else if(fourHearts.satisfiedBy(hand)){
				return new Contract(2, Suit.HEART, position);
			}
			else{ 
				return new Contract(2, Suit.DIAMOND, position);
			}
		}
		
		if(response_number == 4 && (response_suit == Suit.HEART || response_suit == Suit.SPADE)){
			return new Contract(-1, null, position);
		}
		
		if(response_number == 3){
			if(response_suit == Suit.SPADE){
				MinMaxInSpecifiedSuitConstraint threeOrFourSpades = 
						new MinMaxInSpecifiedSuitConstraint(3, 4, Suit.SPADE);
				
				if (threeOrFourSpades.satisfiedBy(hand)){
					//Bid game
					return new Contract(4, Suit.SPADE, position);
				}				
			}
			
			if(response_suit == Suit.HEART){
				MinMaxInSpecifiedSuitConstraint threeOrFourHearts = 
						new MinMaxInSpecifiedSuitConstraint(3, 4, Suit.HEART);
				
				if (threeOrFourHearts.satisfiedBy(hand)){
					//Bid game
					return new Contract(4, Suit.HEART, position);
				}				
			}
			return new Contract(3, null, position);
		}
		return new Contract(-1, null, position);
	}
	
	private static Constraint openingRebidAfter1NTConstraint(Contract response, Contract bid){
		
		if(bid.getNumber() == 3 && bid.getSuit() == null){
			return points14OrMore;
		}
		else if(bid.getNumber() == 4 && bid.getSuit() == null){
			return points14OrMore;
		}
		
		NumInSpecifiedSuitConstraint fourSpades = new NumInSpecifiedSuitConstraint(4, Suit.SPADE);
		NumInSpecifiedSuitConstraint fourHearts = new NumInSpecifiedSuitConstraint(4, Suit.HEART);
		if(response.getNumber() == 2 && response.getSuit() == Suit.CLUB){
			if(bid.getNumber() == 2 && bid.getSuit() == Suit.SPADE){
				return fourSpades;
			}
			else if(bid.getNumber() == 2 && bid.getSuit() == Suit.HEART){
				return fourHearts;
			}
		}
		
		//Game bids
		if(response.getNumber() == 3 && response.getSuit() == Suit.SPADE){
			if(bid.getNumber() == 4 && bid.getSuit() == Suit.SPADE){
				MinMaxInSpecifiedSuitConstraint threeOrFourSpades = 
						new MinMaxInSpecifiedSuitConstraint(3, 4, Suit.SPADE);
				return threeOrFourSpades;
			}
		}
		if(response.getNumber() == 3 && response.getSuit() == Suit.HEART){
			if(bid.getNumber() == 4 && bid.getSuit() == Suit.HEART){
				MinMaxInSpecifiedSuitConstraint threeOrFourHearts = 
						new MinMaxInSpecifiedSuitConstraint(3, 4, Suit.HEART);
				return threeOrFourHearts;
			}
		}
			
		if (response.getNumber() == 3){
			if(bid.getNumber() == 3 && bid.getSuit() == null){
				MinMaxInSpecifiedSuitConstraint zeroToTwoOfSuit = 
						new MinMaxInSpecifiedSuitConstraint(0, 2, response.getSuit());
				return zeroToTwoOfSuit;
			}
		}
		
		return null;
		
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
					return new Contract(4, Suit.SPADE, position);
				}
			}
			
			if(response_suit == Suit.HEART){
				MinMaxInSpecifiedSuitConstraint threeOrFourHearts = 
					new MinMaxInSpecifiedSuitConstraint(3, 4, Suit.HEART);
				
				if (threeOrFourHearts.satisfiedBy(hand)){
					return new Contract(4, Suit.HEART, position);
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
	
	private static Constraint openingRebidAfter2NTConstraint(Contract response, Contract bid){
		
		if(response.getNumber() == 3){
			
			//Game bids
			if(bid.getNumber() == 4){
				
				MinMaxInSpecifiedSuitConstraint threeOrFourSpades = 
						new MinMaxInSpecifiedSuitConstraint(3, 4, Suit.SPADE);
				MinMaxInSpecifiedSuitConstraint threeOrFourHearts = 
						new MinMaxInSpecifiedSuitConstraint(3, 4, Suit.HEART);
				
				if(bid.getSuit() == Suit.SPADE){
					return threeOrFourSpades;
				}
				else if(bid.getSuit() == Suit.HEART){
					return threeOrFourHearts;
				}
			}

			if(bid.getNumber() == 3 && bid.getSuit() == null){
				MinMaxInSpecifiedSuitConstraint zeroToTwoOfSuit = 
					new MinMaxInSpecifiedSuitConstraint(0, 2, response.getSuit());
				return zeroToTwoOfSuit;
			}
		}
		
		if(response.getNumber() == 4 && response.getSuit() == null){
			if(bid.getNumber() == 6 && bid.getSuit() == null){
				return points22OrMore;
			}
		}
		
		return null;
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
					//Bid game
					int game_bid = gameBidForSuit(response_suit);
					return new Contract(game_bid, response_suit, position);
				}
				//3NT
				else{
					return new Contract(3, null, position);
				}
			}
		}

		return new Contract(-1, null, position);
	}
	
	private static Constraint openingRebidAfterOneOfASuitConstraint(Contract opening_bid,
			Contract response, Contract bid){
		
		if(response.getSuit() != opening_bid.getSuit()){
			
			MinMaxInSpecifiedSuitConstraint zeroToTwoInThisSuit =
					new MinMaxInSpecifiedSuitConstraint(0, 3, response.getSuit());
			NumInSpecifiedSuitConstraint fourInThisSuit = 
					new NumInSpecifiedSuitConstraint(4, response.getSuit());
			NumInSpecifiedSuitConstraint fiveInOriginalSuit = 
					new NumInSpecifiedSuitConstraint(5, opening_bid.getSuit());
			NumInSpecifiedSuitConstraint sixInOriginalSuit = 
					new NumInSpecifiedSuitConstraint(6, opening_bid.getSuit());
			
			if(bid.getNumber() == response.getNumber() + 1){
		
				if (bid.getNumber() == response.getNumber() + 1 && bid.getSuit() == response.getSuit()){
					return new AndConstraint(fourInThisSuit, points11to15);
				}
			
				if(response.getSuit() != null){
					return new AndConstraint(points11to15, fiveInOriginalSuit);
				}
				else if (bid.getNumber() == response.getNumber() + 1 && 
						bid.getSuit() == opening_bid.getSuit()){
					return sixInOriginalSuit;
				}
			}
			else if(bid.getNumber() >= response.getNumber() + 2){
				return new AndConstraint(fourInThisSuit, points16OrMore);
			}
			else if(bid.getNumber() == 3 && bid.getSuit() == null){
				return new AndConstraint(points19OrMore, zeroToTwoInThisSuit);
			}
		}
		
		return null;
	}
	
	private static Contract openingRebidAfterTwoOfASuit(Hand hand, int position, Contract original, Contract response){

		//BID AI
		
		return new Contract(-1, null, position);
	}
	
	private static Contract openingRebidAfterTwoClubs(Hand hand, int position, Contract response){
		int response_number = response.getNumber();
		Suit response_suit = response.getSuit();
		
		if(response_number == 2 && response_suit == Suit.DIAMOND){
			if(points23to24.satisfiedBy(hand)){
				return new Contract(2, null, position);
			}
		}
		
		return new Contract(-1, null, position);
	}
	
	private static Constraint openingRebidAfterTwoClubsConstraint(Contract response, Contract bid){
		
		if(bid.getNumber() == 2 && bid.getSuit() == null){
			return points23to24;
		}
		
		return null;
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
	
//OTHER METHODS----------------------------------------------------------------
	
	private static int gameBidForSuit(Suit suit){
		switch(suit){
		case CLUB: return 5;
		case DIAMOND: return 5;
		case HEART: return 4;
		case SPADE: return 4;
		default: return 3; //NT
		}
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
