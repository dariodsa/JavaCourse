package hr.fer.zemris.java.hw07.shell.builder;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.exceptions.ShellException;
import hr.fer.zemris.java.hw07.shell.lexer.Lexer;
import hr.fer.zemris.java.hw07.shell.lexer.LexerType;

/**
 * {@link NameBuilderParser} parse expression and creates all builders, with last one, {@link NameBuilderGenerator}. 
 * It returns {@link NameBuilderGenerator} in the method {@link #getNameBuilder()}, which holds references to 
 * all builders. 
 * @author dario
 *
 */
public class NameBuilderParser {
	
	/**
	 * {@link NameBuilder} result, last one
	 */
	private NameBuilder result;
	
	/**
	 * Constructs {@link NameBuilderParser} with the {@link String} expression which will 
	 * be parsed and turn into {@link NameBuilder}s
	 * @param expression string expression
	 */
	public NameBuilderParser(String expression) {
	
		List<NameBuilder> list = new ArrayList<>();
		Lexer lexer = new Lexer(expression);
		while(lexer.getNextToken().getType() != LexerType.EOF) {
			
			switch(lexer.getToken().getType()) {
				case STRING:
					list.add(new NameBuilderString(lexer.getToken().getValue()));
					break;
				case OPEN_BRACKET:
					String value1 = lexer.getNextToken().getValue();
					lexer.getNextToken();
					
					if(lexer.getToken().getType() == LexerType.COMMA) {
						String value2 = lexer.getNextToken().getValue();
						if(value2.charAt(0) == '0' && value2.length() > 1) {
							list.add(new NameBuilderStringSize(Integer.parseInt(value1), Integer.parseInt(value2.substring(1)), '0'));
						} else {
							list.add(new NameBuilderStringSize(Integer.parseInt(value1), Integer.parseInt(value2.substring(1)), ' '));
						}
						if(lexer.getNextToken().getType() != LexerType.CLOSE_BRACKET) {
							throw new ShellException("Parser error.");
						}
						
					} else if(lexer.getToken().getType() != LexerType.CLOSE_BRACKET) {
						throw new ShellException("Parser error, close bracket.");
					}
					
					else {
						list.add(new NameBuilderStringSize(Integer.parseInt(value1), 0, '0'));
						//builder.append(matcher.group(Integer.parseInt(value1)));
					}
					
					break;
				default:
					throw new ShellException("Parser error.");
					
			}
		}
		this.result = new NameBuilderGenerator(list);
	}
	/**
	 * Returns {@link NameBuilderGenerator} which returns last generated builder which holds 
	 * references to all builders. 
	 * @return {@link NameBuilder} last generated builder
	 */
	public NameBuilder getNameBuilder() {
		return this.result;
	}
	
}
