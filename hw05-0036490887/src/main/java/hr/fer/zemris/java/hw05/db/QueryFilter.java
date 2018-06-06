package hr.fer.zemris.java.hw05.db;

import java.util.List;

import hr.fer.zemris.java.hw05.db.exceptions.LikeOperatorException;
/**
 * Class {@link QueryFilter} offers you to init {@link ConditionalExpression} as parameter and to return true 
 * if the given {@link StudentRecord} passes all given expressions in the method {@link #accepts(StudentRecord)}.
 * @author dario
 *
 */
public class QueryFilter implements IFilter{

	/**
	 * list of expressions by which will student record be filtered
	 */
	private List<ConditionalExpression> expressions;
	/**
	 * Constructs {@link QueryFilter} with the list of {@link ConditionalExpression}.
	 * @param expressions expressions which will be used in accepts method.
	 */
	public QueryFilter(List<ConditionalExpression> expressions) {
		this.expressions = expressions;
	}
	/**
	 * Returns true if the given {@link StudentRecord} pass all expressions, 
	 * false otherwise.
	 * @param student record which will be tested
	 * @return true if the above statament is true, false otherwise
	 * @throws LikeOperatorException 
	 */
	@Override
	public boolean accepts(StudentRecord record) throws LikeOperatorException {
		
		for(ConditionalExpression expression : expressions) {
			if(!expression.getComparisonOperator().satisfied(
               expression.getFieldGetter().get(record),
               expression.getStringLiteral())) { 
				return false;
			}
		}
		return true;
	}
	
}
