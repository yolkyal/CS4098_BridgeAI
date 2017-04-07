package unit_tests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import bridge_data_structures.Contract;
import bridge_data_structures.Hand;
import bridge_data_structures.Suit;
import constraints.BalanceConstraint;
import constraints.NumInSpecifiedSuitConstraint;
import constraints.PointConstraint;
import main.BiddingAI;
import main.PlayerConstraint;
import main.RDealGenerator;

public class ACOLBiddingTests {

	BalanceConstraint balanced = new BalanceConstraint(true);
	BalanceConstraint unbalanced = new BalanceConstraint(false);
	PointConstraint points0to10 = new PointConstraint(0, 10);
	PointConstraint points10to12 = new PointConstraint(10, 12);
	PointConstraint points11to12 = new PointConstraint(11, 12);
	PointConstraint points12to14 = new PointConstraint(12, 14);
	PointConstraint points20to22 = new PointConstraint(20, 22);
	NumInSpecifiedSuitConstraint fourInSpades = new NumInSpecifiedSuitConstraint(4, Suit.SPADE);
	
	@Test
	public void testOpeningBids(){
		ArrayList<PlayerConstraint> p_constraints = new ArrayList<PlayerConstraint>();
		p_constraints.add(new PlayerConstraint(0, balanced));
		p_constraints.add(new PlayerConstraint(0, points12to14));
		p_constraints.add(new PlayerConstraint(1, balanced));
		p_constraints.add(new PlayerConstraint(1, points20to22));
		Hand[] hands = RDealGenerator.generateRDeal(p_constraints);
		
		Contract opening_bid1 = BiddingAI.getBid(0, hands[0], p_constraints, new ArrayList<Contract>(), null);
		Contract opening_bid2 = BiddingAI.getBid(1, hands[1], p_constraints, new ArrayList<Contract>(), null);
		
		//Assert this bid is 1NT
		assertTrue(opening_bid1.getNumber() == 1);
		assertTrue(opening_bid1.getSuit() == null);
		
		//Assert this bid is 2NT
		assertTrue(opening_bid2.getNumber() == 2);
		assertTrue(opening_bid2.getSuit() == null);
	}
	
	@Test
	public void testResponseTo1NTOpening(){
		ArrayList<PlayerConstraint> p_constraints = new ArrayList<PlayerConstraint>();
		p_constraints.add(new PlayerConstraint(2, balanced));
		p_constraints.add(new PlayerConstraint(2, points11to12));
		Hand[] hands = RDealGenerator.generateRDeal(p_constraints);
		
		//1NT NORTH, PASS EAST
		ArrayList<Contract> ls_bids = new ArrayList<Contract>();
		ls_bids.add(new Contract(1, null, 0));
		ls_bids.add(new Contract(-1, null, 1));
		
		Contract response_bid = BiddingAI.getBid(2, hands[2], p_constraints, ls_bids, null);
		
		//Assert this bid is 2NT
		assertTrue(response_bid.getNumber() == 2);
		assertTrue(response_bid.getSuit() == null);
		
		p_constraints = new ArrayList<PlayerConstraint>();
		p_constraints.add(new PlayerConstraint(2, unbalanced));
		p_constraints.add(new PlayerConstraint(2, points0to10));
		hands = RDealGenerator.generateRDeal(p_constraints);
		response_bid = BiddingAI.getBid(2, hands[2], p_constraints, ls_bids, null);
		
		//Assert bid of 2 on a suit so long as their best suit is not clubs.
		if(hands[2].getLargestSuit() != Suit.CLUB){
			assertTrue(response_bid.getNumber() == 2);
			assertTrue(response_bid.getSuit() != null);
		}
		
	}
	
	@Test
	public void testResponseToOneOfASuitOpening(){
		ArrayList<PlayerConstraint> p_constraints = new ArrayList<PlayerConstraint>();
		p_constraints.add(new PlayerConstraint(2, fourInSpades));
		p_constraints.add(new PlayerConstraint(2, points10to12));
		Hand[] hands = RDealGenerator.generateRDeal(p_constraints);
		
		//1S NORTH, PASS EAST
		ArrayList<Contract> ls_bids = new ArrayList<Contract>();
		ls_bids.add(new Contract(1, Suit.SPADE, 0));
		ls_bids.add(new Contract(-1, null, 1));
		
		Contract response_bid = BiddingAI.getBid(2, hands[2], p_constraints, ls_bids, null);
		
		assertTrue(response_bid.getNumber() == 3);
		assertTrue(response_bid.getSuit() == Suit.SPADE);
		
	}
}
