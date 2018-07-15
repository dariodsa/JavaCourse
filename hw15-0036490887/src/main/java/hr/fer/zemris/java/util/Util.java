package hr.fer.zemris.java.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Helper class with two main static methods {@link #hextobyte(String)
 * hextobyte} and {@link #bytetohex(byte[]) bytetohex}. They are used in
 * {@link Crypto} for digesting, encrypting and decrypting.
 * 
 * @author dario
 *
 */
public class Util {

    /**
     * Returns String representation of the byte array using big-endian annotation
     * and hex coding rules.
     * 
     * @param array
     *            byte array
     * @return hex value of the byte array
     */
    public static String bytetohex(byte[] array) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < array.length; ++i) {
            int val;
            if (array[i] < 0) {
                val = ((int) array[i]) + 256;
            } else {
                val = (int) array[i];
            }

            builder.append(getChar((val / 16) % 16));
            builder.append(getChar(val % 16));
        }
        return builder.toString();
    }

    /**
     * Returns char for the specific integer value using hex base. It will return
     * lower case for letters from a to f.
     * 
     * @param val
     *            integer value
     * @return character representation in hex base
     */
    private static char getChar(int val) {
        if (val >= 0 && val <= 9) {
            return (char) (val + '0');
        }
        return (char) (val - 10 + 'a');
    }

    /**
     * Returns the encrypted value of SHA-1 algorithm
     * 
     * @param password
     *            string which will be encrypted
     * @return result of encryption
     */
    public static String calculateHash(String password) {

        MessageDigest shaDigest = null;
        try {
            shaDigest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        shaDigest.update(password.getBytes());

        return Util.bytetohex(shaDigest.digest());
    }

}
