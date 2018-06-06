package hr.fer.zemris.java.hw06.demo4;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.*;

public class Demo4Test {
	
	List<StudentRecord> records ;
	@Before
	public void init() {
		try {
			List<String> lines = Files.readAllLines(Paths.get("./src/main/resources/studenti.txt"));
			records = StudentDemo.convert(lines);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void firstTaskTest() {
		long actual = StudentDemo.vratiBodovaViseOd25(records);
		long expected = 0;
		for(StudentRecord record : records) {
			if(record.getTotalPoints() > 25) {
				expected++;
			}
		}
		Assert.assertEquals(expected, actual);
	}
	@Test
	public void secondTaskTest() {
		long actual = StudentDemo.vratiBrojOdlikasa(records);
		long expected = 0;
		for(StudentRecord record : records) {
			if(record.getOcjena() == 5) {
				expected++;
			}
		}
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void thirdTaskTest() {
		List<StudentRecord> actual = StudentDemo.vratiListuOdlikasa(records);
		List<StudentRecord> expected = new ArrayList<>();
		for(StudentRecord record : records) {
			if(record.getOcjena() == 5) {
				expected.add(record);
			}
		}
		Assert.assertTrue(expected.containsAll(actual));
	}
	
	@Test
	public void fourthTaskTest() {
		List<StudentRecord> actual = StudentDemo.vratiSortiranuListuOdlikasa(records);
		List<StudentRecord> expected = new ArrayList<>();
		for(StudentRecord record : records) {
			if(record.getOcjena() == 5) {
				expected.add(record);
			}
		}
		
		Collections.sort(expected,Collections.reverseOrder(
				(x,y)->(
						Double.valueOf(x.getTotalPoints()).compareTo(y.getTotalPoints())
						)
		));
		Assert.assertEquals(expected.size(), actual.size());
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void fifthTaskTest() {
		List<String> actual = StudentDemo.vratiPopisNepolozenih(records);
		List<String> expected = new ArrayList<>();
		for(StudentRecord record : records) {
			if(record.getOcjena() == 1) {
				expected.add(record.getJmbag());
			}
		}
		
		Collections.sort(expected);
		Assert.assertEquals(expected.size(), actual.size());
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void sixthTaskTest() {
		Map<Integer, List<StudentRecord>> actual = StudentDemo.razvrstajStudentePoOcjenama(records);
		Map<Integer, List<StudentRecord>> expected = new HashMap<>();
		for(StudentRecord record : records) {
			if(expected.containsKey(record.getOcjena())) {
				expected.get(record.getOcjena()).add(record);
			} else {
				expected.put(record.getOcjena(), new ArrayList<>());
				expected.get(record.getOcjena()).add(record);
			}
		}
		Assert.assertEquals(expected.size(), actual.size());
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void seventhTaskTest() {
		Map<Integer, Integer> actual = StudentDemo.vratiBrojStudenataPoOcjenama(records);
		Map<Integer, Integer> expected = new HashMap<>();
		for(StudentRecord record : records) {
			if(expected.containsKey(record.getOcjena())) {
				expected.put(record.getOcjena(), 1 + expected.get(record.getOcjena()));
			} else {
				expected.put(record.getOcjena(), 1);
			}
		}
		Assert.assertEquals(expected.size(), actual.size());
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void eigthTaskTest() {
		Map<Boolean, List<StudentRecord>> actual = StudentDemo.razvrstajProlazPad(records);
		Map<Boolean, List<StudentRecord>> expected = new HashMap<>();
		for(StudentRecord record : records) {
			//System.out.println(record.getIfStudentPass() +" " +record.getOcjena()+ " " + (record.getOcjena()>1));
			boolean k = record.getIfStudentPass();
			if(expected.containsKey(record.getIfStudentPass())) {
				expected.get(record.getIfStudentPass()).add(record);
			} else {
				System.out.println(record.getIfStudentPass());
				expected.put(record.getIfStudentPass(), new ArrayList<>());
				expected.get(record.getIfStudentPass()).add(record);
			}
		}
		System.out.println(expected.size());
		Assert.assertEquals(expected.size(), actual.size());
		Assert.assertEquals(expected, actual);
	}
	@After
	public void clear() {
		records.clear();
	}
}
