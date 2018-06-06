package hr.fer.zemris.math;
import org.junit.*;

public class Vector2DTest {
	private final double DELTA = 1e-5;
	
	@Test
	public void testInit() {
		Vector2D vector = new Vector2D(2, 3);
		Assert.assertEquals(2.0, vector.getX(), DELTA);
		Assert.assertEquals(3.0, vector.getY(), DELTA);
	}
	
	@Test
	public void testInit2() {
		Vector2D vector = new Vector2D(-12, 24);
		Assert.assertEquals(-12, vector.getX(), DELTA);
		Assert.assertEquals(24, vector.getY(), DELTA);
	}
	@Test
	public void testCopy() {
		Vector2D vector = new Vector2D(-1, 7);
		Assert.assertEquals(-1, vector.getX(), DELTA);
		Assert.assertEquals(7, vector.getY(), DELTA);
		
		Vector2D vectorCopy = vector.copy();
		Assert.assertEquals(-1, vectorCopy.getX(), DELTA);
		Assert.assertEquals(7, vectorCopy.getY(), DELTA);
		
	}
	@Test
	public void testTranslate() {
		Vector2D offset = new Vector2D(2, 3);
		Vector2D vector = new Vector2D(4, 5);
		
		vector.translate(offset);
		Assert.assertEquals(6, vector.getX(), DELTA);
		Assert.assertEquals(8, vector.getY(), DELTA);
		
		vector.translate(offset);
		Assert.assertEquals(8, vector.getX(), DELTA);
		Assert.assertEquals(11, vector.getY(), DELTA);
		
		Vector2D vector2 = vector.translated(offset);
		Assert.assertEquals(10, vector2.getX(), DELTA);
		Assert.assertEquals(14, vector2.getY(), DELTA);
	}
	@Test
	public void testRotate() {
		Vector2D vector = new Vector2D(10, 0);
		
		vector.rotate(90);
		Assert.assertEquals(0, vector.getX(), DELTA);
		Assert.assertEquals(10, vector.getY(), DELTA);
		
		vector.rotate(90);
		Assert.assertEquals(-10, vector.getX(), DELTA);
		Assert.assertEquals(0, vector.getY(), DELTA);
		
		vector.rotate(90);
		Assert.assertEquals(0, vector.getX(), DELTA);
		Assert.assertEquals(-10, vector.getY(), DELTA);
		
		vector.rotate(90);
		Assert.assertEquals(10, vector.getX(), DELTA);
		Assert.assertEquals(0, vector.getY(), DELTA);
	}
	@Test
	public void testRotated() {
		Vector2D vector = new Vector2D(10, 0);
		
		Vector2D vector2 = vector.rotated(90);
		
		Assert.assertEquals(0, vector2.getX(), DELTA);
		Assert.assertEquals(10, vector2.getY(), DELTA);
		
		vector2 = vector2.rotated(90);
		Assert.assertEquals(-10, vector2.getX(), DELTA);
		Assert.assertEquals(0, vector2.getY(), DELTA);
		
		vector2 = vector2.rotated(90);
		Assert.assertEquals(0, vector2.getX(), DELTA);
		Assert.assertEquals(-10, vector2.getY(), DELTA);
		
		vector2 = vector2.rotated(90);
		Assert.assertEquals(10, vector2.getX(), DELTA);
		Assert.assertEquals(0, vector2.getY(), DELTA);
	}
	@Test
	public void testScale() {
		Vector2D vector = new Vector2D(64, 32);
		double scale = 0.5;
		vector.scale(scale);
		Assert.assertEquals(32, vector.getX(), DELTA);
		Assert.assertEquals(16, vector.getY(), DELTA);
		
		vector.scale(scale);
		Assert.assertEquals(16, vector.getX(), DELTA);
		Assert.assertEquals(8, vector.getY(), DELTA);
		
	}
	
	@Test
	public void testScaled() {
		Vector2D vector = new Vector2D(64, 32);
		double scale = 0.5;
		Vector2D vector2 = vector.scaled(scale);
		Assert.assertEquals(32, vector2.getX(), DELTA);
		Assert.assertEquals(16, vector2.getY(), DELTA);
		
		vector2 = vector2.scaled(scale);
		Assert.assertEquals(16, vector2.getX(), DELTA);
		Assert.assertEquals(8, vector2.getY(), DELTA);
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testException() {
		Vector2D vector = new Vector2D(4, 5);
		vector.translate(null); /// 
		vector.rotate(50);
		vector.translate(null);
	}
}
