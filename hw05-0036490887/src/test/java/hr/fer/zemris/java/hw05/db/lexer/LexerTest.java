package hr.fer.zemris.java.hw05.db.lexer;
import org.junit.*;

import hr.fer.zemris.java.hw05.db.exceptions.LikeOperatorException;
import hr.fer.zemris.java.hw05.db.exceptions.UnknownVariableName;
import hr.fer.zemris.java.hw05.db.lexer.Lexer;
import hr.fer.zemris.java.hw05.db.lexer.TokenType;

public class LexerTest {

	@Test 
	public void testVariable() {
		Lexer lexer = new Lexer("mirko");
		Assert.assertEquals(TokenType.VARIABLE, lexer.nextToken().getType());
		Assert.assertEquals("mirko", lexer.getToken().getValue());
	}
	
	@Test 
	public void testAnd() {
		Lexer lexer = new Lexer("anD");
		Assert.assertEquals(TokenType.AND, lexer.nextToken().getType());
	}
	
	@Test 
	public void testOperatorLess() {
		Lexer lexer = new Lexer("<");
		Assert.assertEquals(TokenType.OPERATOR, lexer.nextToken().getType());
		Assert.assertEquals("<", lexer.getToken().getValue());
	}
	
	@Test 
	public void testOperatorGreater() {
		Lexer lexer = new Lexer(">");
		Assert.assertEquals(TokenType.OPERATOR, lexer.nextToken().getType());
		Assert.assertEquals(">", lexer.getToken().getValue());
	}
	
	@Test 
	public void testOperatorLike() {
		Lexer lexer = new Lexer("LIKE");
		Assert.assertEquals(TokenType.OPERATOR, lexer.nextToken().getType());
		Assert.assertEquals("LIKE", lexer.getToken().getValue());
	}
	
	@Test 
	public void testOperatorEquals() {
		Lexer lexer = new Lexer("=");
		Assert.assertEquals(TokenType.OPERATOR, lexer.nextToken().getType());
		Assert.assertEquals("=", lexer.getToken().getValue());
	}
	
	
	@Test 
	public void testOperatorLikeWIthString() {
		Lexer lexer = new Lexer("LIKE \"miro*\"");
		Assert.assertEquals(TokenType.OPERATOR, lexer.nextToken().getType());
		Assert.assertEquals("LIKE", lexer.getToken().getValue());
		Assert.assertEquals(TokenType.TEXT, lexer.nextToken().getType());
		Assert.assertEquals("miro*", lexer.getToken().getValue());
	}
	
	@Test
	public void testUnknownVariableName() {
		Lexer lexer = new Lexer("mirko");
		Assert.assertEquals(TokenType.VARIABLE, lexer.nextToken().getType());
		Assert.assertEquals("mirko", lexer.getToken().getValue());
		
		
		
	}
	
	@Test 
	public void testOperatorWithoutSpace() {
		Lexer lexer = new Lexer("jmbag<\"00364908\"");
		Assert.assertEquals(TokenType.VARIABLE, lexer.nextToken().getType());
		Assert.assertEquals(TokenType.OPERATOR, lexer.nextToken().getType());
		Assert.assertEquals("<", lexer.getToken().getValue());
		Assert.assertEquals(TokenType.TEXT, lexer.nextToken().getType());
		Assert.assertEquals("00364908", lexer.getToken().getValue());
		Assert.assertEquals(TokenType.EOF, lexer.nextToken().getType());
	}
	
}
