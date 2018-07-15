package hr.fer.zemris.java.hw06.demo2;

/**
 * Demo class for executing first example from the second task.
 * @author dario
 *
 */
public class PrimesDemo1 {

	/**
	 * Executes first example in the second task.
	 * @param args not used
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(5); // 5: how many of them
		for(Integer prime : primesCollection) {
		    System.out.println("Got prime: "+prime);
		}

	}

}