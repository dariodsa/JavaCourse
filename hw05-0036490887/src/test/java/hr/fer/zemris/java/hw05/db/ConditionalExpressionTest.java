package hr.fer.zemris.java.hw05.db;
import org.junit.*;

import hr.fer.zemris.java.hw05.db.ComparisonOperators;
import hr.fer.zemris.java.hw05.db.ConditionalExpression;
import hr.fer.zemris.java.hw05.db.FieldValueGetters;
import hr.fer.zemris.java.hw05.db.StudentRecord;

public class ConditionalExpressionTest {
	
	private ConditionalExpression expr;
	@Before
	public void init() {
		expr = new ConditionalExpression(
				  FieldValueGetters.LAST_NAME,
				  "Bos*",
				  ComparisonOperators.LIKE
				);
	}
	@Test
	public void testGetStringLiteral() {
		
		Assert.assertEquals("Bos*", expr.getStringLiteral());
	}
	
	@Test
	public void testGetComparisonOperator() {
		Assert.assertEquals(ComparisonOperators.LIKE, expr.getComparisonOperator());
	}
	
	@Test
	public void testGetFieldGetter() {
		Assert.assertEquals(FieldValueGetters.LAST_NAME, expr.getFieldGetter());
	}
	
	@Test
	public void testRecordSatisfiesTrue() {
		StudentRecord record = new StudentRecord("1", "Bosnjak", "", "");
		Assert.assertTrue(expr.getComparisonOperator().satisfied(expr.getFieldGetter().get(record), expr.getStringLiteral()));
	}
	
	@Test
	public void testRecordSatisfiesFalse() {
		StudentRecord record = new StudentRecord("1", "Bonjka", "", "");
		Assert.assertFalse(expr.getComparisonOperator().satisfied(expr.getFieldGetter().get(record), expr.getStringLiteral()));
	}
}