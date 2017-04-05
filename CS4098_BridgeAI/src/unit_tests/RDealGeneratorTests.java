package unit_tests;

import constraints.*;
import main.PlayerConstraint;
import main.RDealGenerator;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import bridge_data_structures.Hand;


public class RDealGeneratorTests {
	
	BalanceConstraint balanced = new BalanceConstraint(true);
	PointConstraint points16OrMore = new PointConstraint(16, 100);
	
	@Test
	public void rDealGenerationBalanceTest(){
		ArrayList<PlayerConstraint> p_constraints = new ArrayList<PlayerConstraint>();
		
		p_constraints.add(new PlayerConstraint(0, balanced));
		p_constraints.add(new PlayerConstraint(1, balanced));
		p_constraints.add(new PlayerConstraint(2, balanced));
		p_constraints.add(new PlayerConstraint(3, balanced));
		
		Hand[] balanced_hands = RDealGenerator.generateRDeal(p_constraints);
		
		for(int i = 0; i < balanced_hands.length; i++){
			assertTrue(balanced.satisfiedBy(balanced_hands[i]));
		}
	}
	
	@Test
	public void rDealGenerationPointsTest(){
		ArrayList<PlayerConstraint> p_constraints = new ArrayList<PlayerConstraint>();
		
		p_constraints.add(new PlayerConstraint(0, points16OrMore));
		p_constraints.add(new PlayerConstraint(1, points16OrMore));
		
		Hand[] point_hands = RDealGenerator.generateRDeal(p_constraints);
		
		assertTrue(points16OrMore.satisfiedBy(point_hands[0]));
		assertTrue(points16OrMore.satisfiedBy(point_hands[1]));
	}
	
	@Test(timeout=500)
	public void rDeal1000AllBalancedTest(){
		//Fails if this takes longer than 0.5 seconds
		
		ArrayList<PlayerConstraint> p_constraints = new ArrayList<PlayerConstraint>();
		
		p_constraints.add(new PlayerConstraint(0, balanced));
		p_constraints.add(new PlayerConstraint(1, balanced));
		p_constraints.add(new PlayerConstraint(2, balanced));
		p_constraints.add(new PlayerConstraint(3, balanced));
		
		ArrayList<Hand[]> balanced_hands = RDealGenerator.generateRDeals(p_constraints, 1000);
		
		for(Hand[] hands : balanced_hands){
			for(int i = 0; i < hands.length; i++){
				assertTrue(balanced.satisfiedBy(hands[i]));
			}
		}
	}
}
