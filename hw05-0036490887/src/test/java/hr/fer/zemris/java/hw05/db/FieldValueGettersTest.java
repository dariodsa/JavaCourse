package hr.fer.zemris.java.hw05.db;
import org.junit.*;

import hr.fer.zemris.java.hw05.db.FieldValueGetters;
import hr.fer.zemris.java.hw05.db.StudentRecord;

public class FieldValueGettersTest {
	private StudentRecord record;
	@Before
	public void init() {
		 this.record = new StudentRecord("0036490887", "Sindicic", "Dario", "non");
	}
	@Test
	public void testJmbag() {
		Assert.assertEquals(FieldValueGetters.JMBAG.get(record), "0036490887");
	}
	@Test
	public void testFirstName() {
		Assert.assertEquals(FieldValueGetters.FIRST_NAME.get(record), "Dario");
	}
	@Test
	public void testLastName() {
		Assert.assertEquals(FieldValueGetters.LAST_NAME.get(record), "Sindicic");
	}
	
}
