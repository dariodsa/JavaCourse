package hr.fer.zemris.math;
import org.junit.Assert;
import org.junit.Test;

public class Vector3Test {
    
    private final double DELTA = 1E-3;
    @Test
    public void normTest() {
	Assert.assertEquals(
		Math.sqrt(2*2 + 3*3 + 4*4),
		new Vector3(2, 3, 4).norm(),
		DELTA);
    }
    @Test
    public void dotTest() {
	Assert.assertEquals(
		(2*2 + 3*6 + 4*4),
		new Vector3(2, 3, 4).dot(new Vector3(2, 6, 4)),
		DELTA);
    }
    @Test
    public void getXTest() {
	Assert.assertEquals(
		3,
		new Vector3(3, 5, 4).getX(),
		DELTA);
    }
    @Test
    public void getYTest() {
	Assert.assertEquals(
		5,
		new Vector3(3, 5, 4).getY(),
		DELTA);
    }
    @Test
    public void getZTest() {
	Assert.assertEquals(
		4,
		new Vector3(3, 5, 4).getZ(),
		DELTA);
    }
    @Test
    public void toArrayTest() {
	Assert.assertArrayEquals(
		new double[] {3,5,4},
		new Vector3(3, 5, 4).toArray(),
		DELTA);
    }
    @Test
    public void addTest() {
	Assert.assertEquals(
		new Vector3(2+2 , 3+6 , 4+4),
		new Vector3(2, 3, 4).add(new Vector3(2, 6, 4))
		);
    }
    @Test
    public void subTest() {
	Assert.assertEquals(
		new Vector3(2-2 , 3-6 , 4-4),
		new Vector3(2, 3, 4).sub(new Vector3(2, 6, 4))
		);
    }
    @Test
    public void angleTest() {
	Assert.assertEquals(
		0,
		new Vector3(1, 0, 0).cosAngle(new Vector3(0, 1, 0)),
		DELTA
		);
    }
    @Test
    public void crossTest() {
	Assert.assertEquals(
		new Vector3(0, 0, 1),
		new Vector3(1, 0, 0).cross(new Vector3(0, 1, 0))
		
		);
    }
    @Test
    public void scaleTest() {
	Assert.assertEquals(
		new Vector3(2*2 , 3*2 , 4*2),
		new Vector3(2, 3, 4).scale(2)
		);
    }
    @Test
    public void scaleTest2() {
	Assert.assertEquals(
		new Vector3(2*-2 , 3*-2 , 4*-2),
		new Vector3(2, 3, 4).scale(-2)
		);
    }
    @Test
    public void normalizeTest() {
	Assert.assertEquals(
		new Vector3(1 , 0 , 0),
		new Vector3(56, 0, 0).normalized()
		);
    }
    @Test
    public void normalizeTest2() {
	double dis = Math.sqrt(56 * 56 * 3);
	Assert.assertEquals(
		new Vector3(56.0 / dis , 56.0 / dis , 56.0 / dis),
		new Vector3(56, 56, 56).normalized()
		);
    }
}
