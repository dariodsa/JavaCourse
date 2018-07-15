package hr.fer.zemris.java.hw02;

import org.junit.Assert;
import org.junit.Test;

public class ComplexNumberTest {
	private final double THRESHOLD = 1E-2;

	@Test
	public void testRealPart() {
		ComplexNumber number = new ComplexNumber(0.5, 1.123);
		if (Math.abs(number.getReal() - 0.5) > THRESHOLD)
			Assert.fail();
	}

	@Test
	public void testImaginaryPart() {
		ComplexNumber number = new ComplexNumber(0.5, 1.123);
		if (Math.abs(number.getImaginary() - 1.123) > THRESHOLD)
			Assert.fail();
	}

	@Test
	public void testMagnitude() {
		ComplexNumber number = new ComplexNumber(0.5, 1.123);
		if (Math.abs(number.getMagnitude() - Math.sqrt(0.5 * 0.5 + 1.123 * 1.123)) > THRESHOLD)
			Assert.fail();
	}

	@Test
	public void testAngle() {
		ComplexNumber number = new ComplexNumber(0.5, 1.123);
		if (Math.abs(number.getAngle() - 1.151911223) > THRESHOLD)
			Assert.fail();

		ComplexNumber number2 = new ComplexNumber(-0.5, -1.123);
		if (Math.abs(number2.getAngle() - (-1.98968 + 2 * Math.PI)) > THRESHOLD)
			Assert.fail();
	}

	@Test
	public void testParse() {
		ComplexNumber number = ComplexNumber.parse("1.12+15i");
		if (Math.abs(number.getReal() - 1.12) > THRESHOLD)
			Assert.fail();

		if (Math.abs(number.getImaginary() - 15) > THRESHOLD)
			Assert.fail();

		ComplexNumber number2 = ComplexNumber.parse("1.12i+15");
		if (Math.abs(number2.getImaginary() - 1.12) > THRESHOLD)
			Assert.fail();

		if (Math.abs(number2.getReal() - 15) > THRESHOLD)
			Assert.fail();

		ComplexNumber number3 = ComplexNumber.parse("1.12-15i");
		if (Math.abs(number3.getReal() - 1.12) > THRESHOLD)
			Assert.fail();

		if (Math.abs(number3.getImaginary() + 15) > THRESHOLD)
			Assert.fail();
		ComplexNumber number4 = ComplexNumber.parse("-1.12i-15");
		if (Math.abs(number4.getReal() + 15) > THRESHOLD)
			Assert.fail();

		if (Math.abs(number4.getImaginary() + 1.12) > THRESHOLD)
			Assert.fail();
	}

	@Test
	public void testAdd() {
		ComplexNumber number1 = new ComplexNumber(1, 2);
		ComplexNumber number2 = new ComplexNumber(3, 4);

		ComplexNumber result = number1.add(number2);
		if (Math.abs(result.getReal() - 4) > THRESHOLD)
			Assert.fail();
		if (Math.abs(result.getImaginary() - 6) > THRESHOLD)
			Assert.fail();
	}

	@Test
	public void testSub() {
		ComplexNumber number1 = new ComplexNumber(1, 2);
		ComplexNumber number2 = new ComplexNumber(3, 4);

		ComplexNumber result = number1.sub(number2);
		if (Math.abs(result.getReal() + 2) > THRESHOLD)
			Assert.fail();
		if (Math.abs(result.getImaginary() + 2) > THRESHOLD)
			Assert.fail();
	}

	@Test
	public void testMulti() {
		ComplexNumber number1 = new ComplexNumber(1, 2);
		ComplexNumber number2 = new ComplexNumber(3, 4);

		ComplexNumber result = number1.mul(number2);
		if (Math.abs(result.getReal() + 5) > THRESHOLD)
			Assert.fail();
		if (Math.abs(result.getImaginary() - 10) > THRESHOLD)
			Assert.fail();
	}

	@Test
	public void testDiv() {
		ComplexNumber number1 = new ComplexNumber(1, 2);
		ComplexNumber number2 = new ComplexNumber(3, 4);

		ComplexNumber result = number1.div(number2);
		if (Math.abs(result.getReal() - 11f / 25f) > THRESHOLD)
			Assert.fail();
		if (Math.abs(result.getImaginary() - 2f / 25) > THRESHOLD)
			Assert.fail();
	}

	@Test
	public void testFromMagnitudeAndAngle() {
		ComplexNumber number1 = ComplexNumber.fromMagnitudeAndAngle(15.53, 23.3);
		if (Math.abs(number1.getReal() + 4.0216425) > THRESHOLD)
			Assert.fail();
		if (Math.abs(number1.getImaginary() + 15.000024) > THRESHOLD)
			Assert.fail();

		ComplexNumber number2 = ComplexNumber.fromMagnitudeAndAngle(4, 14.1);
		if (Math.abs(number2.getReal() - 0.14863353) > THRESHOLD)
			Assert.fail();
		if (Math.abs(number2.getImaginary() - 3.9972375) > THRESHOLD)
			Assert.fail();
	}

	@Test
	public void testPower() {
		ComplexNumber number1 = new ComplexNumber(14.1, 13.2).power(3);

		if (Math.abs(number1.getReal() + 4567.131) > THRESHOLD)
			Assert.fail();
		if (Math.abs(number1.getImaginary() - 5572.908) > THRESHOLD)
			Assert.fail();

		ComplexNumber number2 = new ComplexNumber(-12.1, 7.2).power(2);

		if (Math.abs(number2.getReal() - 94.57) > THRESHOLD)
			Assert.fail();
		if (Math.abs(number2.getImaginary() + 174.24) > THRESHOLD)
			Assert.fail();
	}

	@Test
	public void testRoot() {
		ComplexNumber[] number1 = new ComplexNumber(16, 0).root(2);

		if (Math.abs(number1[0].getReal() - 4) > THRESHOLD)
			Assert.fail(new Double(number1[0].getReal()).toString());
		if (Math.abs(number1[0].getImaginary() - 0) > THRESHOLD)
			Assert.fail();
		if (Math.abs(number1[1].getReal() + 4) > THRESHOLD)
			Assert.fail();
		if (Math.abs(number1[1].getImaginary() - 0) > THRESHOLD)
			Assert.fail();

		ComplexNumber number2 = new ComplexNumber(-12.1, 7.2).power(2);

		if (Math.abs(number2.getReal() - 94.57) > THRESHOLD)
			Assert.fail();
		if (Math.abs(number2.getImaginary() + 174.24) > THRESHOLD)
			Assert.fail();
	}
}
