package hr.fer.zemris.java.custom.collections;
import org.junit.*;

public class DictornaryTest {
	private Dictionary dictonary;
	
	@Before
	public void init() {
		dictonary = new Dictionary();
	}
	
	@Test
	public void testPut() {
		Assert.assertEquals(0, dictonary.size());
		Assert.assertEquals(true, dictonary.isEmpty());
		
		dictonary.put("Mirko", 54);
		Assert.assertEquals(1, dictonary.size());
		dictonary.put("Maja", 34);
		Assert.assertEquals(2, dictonary.size());
		dictonary.put("Dario", 48);
		Assert.assertEquals(3, dictonary.size());
		dictonary.put("Veronika", 36);
		Assert.assertEquals(4, dictonary.size());
		dictonary.put("Veronika", 41);
		Assert.assertEquals(4, dictonary.size());
		dictonary.put("Veronika", 30);
		Assert.assertEquals(4, dictonary.size());
		dictonary.put("Tena", 45);
		Assert.assertEquals(5, dictonary.size());
	
		Assert.assertEquals(false, dictonary.isEmpty());
	}
	
	@Test
	public void testGet() {
		Assert.assertEquals(true, dictonary.isEmpty());
		
		dictonary.put("Mirko", 54);
		dictonary.put("Darko", 55);
		dictonary.put("Dario", 56);
		dictonary.put("Veronika", 57);
		Assert.assertEquals(54, dictonary.get("Mirko"));
		Assert.assertEquals(55, dictonary.get("Darko"));
		Assert.assertEquals(56, dictonary.get("Dario"));
		Assert.assertEquals(57, dictonary.get("Veronika"));
		Assert.assertEquals(null, dictonary.get("Luna"));
		
		Assert.assertEquals(false, dictonary.isEmpty());
		
		dictonary.put("Veronika", 12);
		Assert.assertEquals(12, dictonary.get("Veronika"));
		
		dictonary.clear();
		Assert.assertEquals(true, dictonary.isEmpty());
		
	}
}
