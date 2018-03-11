package hr.fer.zemris.java.hw01;

import org.junit.Test;

import org.junit.Assert;

public class FactorialTest {
	@Test
	public void testFactorialForNumber1() {
		long expected = 1;
		long actual = Factorial.factorial(1);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testFactorialForNumber5() {
		long expected = 1 * 2 * 3 * 4 * 5;
		long actual = Factorial.factorial(5);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testFactorialForNumber20() {
		long expected = 1l * 2l * 3l * 4l * 5l * 6l * 7l * 8l * 9l * 10l * 11l * 12l * 13l * 14l * 15l * 16l * 17l * 18l * 19l * 20l;
		long actual = Factorial.factorial(20);
		Assert.assertEquals(expected, actual);
	}
	@Test
	public void testFactorialForNumberMinus1() {
	    	try {
	    	    Factorial.factorial(-1);
	    	    Assert.fail("You shouldn't be able to now (-1)!.");
	    	} catch(IllegalArgumentException exception) {
	    	    
	    	}
	}
	@Test
	public void testFactorialForNumberMinus7() {
	    	try {
	    	    Factorial.factorial(-7);
	    	    Assert.fail("You shouldn't be able to now (-7)!.");
	    	} catch(IllegalArgumentException exception) {
	    	    
	    	}
	}
}

