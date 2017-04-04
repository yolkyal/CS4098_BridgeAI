package unit_tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import bridge_data_structures.*;
import constraints.*;

public class ConstraintTests {
	
	Hand hand1 = new Hand("A895.KQ4.J97.A45");
	Hand hand2 = new Hand("AT432..J982.8653");
	Hand hand3 = new Hand("AT762.Q9832.T4.A");
	Hand hand4 = new Hand("AKQ.KQJ.A7654.42");

	@Test
	public void testBalanceConstraints(){
		//Balanced hand 12 -14 points, opening.
		
		BalanceConstraint bc1 = new BalanceConstraint(true);
		BalanceConstraint bc2 = new BalanceConstraint(false);
		assertTrue(bc1.satisfiedBy(hand1));
		assertFalse(bc1.satisfiedBy(hand2));
		assertTrue(bc2.satisfiedBy(hand2));
		assertFalse(bc1.satisfiedBy(hand3));
		assertTrue(bc1.satisfiedBy(hand4));
	}
	
	@Test
	public void testPointConstraints(){
		PointConstraint pc1 = new PointConstraint(14, 14);
		PointConstraint pc2 = new PointConstraint(5, 14);
		
		assertTrue(pc1.satisfiedBy(hand1));
		assertTrue(pc2.satisfiedBy(hand1));
		assertTrue(pc2.satisfiedBy(hand2));
		assertFalse(pc1.satisfiedBy(hand2));
		
		//No distributional testing yet.
	}
	
	@Test
	public void testNumInASuitConstraints(){
		NumInASuitConstraint niasc1 = new NumInASuitConstraint(5);
		
		assertFalse(niasc1.satisfiedBy(hand1));
		assertTrue(niasc1.satisfiedBy(hand2));
		assertTrue(niasc1.satisfiedBy(hand3));
	}
	
	@Test
	public void testNumPlayingTricks(){
		NumPlayingTricksConstraint nptc1 = new NumPlayingTricksConstraint(3);
		NumPlayingTricksConstraint nptc2 = new NumPlayingTricksConstraint(5);
		assertTrue(nptc1.satisfiedBy(hand1));
		assertTrue(nptc2.satisfiedBy(hand4));
		assertFalse(nptc2.satisfiedBy(hand1));
	}
	
	@Test
	public void testNumInSpecifiedSuit(){
		NumInSpecifiedSuitConstraint nissc1 = new NumInSpecifiedSuitConstraint(5, Suit.HEART);
		NumInSpecifiedSuitConstraint nissc2 = new NumInSpecifiedSuitConstraint(5, Suit.DIAMOND);
		
		assertFalse(nissc1.satisfiedBy(hand1));
		assertFalse(nissc2.satisfiedBy(hand2));
		assertTrue(nissc1.satisfiedBy(hand3));
		assertTrue(nissc2.satisfiedBy(hand4));
	}
	
	@Test
	public void testLongestSuit(){
		LongestSuitConstraint lsc1 = new LongestSuitConstraint(Suit.SPADE);
		LongestSuitConstraint lsc2 = new LongestSuitConstraint(Suit.HEART);
		
		assertTrue(lsc1.satisfiedBy(hand1));
		assertTrue(lsc1.satisfiedBy(hand2));
		assertFalse(lsc1.satisfiedBy(hand3));
		assertFalse(lsc2.satisfiedBy(hand3));
	}
	
	@Test
	public void testNumInAMinorSuit(){
		NumInAMinorSuitConstraint nimsc1 = new NumInAMinorSuitConstraint(4);
		
		assertFalse(nimsc1.satisfiedBy(hand1));
		assertTrue(nimsc1.satisfiedBy(hand2));
		assertFalse(nimsc1.satisfiedBy(hand3));
		assertTrue(nimsc1.satisfiedBy(hand4));
	}
	
	@Test
	public void testNumInMajorSuit(){
		NumInAMajorSuitConstraint nimsc1 = new NumInAMajorSuitConstraint(5);
		
		assertFalse(nimsc1.satisfiedBy(hand1));
		assertTrue(nimsc1.satisfiedBy(hand2));
		assertTrue(nimsc1.satisfiedBy(hand3));
		assertFalse(nimsc1.satisfiedBy(hand4));
	}
	
	@Test
	public void testNumSuitsOfSizeConstraint(){
		NumSuitsOfSizeConstraint nsosc1 = new NumSuitsOfSizeConstraint(2, 4);
		
		assertFalse(nsosc1.satisfiedBy(hand1));
		assertTrue(nsosc1.satisfiedBy(hand2));
		assertTrue(nsosc1.satisfiedBy(hand3));
		assertFalse(nsosc1.satisfiedBy(hand4));
	}

}
