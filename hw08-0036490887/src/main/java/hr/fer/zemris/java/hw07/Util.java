package hr.fer.zemris.java.hw07;

import hr.fer.zemris.java.hw07.crypto.Crypto;

/**
 * Helper class with two main static methods {@link #hextobyte(String) hextobyte} 
 * and {@link #bytetohex(byte[]) bytetohex}. They are used in {@link Crypto} for digesting, 
 * encrypting and decrypting.  
 * 
 * @author dario
 *
 */
public class Util {

	/**
	 * Methods 
	 * @param keyText
	 * @return
	 */
	public static byte[] hextobyte(String keyText) {

		if (keyText == null)
			throw new IllegalArgumentException("Keytext can't be null.");
		if (keyText.length() % 2 == 1) {
			throw new IllegalArgumentException("Keytext should have even size.");
		}
		byte[] answer = new byte[keyText.length() / 2];
		for (int i = 0, len = keyText.length(); i < len; i += 2) {
			byte left = charValue(keyText.charAt(i));
			byte right = charValue(keyText.charAt(i + 1));

			byte ans = (byte) (left * 16 + right);
			answer[i >> 1] = ans;
		}
		return answer;
	}

	/**
	 * Returns byte value according to the given char value. 
	 * Char value is transformed to byte using hex-coding rules.
	 * @param ch char which will be turned into byte
	 */
	private static byte charValue(char ch) {
		if (!isValid(ch)) {
			throw new IllegalArgumentException("Non valid char in keyText: " + ch);
		}

		if (Character.isDigit(ch)) {
			return (byte) (ch - '0');
		} else {
			return (byte) ((Character.toLowerCase(ch)) - 'a' + 10);
		}
	}

	/**
	 * Returns true if the given character is number or letter from a to f, lower
	 * case or upper case doesn't matter.
	 * 
	 * @param character
	 *            char which will be tested
	 * @return true if the above condition is satisfied
	 */
	private static boolean isValid(char character) {
		return Character.isDigit(character)
				|| (Character.toLowerCase(character) >= 'a' && Character.toLowerCase(character) <= 'f');
	}
	/**
	 * Returns String representation of the byte array using big-endian annotation
	 * and hex coding rules.  
	 * @param array byte array
	 * @return hex value of the byte array
	 */
	public static String bytetohex(byte[] array) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < array.length; ++i) {
			int val;
			if(array[i] < 0) {
				val = ((int)array[i]) + 256;
			} else {
				val = (int)array[i];
			}
			
			builder.append(getChar((val/16)%16));
			builder.append(getChar(val%16));
		}
		return builder.toString();
	}
	/**
	 * Returns char for the specific integer value using hex base.
	 * It will return lower case for letters from a to f.
	 * @param val integer value
	 * @return character representation in hex base
	 */
	private static char getChar(int val) {
		if(val>=0 && val <= 9) {
			return (char) (val + '0');
		}
		return (char) (val - 10 + 'a');
	}
}
