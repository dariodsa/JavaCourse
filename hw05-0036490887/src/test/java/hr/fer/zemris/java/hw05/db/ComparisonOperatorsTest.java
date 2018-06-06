package hr.fer.zemris.java.hw05.db;

import org.junit.*;

import hr.fer.zemris.java.hw05.db.ComparisonOperators;
import hr.fer.zemris.java.hw05.db.IComparisonOperator;
import hr.fer.zemris.java.hw05.db.exceptions.LikeOperatorException;

public class ComparisonOperatorsTest {

	@Test
	public void testLessOperator() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		Assert.assertTrue(oper.satisfied("Ana", "Jasna"));
	}
	
	@Test
	public void testGreaterOperator() {
		IComparisonOperator oper = ComparisonOperators.GREATER;
		Assert.assertTrue(oper.satisfied("Petra", "Jasna"));
	}
	
	@Test
	public void testEqualsOperator() {
		IComparisonOperator oper = ComparisonOperators.EQUALS;
		Assert.assertTrue(oper.satisfied("Petra", "Petra"));
	}
	
	@Test
	public void testGreaterOrEqualsOperator() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		Assert.assertTrue(oper.satisfied("Petra", "Petra"));
		Assert.assertTrue(oper.satisfied("Vetra", "Petra"));
	}
	
	@Test
	public void testLessOrEqualsOperator() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		Assert.assertTrue(oper.satisfied("Petra", "Petra"));
		Assert.assertTrue(oper.satisfied("Klara", "Petra"));
	}
	
	@Test
	public void testNotEqualsOperator() {
		IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;
		Assert.assertTrue(oper.satisfied("Petra", "Mitra"));
		Assert.assertFalse(oper.satisfied("Petra", "Petra"));
	}
	
	@Test
	public void testLikeOperator() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		Assert.assertFalse(oper.satisfied("Zagreb", "Aba*"));
		Assert.assertTrue(oper.satisfied("Zagreb", "Zag*b"));
		Assert.assertTrue(oper.satisfied("Zagreb", "Zagre*b"));
		Assert.assertFalse(oper.satisfied("AAA", "AA*AA"));
		Assert.assertTrue(oper.satisfied("AAAA", "AA*AA"));
		Assert.assertTrue(oper.satisfied("MirkecMirko", "*o"));
		Assert.assertTrue(oper.satisfied("Bosnjak", "B*"));
		Assert.assertFalse(oper.satisfied("Cilic", "B*"));
		Assert.assertFalse(oper.satisfied("Petra", "Mitra"));
		
	}
	@Test (expected = LikeOperatorException.class)
	public void testLikeOperatorException() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		oper.satisfied("Zagreb", "A*b*a");
	}

}
