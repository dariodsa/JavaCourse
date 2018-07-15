package hr.fer.zemris.java.hw07;
import org.junit.*;

public class UtilTest {
	
	/*
	 * hexToByte testing methods
	 */
	@Test
	public void testHexToByteExample1() {
		byte[] result = Util.hextobyte("01aE22");
		Assert.assertArrayEquals(new byte[] {1, -82, 34}, result); 
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testHexToByteNullException() {
		Util.hextobyte(null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testHexToByteOddLength() {
		Util.hextobyte("012");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testHexToByteNonHEXChars() {
		Util.hextobyte("012s");
	}
	
	@Test
	public void testHexToByteLowerCaseTest() {
		byte[] result1 = Util.hextobyte("01aE22");
		byte[] result2 = Util.hextobyte("01Ae22");
		Assert.assertArrayEquals(result2, result1);
		
		byte[] result3 = Util.hextobyte("01bf22");
		byte[] result4 = Util.hextobyte("01BF22");
		Assert.assertArrayEquals(result4, result3);
	}
	
	@Test
	public void testHexToByteZeroLengthArray() {
		byte[] result1 = Util.hextobyte("");
		Assert.assertEquals(0, result1.length);
	}
	
	/*
	 * byteToHex testing methods 
	 */
	
	@Test
	public void testByteToHexExample1() {
		String hex = Util.bytetohex(new byte[] {1, -82, 34});
		Assert.assertEquals("01ae22" ,hex); 
	}
	
	@Test
	public void testByteToHexZeroLength() {
		String hex = Util.bytetohex(new byte[] {});
		Assert.assertEquals("" ,hex); 
	}
	
}
