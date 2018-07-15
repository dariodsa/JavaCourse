package hr.fer.zemris.java.hw05.db;
import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import hr.fer.zemris.java.hw05.db.ComparisonOperators;
import hr.fer.zemris.java.hw05.db.ConditionalExpression;
import hr.fer.zemris.java.hw05.db.FieldValueGetters;
import hr.fer.zemris.java.hw05.db.QueryFilter;
import hr.fer.zemris.java.hw05.db.StudentRecord;

public class QueryFilterTest {

	@Test
	public void testFirstExample() {
		List<ConditionalExpression> expressions = new ArrayList<>();
		expressions.add(new ConditionalExpression(FieldValueGetters.FIRST_NAME, "Dario", ComparisonOperators.EQUALS));
		QueryFilter filter = new QueryFilter(expressions);
		Assert.assertTrue(filter.accepts(new StudentRecord("0","sin","Dario","1")));
	}
	
	@Test
	public void testSecondExample() {
		List<ConditionalExpression> expressions = new ArrayList<>();
		expressions.add(new ConditionalExpression(FieldValueGetters.FIRST_NAME, "D*io", ComparisonOperators.LIKE));
		QueryFilter filter = new QueryFilter(expressions);
		Assert.assertTrue(filter.accepts(new StudentRecord("0","sin","Dario","1")));
	}
	
	@Test
	public void testTwoExpressions() {
		List<ConditionalExpression> expressions = new ArrayList<>();
		expressions.add(new ConditionalExpression(FieldValueGetters.FIRST_NAME, "A", ComparisonOperators.GREATER));
		expressions.add(new ConditionalExpression(FieldValueGetters.FIRST_NAME, "Z", ComparisonOperators.LESS));
		QueryFilter filter = new QueryFilter(expressions);
		Assert.assertTrue(filter.accepts(new StudentRecord("0","sin","Dario","1")));
	}
	
	@Test
	public void testTwoExpressionsFalse() {
		List<ConditionalExpression> expressions = new ArrayList<>();
		expressions.add(new ConditionalExpression(FieldValueGetters.FIRST_NAME, "E", ComparisonOperators.GREATER));
		expressions.add(new ConditionalExpression(FieldValueGetters.FIRST_NAME, "Z", ComparisonOperators.LESS));
		QueryFilter filter = new QueryFilter(expressions);
		Assert.assertFalse(filter.accepts(new StudentRecord("0","sin","Dario","1")));
	}
}
