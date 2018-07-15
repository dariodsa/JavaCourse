package hr.fer.zemris.java.hw07.shell.lexer;
/**
 * Class {@link Lexer} turns {@link String} into sequence of 
 * tokens which can be get by calling {@link #getNextToken()}, you can 
 * also get current token by calling {@link #getToken()}. 
 * @author dario
 *
 */
public class Lexer {
	
	/**
	 * query for lexer
	 */
	private String query;
	/**
	 * current token
	 */
	private LexerToken token;
	
	/**
	 * current index in query
	 */
	private int current;
	
	/**
	 * Constructs {@link Lexer} with query as parameter which will 
	 * be turn into sequence of tokens. 
	 * @param query {@link String} which will be passed in lexer process
	 */
	public Lexer(String query) {
		this.query = query;
		this.current = 0;
	}
	/**
	 * Returns next token in the query.
	 * @return {@link LexerToken} new token 
	 */
	public LexerToken getNextToken() {
		
		StringBuilder builder = new StringBuilder();
		if(current == query.length()) {
			this.token = new LexerToken(LexerType.EOF, "");
			return this.token;
		}
		
		for(; current < query.length(); ++current) {
			if(query.length() >= current + 2 && query.substring(current, current+2).equals("${")) {
				if(builder.length() == 0) {
					this.token = new LexerToken(LexerType.OPEN_BRACKET, "");
					current += 2;
					break;
				} else {
					this.token = new LexerToken(LexerType.STRING, builder.toString());
					builder.setLength(0);
					break;
				}
			} else if(query.charAt(current) == '}') {
				if(builder.length() == 0) {
					this.token = new LexerToken(LexerType.CLOSE_BRACKET, "");
					++current;
					break;
				} else {
					this.token = new LexerToken(LexerType.STRING, builder.toString());
					builder.setLength(0);
					break;
				}
			} else if(query.charAt(current) == ','){
				if(builder.length() == 0) {
					this.token = new LexerToken(LexerType.COMMA, "");
					++current;
					break;
				} else {
					this.token = new LexerToken(LexerType.STRING, builder.toString());
					builder.setLength(0);
					break;
				}
			} else {
				builder.append(query.charAt(current));
			}
		}
		
		if(builder.length() != 0) {
			this.token = new LexerToken(LexerType.STRING, builder.toString());
		} 
		return this.token;
	}
	/**
	 * Returns currently generated token.
	 * @return previously generated token
	 */
	public LexerToken getToken() {
		return this.token;
	}
}
