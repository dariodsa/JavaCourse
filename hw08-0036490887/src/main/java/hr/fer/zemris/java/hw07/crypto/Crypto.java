package hr.fer.zemris.java.hw07.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import hr.fer.zemris.java.hw07.Util;

/**
 * Class {@link Crypto} allow the user to encrypt/decrypt given file using the
 * AES crypto-algorithm and the 128-bit encryption key or calculate and check
 * the SHA-256 file digest.
 * 
 * @author dario
 *
 */
public class Crypto {

	/**
	 * static reader from standard input
	 */
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	/**
	 * Main method which loads arguments, accepts input from standard input stream
	 * and prints the result.
	 * 
	 * @param args
	 *            two or more arguement, depends on input, but it will contains file
	 *            locations which we want to encrpy and location of the new one
	 */
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("You should provide 2 or more arguments.");
			System.exit(1);
		}
		try {
		switch (args[0].trim().toLowerCase()) {
		case "checksha":
			checkSHA(args);
			break;
		case "encrypt":
			operate(Operation.ENCRYPT, args);
			break;
		case "decrypt":
			operate(Operation.DECRYPT, args);
			break;
		default:
			System.out.println("Unsupported operation.");
			System.exit(1);
		}
		} catch(Exception ex) {
			System.err.println(ex.getMessage());
		}

	}
	/**
	 * Generates specific command based on which type of 
	 * operation it receives as argument which will be used in 
	 * running  the commands as they will be input and output
	 * files.
	 * @param operation {@link Operation} type of operation
	 * @param args {@link String} array of argumetns needed for 
	 * operations
	 */
	private static void operate(Operation operation, String[] args) {

		try {
			System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
			String keyText = getFromInput(reader);
			
			System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
			String ivText = getFromInput(reader);
			
			SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
			
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(operation == Operation.ENCRYPT ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);

			BufferedInputStream reader = new BufferedInputStream(Files.newInputStream(Paths.get(args[1])));
			BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(args[2]));
			
			byte[] buff = new byte[2048];
			int len = 0;
			
			while ((len = reader.read(buff)) != -1) {

				writer.write(cipher.update(buff, 0, len));
			}
			
			writer.write(cipher.doFinal());
			writer.close();
			
			if (operation == Operation.ENCRYPT) {
				System.out.println("Encryption completed. Generated file " + args[2] + " based on " + args[1]);
			} else {
				System.out.println("Decryption completed. Generated file " + args[2] + " based on " + args[1]);
			}

		} catch (InvalidKeyException | InvalidAlgorithmParameterException e) {

			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		} catch (NoSuchPaddingException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Method checkSHA will check SHA-256 digest of two 
	 * files which location is in the arguments.
	 * @param args locations of those two file
	 */
	private static void checkSHA(String[] args) {

		Path[] paths = new Path[args.length - 1];
		for (int i = 1; i < args.length; ++i) {
			paths[i - 1] = Paths.get(args[i]);
		}
		for (Path path : paths) {
			System.out.printf("Please provide expected sha-256 digest for %s:%n", path.getFileName());
			
			String expected = getFromInput(reader);

			try (BufferedInputStream br = new BufferedInputStream(Files.newInputStream(path))) {

				MessageDigest shaDigest = MessageDigest.getInstance("SHA-256");

				byte[] data = new byte[2048];
				int len = 0;
				while ((len = br.read(data)) != -1) {
					shaDigest.update(data, 0, len);
				}

				byte[] expectedByteArray = Util.hextobyte(expected);
				
				if (MessageDigest.isEqual(shaDigest.digest(), expectedByteArray)) {
					System.out.println(
							"Digesting completed. Digest of " + path.getFileName() + " matches expected digest.");
				} else {
					System.out.println("Digesting completed. " + "Digest of " + path.getFileName()
							+ " does not match the expected digest. Digest\n" + "was: "
							+ Util.bytetohex(shaDigest.digest()));
				}
				

			} catch (IOException e) {
				System.err.println("Unable to read the given file!");
				System.exit(1);
			} catch (NoSuchAlgorithmException e) {
				System.err.println("Algorithm SHA-256 is not supported.");
				System.exit(1);
			}

		}

	}

	/**
	 * Returns line read from {@link BufferedReader} br, which was given as
	 * argument.
	 * 
	 * @param br
	 *            buffered reader from which is new line read
	 * @return read new line from buffered reader
	 */
	private static String getFromInput(BufferedReader br) {

		String line = "";
		System.out.print("> ");
		try {
			line = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return line;

	}
}
