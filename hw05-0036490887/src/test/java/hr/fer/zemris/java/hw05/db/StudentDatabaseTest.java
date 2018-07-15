package hr.fer.zemris.java.hw05.db;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.*;

import hr.fer.zemris.java.hw05.db.QueryFilter;
import hr.fer.zemris.java.hw05.db.QueryParser;
import hr.fer.zemris.java.hw05.db.StudentDatabase;
import hr.fer.zemris.java.hw05.db.StudentRecord;

public class StudentDatabaseTest {
	@Test
	public void testForJMBAG() throws IOException {
		String[] lines = Files.readAllLines(
				Paths.get("./database.txt"),
				StandardCharsets.UTF_8
				).toArray(new String[0]);
		StudentDatabase db = new StudentDatabase(lines);
		Assert.assertEquals("0000000019", db.forJMBAG("0000000019").getJmbag());
	}
	
	@Test
	public void testFilterAll() throws IOException {
		String[] lines = Files.readAllLines(
				Paths.get("./database.txt"),
				StandardCharsets.UTF_8
				).toArray(new String[0]);
		StudentDatabase db = new StudentDatabase(lines);
		List<StudentRecord> list = db.filter((s)->true);
		Assert.assertEquals(lines.length, list.size());
	}
	
	@Test
	public void testFilterNone() throws IOException {
		String[] lines = Files.readAllLines(
				Paths.get("./database.txt"),
				StandardCharsets.UTF_8
				).toArray(new String[0]);
		StudentDatabase db = new StudentDatabase(lines);
		List<StudentRecord> list = db.filter((s)->false);
		Assert.assertEquals(0, list.size());
	}
	
	@Test
	public void testExampleFromText() throws IOException {
		String[] lines = Files.readAllLines(
				Paths.get("./database.txt"),
				StandardCharsets.UTF_8
				).toArray(new String[0]);
		StudentDatabase db = new StudentDatabase(lines);
		List<StudentRecord> list = db.filter(new QueryFilter(new QueryParser("lastName LIKE \"B*\"").getQuery()));
		Assert.assertEquals(4, list.size());
	}
	
}