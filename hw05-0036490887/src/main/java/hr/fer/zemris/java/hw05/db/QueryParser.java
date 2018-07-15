package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.db.exceptions.ParserException;
import hr.fer.zemris.java.hw05.db.exceptions.UnknownVariableName;
import hr.fer.zemris.java.hw05.db.lexer.Lexer;
import hr.fer.zemris.java.hw05.db.lexer.LexerException;
import hr.fer.zemris.java.hw05.db.lexer.TokenType;

/**
 * Class {@link QueryParser} takes {@link String} query and constructs list of {@link ConditionalExpression}
 * which can be get by {@link #getQuery() using this method}. You can also ask if the given query is 
 * direct query by {@link #isDirectQuery() method}. 
 * 
 * @version 1.0
 * @since 1.0
 * @author dario
 *
 */
public class QueryParser {
	
	/**
	 * list of the {@link ConditionalExpression} which was parsed from the string representation of the query 
	 */
	private List<ConditionalExpression> expressions;
	/**
	 * string representation of the query
	 */
	private String query;
	/**
	 * Constructs {@link QueryParser} with the string query as the parameter. It immediately calls 
	 * method {@link #parseMe()} so it will generate {@link #expressions}, which can be get by
	 * using method {@link #getQuery()}
	 * @param query
	 */
	public QueryParser(String query) {
		this.query = query;
		expressions = new ArrayList<>();
		try {
			parseMe();
		} catch(LexerException ex) {
			throw new ParserException(ex.getMessage());
		}
	}
	/**
	 * @throws ParserException
	 */
	private void parseMe() {
		Lexer lexer = new Lexer(query);
		boolean expression = true;
		while(lexer.nextToken().getType() != TokenType.EOF) {
			if(lexer.getToken().getType() == TokenType.VARIABLE && expression) {
				expression = false;
				String variable = lexer.getToken().getValue();
				if(lexer.nextToken().getType() != TokenType.OPERATOR) {
					throw new ParserException("After variable comes operator.");
				}
				String op = lexer.getToken().getValue();
				if(lexer.nextToken().getType() != TokenType.TEXT) {
					throw new ParserException("After operator comes variable.");
				}
				String text = lexer.getToken().getValue();
				
				IFieldValueGetter fieldGetter = null;
				switch(FieldValueGetters.Enum.fromString(variable)) {
					case FIRST_NAME:
						fieldGetter = FieldValueGetters.FIRST_NAME;
						break;
					case JMBAG:
						fieldGetter = FieldValueGetters.JMBAG;
						break;
					case LAST_NAME:
						fieldGetter = FieldValueGetters.LAST_NAME;
						break;
					default:
						throw new UnknownVariableName(variable);
						
				}
				
				IComparisonOperator operator = null;
				switch(ComparisonOperators.Enum.fromString(op)) {
				case EQUALS:
					operator = ComparisonOperators.EQUALS;
					break;
				case GREATER:
					operator = ComparisonOperators.GREATER;
					break;
				case GREATER_OR_EQUAL:
					operator = ComparisonOperators.GREATER_OR_EQUALS;
					break;
				case LESS:
					operator = ComparisonOperators.LESS;
					break;
				case LESS_OR_EQUAL:
					operator = ComparisonOperators.LESS_OR_EQUALS;
					break;
				case LIKE:
					operator = ComparisonOperators.LIKE;
					break;
				case NOT_EQUALS:
					operator = ComparisonOperators.NOT_EQUALS;
					break;
				default:
					throw new IllegalArgumentException();
					
				}
				
				ConditionalExpression conditionalExpression = new ConditionalExpression(fieldGetter, text, operator);
				expressions.add(conditionalExpression);
				
			} else if(lexer.getToken().getType() == TokenType.AND && !expression) {
				expression = true;
			} else {
				throw new ParserException("I don't know how to parse you.");
			}
		}
	}

	/**
	 * Returns true if query is form jmbag = "something" and no other expressions inside.
	 * @return true if the above expression is satisfied, false otherwise
	 */
	public boolean isDirectQuery() {
		return expressions.size() == 1 &&
			   expressions.get(0).getFieldGetter() == FieldValueGetters.JMBAG &&
			   expressions.get(0).getComparisonOperator() == ComparisonOperators.EQUALS;
	}
	/**
	 * Returns the jmbag which was asked in the query, if and only if the query was direct query.
	 * If the query is not direct query method throw {@link IllegalStateException}.
	 * 
	 * @return {@link String} jmbag which was asked in the direct query
	 * @throws IllegalStateException
	 */
	public String getQueriedJMBAG() {
		if(!isDirectQuery()) throw new IllegalStateException("This is not direct query.");
		return expressions.get(0).getStringLiteral();
	}
	/**
	 * Returns the list of the {@link ConditionalExpression} which was generated in 
	 * the method {@link #parseMe()}.
	 * @return list of expression which query asked
	 */
	public List<ConditionalExpression> getQuery() {
		return this.expressions;
	}
}
