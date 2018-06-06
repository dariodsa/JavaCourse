package hr.fer.zemris.java.hw03;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.lexer.LexerException;
import hr.fer.zemris.java.custom.scripting.lexer.LexerState;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;
import org.junit.Assert;

public class Test {

	@org.junit.Test
	public void testLexer1() {
		String data = "";
		Lexer lexer = new Lexer(data.toCharArray());
		Assert.assertTrue(TokenType.EOF == lexer.nextToken().getType());
		Assert.assertTrue(null == lexer.nextToken().getValue());
	}

	@org.junit.Test
	public void testLexer2() {
		String data = "this is";
		Lexer lexer = new Lexer(data.toCharArray());
		Assert.assertTrue(TokenType.TEXT == lexer.nextToken().getType());
		Assert.assertTrue(TokenType.EOF == lexer.nextToken().getType());
	}

	@org.junit.Test
	public void testLexer3() {
		String data = "{$FOR$}";
		Lexer lexer = new Lexer(data.toCharArray());
		Assert.assertTrue(TokenType.TAG_SYMBOL_OPEN == lexer.nextToken().getType());
		lexer.setLexerState(LexerState.INSIDE_TAG);
		Assert.assertEquals(TokenType.VARIABLE, lexer.nextToken().getType());
		Assert.assertTrue(TokenType.TAG_SYMBOL_CLOSE == lexer.nextToken().getType());
	}

	@org.junit.Test
	public void testLexer4() {
		String data = "{$       FOR		$}";
		Lexer lexer = new Lexer(data.toCharArray());
		Assert.assertTrue(TokenType.TAG_SYMBOL_OPEN == lexer.nextToken().getType());
		lexer.setLexerState(LexerState.INSIDE_TAG);
		Assert.assertEquals(TokenType.VARIABLE, lexer.nextToken().getType());
		Assert.assertTrue(TokenType.TAG_SYMBOL_CLOSE == lexer.nextToken().getType());
	}

	@org.junit.Test
	public void testLexer5() {
		String data = "{$  fOr $}";
		Lexer lexer = new Lexer(data.toCharArray());
		Assert.assertTrue(TokenType.TAG_SYMBOL_OPEN == lexer.nextToken().getType());
		lexer.setLexerState(LexerState.INSIDE_TAG);
		Assert.assertEquals(TokenType.VARIABLE, lexer.nextToken().getType());
		Assert.assertTrue(TokenType.TAG_SYMBOL_CLOSE == lexer.nextToken().getType());
	}

	@org.junit.Test
	public void testLexer6() {
		String data = "{$= i i * @sin \"000\" @Dec $}";
		Lexer lexer = new Lexer(data.toCharArray());
		Assert.assertTrue(TokenType.TAG_SYMBOL_OPEN == lexer.nextToken().getType());
		lexer.setLexerState(LexerState.INSIDE_TAG);
		Assert.assertEquals(TokenType.VARIABLE, lexer.nextToken().getType());
		Assert.assertEquals(TokenType.VARIABLE, lexer.nextToken().getType());
		Assert.assertEquals(TokenType.VARIABLE, lexer.nextToken().getType());
		Assert.assertEquals(TokenType.OPERATOR, lexer.nextToken().getType());
		Assert.assertEquals(TokenType.FUNCTION, lexer.nextToken().getType());
		Assert.assertEquals(TokenType.TEXT, lexer.nextToken().getType());
		Assert.assertEquals(TokenType.FUNCTION, lexer.nextToken().getType());
		Assert.assertEquals(TokenType.TAG_SYMBOL_CLOSE, lexer.nextToken().getType());

	}

	@org.junit.Test(expected = SmartScriptParserException.class)
	public void testParserException() {
		String data = "{$FOR$}";
		SmartScriptParser parser = new SmartScriptParser(data);
	}

	@org.junit.Test
	public void testLexerNegativeNumbers() {
		String data = "{$= -150 $}";
		Lexer lexer = new Lexer(data.toCharArray());
		Assert.assertTrue(TokenType.TAG_SYMBOL_OPEN == lexer.nextToken().getType());
		lexer.setLexerState(LexerState.INSIDE_TAG);
		Assert.assertEquals(TokenType.VARIABLE, lexer.nextToken().getType());
		Assert.assertEquals(TokenType.INTEGER, lexer.nextToken().getType());
		Assert.assertEquals(-150, lexer.getToken().getValue());
	}

	@org.junit.Test(expected = LexerException.class)
	public void testLexerTooBigNumbers() {
		String data = "{$= 12345678946512312345 $}";
		Lexer lexer = new Lexer(data.toCharArray());
		Assert.assertTrue(TokenType.TAG_SYMBOL_OPEN == lexer.nextToken().getType());
		lexer.setLexerState(LexerState.INSIDE_TAG);
		Assert.assertEquals(TokenType.VARIABLE, lexer.nextToken().getType());
		lexer.nextToken();

	}

	@org.junit.Test(expected = LexerException.class)
	public void testLexerWrongVariable() {
		String data = "{$= 12ad1235156a3 $}";
		Lexer lexer = new Lexer(data.toCharArray());
		Assert.assertTrue(TokenType.TAG_SYMBOL_OPEN == lexer.nextToken().getType());
		lexer.setLexerState(LexerState.INSIDE_TAG);
		Assert.assertEquals(TokenType.VARIABLE, lexer.nextToken().getType());
		lexer.nextToken();

	}

	@org.junit.Test(expected = SmartScriptParserException.class)
	public void testParserUnkownTag() {
		String data = "{$mirko$}";
		SmartScriptParser parser = new SmartScriptParser(data);

	}

	@org.junit.Test(expected = SmartScriptParserException.class)
	public void testParserForTooManyArguments() {
		String data = "{$ fOr year 1 10 \"1\" \"10\" $}";
		SmartScriptParser parser = new SmartScriptParser(data);
	}

	@org.junit.Test(expected = SmartScriptParserException.class)
	public void testParserForTooFewArguments() {
		String data = "{$ fOr year $}";
		SmartScriptParser parser = new SmartScriptParser(data);
	}

	@org.junit.Test(expected = SmartScriptParserException.class)
	public void testParserForTooFewArguments2() {
		String data = "{$ fOr year 1 $}";
		SmartScriptParser parser = new SmartScriptParser(data);
	}

	@org.junit.Test(expected = SmartScriptParserException.class)
	public void testParserForTooFewArguments3() {
		String data = "{$ fOr year 845$}";
		SmartScriptParser parser = new SmartScriptParser(data);
	}

	@org.junit.Test(expected = SmartScriptParserException.class)
	public void testParserForTooManyArguments2() {
		String data = "{$ fOr year 1 10 1 3 4 $}";
		SmartScriptParser parser = new SmartScriptParser(data);
	}

	@org.junit.Test(expected = SmartScriptParserException.class)
	public void testParserForVariableName() {
		String data = "{$ fOr 3 1 10 1$}";
		SmartScriptParser parser = new SmartScriptParser(data);
	}

	@org.junit.Test(expected = SmartScriptParserException.class)
	public void testParserForFunctionElement() {
		String data = "{$ fOr year @sin 10 $}";
		SmartScriptParser parser = new SmartScriptParser(data);
	}

	@org.junit.Test
	public void testParserFor() {
		String data = "{$ fOr sco_re       \"-1\"10 \"1\" $}{$  enD$}";
		SmartScriptParser parser = new SmartScriptParser(data);
	}

	@org.junit.Test(expected = SmartScriptParserException.class)
	public void testParserForWithoutEndTag() {
		String data = "{$ fOr sco_re       \"-1\"10 \"1\" $}";
		SmartScriptParser parser = new SmartScriptParser(data);
	}

	@org.junit.Test
	public void testLexerFor() {
		String data = "{$ fOr sco_re       \"-1\"10 \"1\" $}";
		Lexer lexer = new Lexer(data.toCharArray());

		Assert.assertTrue(TokenType.TAG_SYMBOL_OPEN == lexer.nextToken().getType());
		lexer.setLexerState(LexerState.INSIDE_TAG);
		Assert.assertEquals(TokenType.VARIABLE, lexer.nextToken().getType());
		Assert.assertEquals(TokenType.VARIABLE, lexer.nextToken().getType());

		Assert.assertEquals(0, lexer.getToken().getValue().toString().compareTo("sco_re"));

		Assert.assertEquals(TokenType.TEXT, lexer.nextToken().getType());
		

		Assert.assertEquals(TokenType.INTEGER, lexer.nextToken().getType());
		Assert.assertEquals(10, lexer.getToken().getValue());

		Assert.assertEquals(TokenType.TEXT, lexer.nextToken().getType());

		//Assert.assertEquals(0, lexer.getToken().getValue().toString().compareTo("1"));

		Assert.assertEquals(TokenType.TAG_SYMBOL_CLOSE, lexer.nextToken().getType());
	}

	@org.junit.Test(expected = LexerException.class)
	public void testLexerWrongNameVariable() {
		String data = "{$ fOr _a21$}";
		Lexer lexer = new Lexer(data.toCharArray());

		Assert.assertTrue(TokenType.TAG_SYMBOL_OPEN == lexer.nextToken().getType());
		lexer.setLexerState(LexerState.INSIDE_TAG);
		Assert.assertEquals(TokenType.VARIABLE, lexer.nextToken().getType());

		lexer.nextToken();
	}

	@org.junit.Test(expected = LexerException.class)
	public void testLexerWrongNameVariable2() {
		String data = "{$ fOr _a21$}";
		Lexer lexer = new Lexer(data.toCharArray());

		Assert.assertTrue(TokenType.TAG_SYMBOL_OPEN == lexer.nextToken().getType());
		lexer.setLexerState(LexerState.INSIDE_TAG);
		Assert.assertEquals(TokenType.VARIABLE, lexer.nextToken().getType());
		lexer.nextToken();
	}

	@org.junit.Test
	public void testFromExample2() {
		String data = "A tag follows {$= \"Joe \\\"Long\\\" Smith\"$}";
		SmartScriptParser parser = new SmartScriptParser(data);

		Assert.assertEquals(2, parser.getDocumentNode().numberOfChildren());
		Assert.assertTrue(parser.getDocumentNode().getChild(0) instanceof TextNode);
		Assert.assertTrue(parser.getDocumentNode().getChild(1) instanceof EchoNode);
		Assert.assertEquals(1, ((EchoNode) (parser.getDocumentNode().getChild(1))).getElements().length);

	}

	@org.junit.Test
	public void testFromExample3Parser() {
		String data = "\\{$=1$}";
		SmartScriptParser parser = new SmartScriptParser(data);

		Assert.assertEquals(1, parser.getDocumentNode().numberOfChildren());
		Assert.assertTrue(parser.getDocumentNode().getChild(0) instanceof TextNode);
	}

	@org.junit.Test
	public void testFromExample3Lexer() {
		String data = "\\{$=1$}";
		Lexer lexer = new Lexer(data.toCharArray());

		Assert.assertEquals(TokenType.TEXT, lexer.nextToken().getType());
	}

	@org.junit.Test
	public void testEscapingLexer() {
		String data = "\"Some \\\\ test X.\"";
		Lexer lexer = new Lexer(data.toCharArray());
		lexer.setLexerState(LexerState.INSIDE_TAG);
		lexer.nextToken();
		System.out.println(lexer.getToken().getValue());
		//Assert.assertEquals(0, new String("Some \\ test X.").compareTo(lexer.getToken().getValue().toString()));

	}

	@org.junit.Test(expected = LexerException.class)
	public void testEscapingLexer2() {
		String data = "\"Some \\a test X.\"";
		Lexer lexer = new Lexer(data.toCharArray());
		lexer.setLexerState(LexerState.INSIDE_TAG);
		lexer.nextToken();
		//Assert.assertEquals(0, new String("Some \\ test X.").compareTo(lexer.getToken().getValue().toString()));

	}

	@org.junit.Test
	public void testEscapingLexer3() {
		String data = "\"Some \n test X.\"";
		Lexer lexer = new Lexer(data.toCharArray());
		lexer.setLexerState(LexerState.INSIDE_TAG);
		lexer.nextToken();
		//Assert.assertEquals(0, new String("Some \n test X.").compareTo(lexer.getToken().getValue().toString()));

	}

	@org.junit.Test
	public void testEscapingLexer4() {
		String data = "\"Joe \\\"Long\\\" Smith.\"";
		Lexer lexer = new Lexer(data.toCharArray());
		lexer.setLexerState(LexerState.INSIDE_TAG);
		lexer.nextToken();

		//Assert.assertEquals(0, new String("Joe \"Long\" Smith.").compareTo(lexer.getToken().getValue().toString()));

	}

	@org.junit.Test
	public void testEscapingLexer5() {
		String data = "\"Some \\\\ test X.\"";
		Lexer lexer = new Lexer(data.toCharArray());
		lexer.setLexerState(LexerState.INSIDE_TAG);
		lexer.nextToken();

		//Assert.assertEquals(0, new String("Some \\ test X.").compareTo(lexer.getToken().getValue().toString()));

	}

	@org.junit.Test
	public void testEscapingLexer6() {
		String data = "\\{$=1$}.";
		Lexer lexer = new Lexer(data.toCharArray());

		lexer.nextToken();
		System.out.println(lexer.getToken().getValue().toString());
		Assert.assertEquals(0, new String("{$=1$}.").compareTo(lexer.getToken().getValue().toString()));

	}

	@org.junit.Test
	public void testEscapingLexer7() {
		String data = "\\\\ \\{";
		Lexer lexer = new Lexer(data.toCharArray());

		lexer.nextToken();
		System.out.println(lexer.getToken().getValue().toString());
		Assert.assertEquals(0, new String("\\ {").compareTo(lexer.getToken().getValue().toString()));

	}

	@org.junit.Test(expected = LexerException.class)
	public void testEscapingLexer8() {
		String data = "\\\\ \\{ \\mirko";
		Lexer lexer = new Lexer(data.toCharArray());

		lexer.nextToken();
		System.out.println(lexer.getToken().getValue().toString());
		Assert.assertEquals(0, new String("\\ {").compareTo(lexer.getToken().getValue().toString()));

	}

	@org.junit.Test
	public void testFromExample() {
		String docBody = loader("doc1.txt");
		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch (Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = SmartScriptTester.createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody);
		System.out.println(parser.getDocumentNode().numberOfChildren());
		Assert.assertEquals(4, parser.getDocumentNode().numberOfChildren());
		Assert.assertEquals(0, parser.getDocumentNode().getChild(0).numberOfChildren());
		Assert.assertEquals(3, parser.getDocumentNode().getChild(1).numberOfChildren());
		Assert.assertEquals(0, parser.getDocumentNode().getChild(2).numberOfChildren());
		Assert.assertEquals(5, parser.getDocumentNode().getChild(3).numberOfChildren());
		Assert.assertTrue(parser.getDocumentNode().getChild(0) instanceof TextNode);
		Assert.assertTrue(parser.getDocumentNode().getChild(1) instanceof ForLoopNode);
		Assert.assertTrue(parser.getDocumentNode().getChild(2) instanceof TextNode);
		Assert.assertTrue(parser.getDocumentNode().getChild(3) instanceof ForLoopNode);

		Assert.assertTrue(parser.getDocumentNode().getChild(1).getChild(0) instanceof TextNode);
		Assert.assertTrue(parser.getDocumentNode().getChild(1).getChild(1) instanceof EchoNode);
		Assert.assertTrue(parser.getDocumentNode().getChild(1).getChild(2) instanceof TextNode);

		Assert.assertTrue(parser.getDocumentNode().getChild(3).getChild(0) instanceof TextNode);
		Assert.assertTrue(parser.getDocumentNode().getChild(3).getChild(1) instanceof EchoNode);
		Assert.assertTrue(parser.getDocumentNode().getChild(3).getChild(2) instanceof TextNode);
		Assert.assertTrue(parser.getDocumentNode().getChild(3).getChild(3) instanceof EchoNode);
		Assert.assertTrue(parser.getDocumentNode().getChild(3).getChild(4) instanceof TextNode);
		// Assert.assertEquals(3, parser.getDocumentNode().getChild(1).);

	}

	private String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename)) {
			byte[] buffer = new byte[1024];
			while (true) {
				int read = is.read(buffer);
				if (read < 1)
					break;
				bos.write(buffer, 0, read);
			}
			return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		} catch (IOException ex) {
			return null;
		}
	}
}
