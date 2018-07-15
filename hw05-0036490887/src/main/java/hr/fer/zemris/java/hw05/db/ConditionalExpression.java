package hr.fer.zemris.java.hw05.db;
/**
 * Class {@link ConditionalExpression} constructs triple pair of objects 
 * which will be used in expressing the simple comparison operators.
 * 
 * @see FieldValueGetters
 * @see ComparisonOperators
 * @author dario
 *
 */
public class ConditionalExpression {
	/**
	 * field getter, check {@link FieldValueGetters}
	 */
	private IFieldValueGetter fieldGetter;
	/**
	 * string literal
	 */
	private String stringLiteral;
	/**
	 * comaparison operator, check {@link ComparisonOperators}
	 */
	private IComparisonOperator comparisonOperator;
	
	/**
	 * Constructs {@link ConditionalExpression} with the field getter, literal and operator
	 * as parameters.
	 * @param fieldGetter {@link FieldValueGetters} value
	 * @param stringLiteral string used in comparison 
	 * @param comparisonOperator {@link ComparisonOperators} value
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral, IComparisonOperator comparisonOperator) {
		this.fieldGetter = fieldGetter;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = comparisonOperator;
	}
	/**
	 * Returns field getter of the {@link ConditionalExpression}.
	 * @return {@link #fieldGetter} of object
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}
	/**
	 * Returns string literal of the {@link ConditionalExpression}.
	 * @return {@link #stringLiteral} of object
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}
	/**
	 * Returns comparison operator of the {@link ConditionalExpression}.
	 * @return {@link #comparisonOperator} of object
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
}
