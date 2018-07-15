package hr.fer.zemris.java.hw05.db;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.*;

import hr.fer.zemris.java.hw05.db.QueryFilter;
import hr.fer.zemris.java.hw05.db.QueryParser;
import hr.fer.zemris.java.hw05.db.StudentDatabase;
import hr.fer.zemris.java.hw05.db.exceptions.LikeOperatorException;
import hr.fer.zemris.java.hw05.db.exceptions.ParserException;
import hr.fer.zemris.java.hw05.db.exceptions.UnknownVariableName;

public class QueryParserTest {

	@Test 
	public void testExample1() {
		QueryParser parser = new QueryParser("jmbag = \"0036\"");
		Assert.assertTrue(parser.isDirectQuery());
		Assert.assertEquals("0036", parser.getQueriedJMBAG());
	}
	
	@Test 
	public void testNonDirect() {
		QueryParser parser = new QueryParser("firstName = \"0036\"");
		Assert.assertFalse(parser.isDirectQuery());
	}
	
	@Test (expected = IllegalStateException.class)
	public void testNonDirectException() {
		QueryParser parser = new QueryParser("firstName = \"0036\"");
		Assert.assertFalse(parser.isDirectQuery());
		Assert.assertEquals("0036", parser.getQueriedJMBAG());
	}
	
	@Test (expected = ParserException.class)
	public void testExceptionAndBetween() {
		new QueryParser("lastName AND LIKE \"B*\"");
	}
	
	@Test (expected = ParserException.class)
	public void testExceptionTextWithoutQuo() {
		new QueryParser("lastName LIKE B*");
	}
	
	@Test (expected = ParserException.class)
	public void testExceptionVariableWithQuo() {
		new QueryParser("\"lastName\" LIKE \"B*\"");
	}
	
	@Test (expected = ParserException.class)
	public void testExceptionAndAgain() {
		new QueryParser("\"lastName\" LIKE  ANd \"B*\" ANd");
	}
	
	@Test (expected = ParserException.class)
	public void testExceptionAndAtEnd() {
		new QueryParser("lastName LIKE \"B*\" ANd And");
	}
	
	
	@Test (expected = UnknownVariableName.class)
	public void testUnknownVariableName() {
		new QueryParser("prezime LIKE \"B*\"");
	}
	
	@Test (expected = LikeOperatorException.class)
	public void testLikeException() {
		StudentDatabase db = new StudentDatabase(getLines());
		QueryParser query = new QueryParser("lastName LIKE \"B*asa*sa\"");
		db.filter(new QueryFilter(query.getQuery()));
		
	}
	
	@Test (expected = LikeOperatorException.class)
	public void testLikeException2() {
		StudentDatabase db = new StudentDatabase(getLines());
		QueryParser query = new QueryParser("lastName LIKE \"B*as**\"");
		db.filter(new QueryFilter(query.getQuery()));
	}
	
	private String[] getLines() {
		String[] lines = null;
		try {
			lines = Files.readAllLines(
					Paths.get("./database.txt"),
					StandardCharsets.UTF_8
					).toArray(new String[0]);
		} catch (IOException e) {e.printStackTrace();}
		return lines;
	}
}
