package hr.fer.zemris.java.hw05.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import javax.print.DocFlavor.STRING;

import org.junit.*;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;

public class SimpleHashtableTest {
	
	private SimpleHashtable<String, Integer> examMarks;
	@Before
	public void init() {
		examMarks = new SimpleHashtable<>(2);
	}
	
	@Test 
	public void testPut() {
		Assert.assertEquals(0, examMarks.size());
		examMarks.put("Ivana", 2);
		Assert.assertEquals(1, examMarks.size());
		examMarks.put("Ante", 2);
		Assert.assertEquals(2, examMarks.size());
		examMarks.put("Jasna", 2);
		Assert.assertEquals(3, examMarks.size());
	}
	@Test
	public void testPutOverwrite() {
		Assert.assertEquals(0, examMarks.size());
		examMarks.put("Ivana", 2);
		Assert.assertEquals(1, examMarks.size());
		examMarks.put("Ante", 2);
		Assert.assertEquals(2, examMarks.size());
		examMarks.put("Jasna", 2);
		Assert.assertEquals(3, examMarks.size());
		examMarks.put("Kristina", 5);
		Assert.assertEquals(4, examMarks.size());
		examMarks.put("Ivana", 5);
		Assert.assertEquals(4, examMarks.size());
	}
	@Test
	public void testBigTestExample() {
		for(int i=0;i<1000;++i) {
			examMarks.put(String.valueOf(i), i);
		}
		//inside
		for(int i=0;i<1000;++i) {
			Assert.assertFalse(!examMarks.containsKey(String.valueOf(i)));
		}
		//not inside
		for(int i=1500;i<2000;++i) {
			Assert.assertFalse(examMarks.containsKey(String.valueOf(i)));
		}
	}
	@Test
	public void testGet() {
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); 
	
		Assert.assertEquals(Integer.valueOf(5), (Integer)examMarks.get("Kristina"));
		Assert.assertEquals(Integer.valueOf(2), (Integer)examMarks.get("Ante"));
		Assert.assertEquals(Integer.valueOf(2), (Integer)examMarks.get("Jasna"));
		Assert.assertEquals(Integer.valueOf(5), (Integer)examMarks.get("Ivana"));
	}
	@Test
	public void testContains() {
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		examMarks.put("Iva", null);
		
		Assert.assertEquals(false, examMarks.containsKey("Dario"));
		Assert.assertEquals(false, examMarks.containsKey(null));
		
		Assert.assertEquals(true, examMarks.containsValue(null));
		
		Assert.assertEquals(true, examMarks.containsValue(5));
		
		Assert.assertEquals(true, examMarks.containsValue(2));
		
		Assert.assertEquals(true, examMarks.containsKey("Jasna"));
		
	}
	@Test
	public void testIterator() {
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		examMarks.put("Iva", null);
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
			if(pair.getKey().equals("Ivana")) {
				iter.remove(); // sam iterator kontrolirano uklanja trenutni element
			}
		}
		Assert.assertEquals(4, examMarks.size());
	}
	@Test
	public void testIterator2() {
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		examMarks.put("Iva", null);
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
			iter.remove(); // sam iterator kontrolirano uklanja trenutni element
			
		}
		Assert.assertEquals(0, examMarks.size());
	}
	@Test (expected = IllegalStateException.class)
	public void testIteratorIllegal() {
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		examMarks.put("Iva", null);
		
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		while(iter.hasNext()) {
			
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
			if(pair.getKey().equals("Ivana")) {
				iter.remove();
				iter.remove();
			}
		}
	}
	
	@Test (expected = ConcurrentModificationException.class)
	public void testIteratorConcurrent() {
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		examMarks.put("Iva", null);
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
			if(pair.getKey().equals("Ivana")) {
				examMarks.remove("Ivana");
			}
		}
	}
}
