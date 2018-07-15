package hr.fer.zemris.java.custom.collections;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ArrayIndexedCollectionTest {
	
	private static ArrayIndexedCollection collection;
	@BeforeClass
	public static void init() {
		collection = new ArrayIndexedCollection();
	}
	@Test
	public void testAdd() {
		
		collection.add(Integer.valueOf(5));
		collection.add(Integer.valueOf(7));
		Assert.assertEquals(2, collection.size());
		Assert.assertEquals(Integer.valueOf(5), collection.get(0));
		Assert.assertEquals(Integer.valueOf(7), collection.get(1));
	}
	@Test
	public void testGet() {
		for (int i = 0; i < 10; ++i) {
			collection.add(Integer.valueOf(i));
		}
		for(int i=0;i<10;++i) {
			Assert.assertEquals(collection.get(i), Integer.valueOf(i));
		}
	}
	@Test
	public void testClear() {
		collection.add(Integer.valueOf(5));
		collection.add(Integer.valueOf(7));
		
		collection.clear();
		Assert.assertEquals(0, collection.size());
		try {
			collection.get(5);
			Assert.fail();
		} catch(IllegalArgumentException ex) {}
	}
	@Test
	public void testInsert() {
		collection.insert(Integer.valueOf(3), 0);
		collection.insert(Integer.valueOf(9), 1);
		Assert.assertEquals(2, collection.size());
		Assert.assertEquals(Integer.valueOf(3), collection.get(0));
		Assert.assertEquals(Integer.valueOf(9), collection.get(1));
	}
	@Test(expected = IllegalArgumentException.class)
	public void testInsertFail() {
		collection.insert(Integer.valueOf(3), 1);
		collection.insert(Integer.valueOf(9), 2);
		
	}
	@Test
	public void testIndexOf() {
		collection.insert(Integer.valueOf(3), 0);
		collection.insert(Integer.valueOf(9), 1);
		Assert.assertEquals(collection.indexOf(Integer.valueOf(9)), 1);
		Assert.assertEquals(collection.indexOf(Integer.valueOf(3)), 0);
	}
	@Test
	public void testRemove() {
		collection.add(Integer.valueOf(1));
		collection.add(Integer.valueOf(2));
		collection.add(Integer.valueOf(5));
		collection.add(Integer.valueOf(7));
		Assert.assertEquals(collection.size(), 4);
		collection.remove(3);
		Assert.assertEquals(collection.size(), 3);
		Assert.assertEquals(collection.indexOf(Integer.valueOf(7)), -1);
		
	}
	@Test
	public void testRemoveObject() {
		collection.add(Integer.valueOf(4));
		collection.add(Integer.valueOf(5));
		collection.add(Integer.valueOf(6));
		collection.add(Integer.valueOf(7));
		collection.add(Integer.valueOf(8));
		Assert.assertEquals(5, collection.size());
		Assert.assertEquals(true, collection.remove(Integer.valueOf(5)));
		Assert.assertEquals(false, collection.remove(Integer.valueOf(3)));
	}
	@Test(expected = NullPointerException.class)
	public void testConstructorNullCollection() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(null);
	}
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNegativeCapacity() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(-5);
	}
	@After
	public void reinit() {
		collection.clear();
	}
}
