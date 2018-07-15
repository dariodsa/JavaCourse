package hr.fer.zemris.java.custom.scripting.exec;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JSlider;

import org.junit.*;

public class ValueWrapperTest {
	@Test
	public void testNullToInteger() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue());
		Assert.assertEquals(Integer.valueOf(0),v1.getValue());
	}
	
	@Test
	public void testIntegerAndDoubleSummation() {
		ValueWrapper v3 = new ValueWrapper("1.2E1");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));
		v3.add(v4.getValue());
		Assert.assertEquals(Double.valueOf(13),v3.getValue());
		Assert.assertEquals(Integer.valueOf(1),v4.getValue());
	}
	
	@Test
	public void testIntegerAndIntegerSummation() {
		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
		v5.add(v6.getValue());
		Assert.assertEquals(Integer.valueOf(13),v5.getValue());
		Assert.assertEquals(Integer.valueOf(1),v6.getValue());
	}
	
	@Test (expected = RuntimeException.class)
	public void testNumberFormatErrorParsing() {
		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
		v7.add(v8.getValue());
	}
	
	@Test (expected = RuntimeException.class)
	public void testAddingGUIandSocket() {
		ValueWrapper v7 = new ValueWrapper(new JFrame());
		ValueWrapper v8 = new ValueWrapper(new Socket());
		v7.add(v8.getValue());
	}
	
	@Test
	public void testCompareTwoNulls() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		Assert.assertEquals(0,v1.numCompare(v2.getValue()));
	}
	
	@Test
	public void testCompareTwoEqualIntegers() {
		ValueWrapper v1 = new ValueWrapper(1);
		ValueWrapper v2 = new ValueWrapper(1);
		Assert.assertEquals(0,v1.numCompare(v2.getValue()));
	}
	
	@Test
	public void testCompareTwoIntegersLess() {
		ValueWrapper v1 = new ValueWrapper(1);
		ValueWrapper v2 = new ValueWrapper(5);
		Assert.assertTrue(v1.numCompare(v2.getValue()) < 0);
	}
	
	@Test
	public void testCompareTwoIntegersGreater() {
		ValueWrapper v1 = new ValueWrapper(10);
		ValueWrapper v2 = new ValueWrapper(5);
		Assert.assertTrue(v1.numCompare(v2.getValue()) > 0);
	}
	
	@Test
	public void testCompareTwoDoublesGreater() {
		ValueWrapper v1 = new ValueWrapper(10.56);
		ValueWrapper v2 = new ValueWrapper(5.78);
		Assert.assertTrue(v1.numCompare(v2.getValue()) > 0);
	}
	@Test
	public void testCompareTwoDoublesLess() {
		ValueWrapper v1 = new ValueWrapper(11.12);
		ValueWrapper v2 = new ValueWrapper(556.56);
		Assert.assertTrue(v1.numCompare(v2.getValue()) < 0);
	}
	@Test
	public void testIntegerAndDouble() {
		ValueWrapper v1 = new ValueWrapper(11);
		ValueWrapper v2 = new ValueWrapper(556.56);
		Assert.assertTrue(v1.numCompare(v2.getValue()) < 0);
	}
	@Test (expected = RuntimeException.class)
	public void testComparisonRuntimeException() {
		ValueWrapper v1 = new ValueWrapper(new JSlider());
		ValueWrapper v2 = new ValueWrapper(556.56);
		Assert.assertTrue(v1.numCompare(v2.getValue()) < 0);
	}
	@Test
	public void testIntegerAndDoubleEqual() {
		ValueWrapper v1 = new ValueWrapper(11);
		ValueWrapper v2 = new ValueWrapper(11.000);
		Assert.assertTrue(v1.numCompare(v2.getValue()) == 0);
	}
	@Test
	public void testIntegerAndNullEqual() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(0);
		Assert.assertTrue(v1.numCompare(v2.getValue()) == 0);
	}
	@Test
	public void testCompareBothNull() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		Assert.assertTrue(v1.numCompare(v2.getValue()) == 0);
	}
}
