package hr.fer.zemris.java.hw06.demo2;

/**
 * Demo class for executing second example from the second task.
 * @author dario
 *
 */
public class PrimesDemo2 {
	
	/**
	 * Executes second example in the second task.
	 * @param args not used
	 */
	public static void main(String[] args) {
		
		PrimesCollection primesCollection = new PrimesCollection(2);
		
		for (Integer prime : primesCollection) {
			for (Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: " + prime + ", " + prime2);
			}
		}
	}
}
