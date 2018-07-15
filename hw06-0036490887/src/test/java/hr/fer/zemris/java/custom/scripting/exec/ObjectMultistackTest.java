package hr.fer.zemris.java.custom.scripting.exec;
import org.junit.*;

public class ObjectMultistackTest {
	
	private ObjectMultistack multiStack;
	
	@Before
	public void init() {
		multiStack = new ObjectMultistack();
	}
	
	@Test
	public void testIsEmpty() {
		multiStack.push("darko", new ValueWrapper(5));
		Assert.assertTrue(multiStack.isEmpty("mirko"));
		Assert.assertFalse(multiStack.isEmpty("darko"));
	}
	
	@Test
	public void testPushAndPeek() {
		multiStack.push("darko", new ValueWrapper(5));
		multiStack.push("darko", new ValueWrapper(7));
		Assert.assertEquals(7,multiStack.peek("darko").getValue());
	}
	@Test
	public void testPushAndPeekAndPop() {
		multiStack.push("darko", new ValueWrapper(5));
		multiStack.push("darko", new ValueWrapper(7));
		multiStack.push("darko", new ValueWrapper(9));
		Assert.assertEquals(9,multiStack.peek("darko").getValue());
		Assert.assertEquals(9,multiStack.pop("darko").getValue());
		Assert.assertEquals(7,multiStack.peek("darko").getValue());
		Assert.assertEquals(7,multiStack.pop("darko").getValue());
		Assert.assertEquals(5,multiStack.peek("darko").getValue());
		Assert.assertEquals(5,multiStack.pop("darko").getValue());
		Assert.assertTrue(multiStack.isEmpty("darko"));
	}
	
	@Test
	public void twoStacks() {
		multiStack.push("darko", new ValueWrapper(5));
		multiStack.push("darko", new ValueWrapper(7));
		multiStack.push("mirko", new ValueWrapper(9));
		multiStack.push("mirko", new ValueWrapper(11));
		
		Assert.assertEquals(11,multiStack.peek("mirko").getValue());
		Assert.assertEquals(7,multiStack.peek("darko").getValue());
		
		multiStack.pop("mirko");
		multiStack.pop("darko");
		
		Assert.assertEquals(9,multiStack.peek("mirko").getValue());
		Assert.assertEquals(5,multiStack.peek("darko").getValue());
	
		multiStack.pop("mirko");
		multiStack.pop("darko");
		
		Assert.assertTrue(multiStack.isEmpty("mirko"));
		Assert.assertTrue(multiStack.isEmpty("darko"));
	}
	
	@Test
	public void testAddingTwoIntegers() {
		multiStack.push("darko", new ValueWrapper(5));
		multiStack.peek("darko").add(7);
		Assert.assertFalse(multiStack.isEmpty("darko"));
		Assert.assertEquals(5+7,multiStack.peek("darko").getValue());
	}
	
	@Test
	public void testSubtractTwoIntegers() {
		multiStack.push("darko", new ValueWrapper(5));
		multiStack.peek("darko").subtract(7);
		Assert.assertFalse(multiStack.isEmpty("darko"));
		Assert.assertEquals(5-7,multiStack.peek("darko").getValue());
	}
	@Test
	public void testMultiplyTwoIntegers() {
		multiStack.push("darko", new ValueWrapper(5));
		multiStack.peek("darko").multiply(7);
		Assert.assertFalse(multiStack.isEmpty("darko"));
		Assert.assertEquals(5*7,multiStack.peek("darko").getValue());
	}
	@Test
	public void testDivideTwoIntegers() {
		multiStack.push("darko", new ValueWrapper(5));
		multiStack.peek("darko").divide(7);
		Assert.assertFalse(multiStack.isEmpty("darko"));
		Assert.assertEquals(5/7,multiStack.peek("darko").getValue());
	}
	
	@Test
	public void testAddingTwoNulls() {
		multiStack.push("darko", new ValueWrapper(5));
		multiStack.peek("darko").add(7);
		Assert.assertFalse(multiStack.isEmpty("darko"));
		Assert.assertEquals(5+7,multiStack.peek("darko").getValue());
	}
	@Test
	public void complexPostfixTest() {
		multiStack.push("darko", new ValueWrapper(5));
		multiStack.push("darko", new ValueWrapper(6));
		multiStack.push("darko", new ValueWrapper(7));
		multiStack.push("darko", new ValueWrapper(8));
		
		Assert.assertEquals(8,multiStack.peek("darko").getValue());
		Object firstValue = multiStack.pop("darko").getValue();
		multiStack.peek("darko").multiply(firstValue);
		
		Assert.assertEquals(8*7,multiStack.peek("darko").getValue());
		Object secondValue = multiStack.pop("darko").getValue();
		multiStack.peek("darko").add(secondValue);
		Object thirdValue = multiStack.pop("darko").getValue();
		multiStack.peek("darko").add(thirdValue);
		
		Assert.assertEquals(5+(6+7*8),multiStack.pop("darko").getValue());
	}
}
