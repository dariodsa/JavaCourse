package hr.fer.zemirs.java.hw06.observer1;
import org.junit.*;

import hr.fer.zemris.java.hw06.observer1.ChangeCounter;
import hr.fer.zemris.java.hw06.observer1.SquareValue;
import hr.fer.zemris.java.hw06.observer1.DoubleValue;
import hr.fer.zemris.java.hw06.observer1.IntegerStorage;

public class IntegerStorageTest {
	
	private IntegerStorage storage;
	@Before 
	public void init() {
		storage = new IntegerStorage(50);
		
	}
	@Test
	public void basicTestAddingObservers() {
		storage.addObserver(new DoubleValue(5));
		storage.addObserver(new ChangeCounter());
		storage.addObserver(new SquareValue());
		
	}
	@Test
	public void addingTwoSameObservers() {
		DoubleValue val = new DoubleValue(5);
		storage.addObserver(val);
		storage.addObserver(val);
		Assert.assertEquals(1, storage.getObservers().size());
	}
	@Test
	public void doubleValueAfterNmodifications() {
		DoubleValue val = new DoubleValue(5);
		storage.addObserver(val);
		storage.setValue(4);
		Assert.assertEquals(1, storage.getObservers().size());
		storage.setValue(5);
		Assert.assertEquals(1, storage.getObservers().size());
		storage.setValue(6);
		Assert.assertEquals(1, storage.getObservers().size());
		storage.setValue(7);
		Assert.assertEquals(1, storage.getObservers().size());
		storage.setValue(8);
		Assert.assertEquals(1, storage.getObservers().size());
		storage.setValue(9);
		Assert.assertEquals(0, storage.getObservers().size());
	}
	@Test
	public void concurrentTest() {
		storage.addObserver(new ChangeCounter());
		DoubleValue val = new DoubleValue(5);
		storage.addObserver(val);
		storage.addObserver(new ChangeCounter());
		storage.setValue(4);
		Assert.assertEquals(3, storage.getObservers().size());
		storage.setValue(5);
		Assert.assertEquals(3, storage.getObservers().size());
		storage.setValue(6);
		Assert.assertEquals(3, storage.getObservers().size());
		storage.setValue(7);
		Assert.assertEquals(3, storage.getObservers().size());
		storage.setValue(8);
		Assert.assertEquals(3, storage.getObservers().size());
		storage.setValue(9);
		Assert.assertEquals(2, storage.getObservers().size());
	}
	
	@Test (expected = NullPointerException.class)
	public void testAddException() {
		storage.addObserver(null);
	}
	
	@Test (expected = NullPointerException.class)
	public void testRemoveException() {
		storage.removeObserver(null);
	}
	
}
