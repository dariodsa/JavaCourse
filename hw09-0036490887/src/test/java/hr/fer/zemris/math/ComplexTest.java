package hr.fer.zemris.math;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class ComplexTest {
    
    private final double DELTA = 1E-3;
    @Test
    public void addTest() {
	Assert.assertEquals(
		new Complex(2, 3),
		new Complex(1, 1).add(new Complex(1, 2))
		);
    }
    @Test
    public void mulTest() { // (3i+1)(5+2i) 
	Assert.assertEquals(
		new Complex(-1, 17),
		new Complex(1, 3).multiply(new Complex(5, 2))
		);
    }
    @Test
    public void divTest() { // (3i+1)(5+2i) 
	Assert.assertEquals(
		new Complex(0.37931, 0.448275),
		new Complex(1, 3).divide(new Complex(5, 2))
		);
    }
    
    @Test
    public void subTest() {
	Assert.assertEquals(
		new Complex(0, -1),
		new Complex(1, 1).sub(new Complex(1, 2))
		);
    }
    
    @Test
    public void moduleTest() {
	Assert.assertEquals(
		Math.sqrt( 4*4 + 5*5),
		new Complex(4, -5).module(),
		DELTA
		);
    }
    @Test
    public void powerTest() {
	Assert.assertEquals(
		new Complex(-26, -18),
		new Complex(1, 3).power(3)
		);
    }
    @Test
    public void rootTest() {
	List<Complex> list = new ArrayList<>();
	list.add(new Complex(2, 0));
	list.add(new Complex(-2, 0));
	Assert.assertEquals(
		list,
		new Complex(4, 0).root(2)
		);
    }
    @Test
    public void rootTest2() {
	List<Complex> list = new ArrayList<>();
	list.add(new Complex(4, 0));
	list.add(new Complex(-4, 0));
	Assert.assertEquals(
		list,
		new Complex(16, 0).root(2)
		);
    }
    @Test
    public void negateTest() {
	Assert.assertEquals(
		new Complex(-1, -3),
		new Complex(1, 3).negate()
		);
    }
    @Test
    public void negateTest2() {
	Assert.assertEquals(
		new Complex(-5, 3),
		new Complex(5, -3).negate()
		);
    }
    @Test
    public void moduleTest2() {
	Assert.assertEquals(
		Math.sqrt( 4*4 + 5*5),
		new Complex(4, 5).module(),
		DELTA
		);
    }
}
