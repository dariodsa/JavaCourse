package hr.fer.zemris.math;
import org.junit.Assert;
import org.junit.Test;

public class PolynomTest {
    @Test
    public void multiply1() {
	ComplexPolynomial pol1 = new ComplexPolynomial(new Complex(2, 1));
	ComplexPolynomial pol2 = new ComplexPolynomial(new Complex(4, 5));
	ComplexPolynomial ans = pol1.multiply(pol2);
	Assert.assertEquals(ans.factors.size(), 1);
	Assert.assertEquals(
		ans.factors.get(0),
		new Complex(3, 14)
		);
    }
    
    @Test
    public void multiply2() {
	ComplexPolynomial pol1 = new ComplexPolynomial(
		new Complex(1, 0),
		new Complex(-2, 1));
	ComplexPolynomial pol2 = new ComplexPolynomial(
		new Complex(1, 0),
		new Complex(-4, 5));
	ComplexPolynomial ans = pol1.multiply(pol2);
	Assert.assertEquals(ans.factors.size(), 3);
	Assert.assertEquals(
		ans.factors.get(0),
		new Complex(1, 0)
		);
	Assert.assertEquals(
		ans.factors.get(1),
		new Complex(-6, 6)
		);
	Assert.assertEquals(
		ans.factors.get(2),
		new Complex(3, -14)
		);
    }
    
    @Test
    public void multiply3() {
	ComplexPolynomial pol1 = new ComplexPolynomial(
		new Complex(1, 0),
		new Complex(1, 0));
	ComplexPolynomial pol2 = new ComplexPolynomial(
		new Complex(1, 0),
		new Complex(-1, 0));
	ComplexPolynomial pol3 = new ComplexPolynomial(
		new Complex(1, 0),
		new Complex(0, 1));
	ComplexPolynomial pol4 = new ComplexPolynomial(
		new Complex(1, 0),
		new Complex(0, -1));
	ComplexPolynomial ans = pol1.multiply(pol2).multiply(pol3).multiply(pol4);
	Assert.assertEquals(ans.factors.size(), 5);
	Assert.assertEquals(
		ans.factors.get(0),
		new Complex(1, 0)
		);
	Assert.assertEquals(
		ans.factors.get(4),
		new Complex(-1, 0)
		);
	Assert.assertEquals(
		ans.factors.get(2),
		new Complex(0, 0)
		);
    }
}
