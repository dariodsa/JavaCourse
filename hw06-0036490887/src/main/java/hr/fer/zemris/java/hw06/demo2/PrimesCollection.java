package hr.fer.zemris.java.hw06.demo2;

import java.util.Iterator;
/**
 * {@link PrimesCollection} implements {@link Iterable Iterable<Integer>} and allows you 
 * to get primes numbers. In the constructor you type how many you need and after that 
 * you simply calls iterator to get you new one.
 * @author dario
 *
 */
public class PrimesCollection implements Iterable<Integer>{

	/**
	 * size of the primesCollection
	 */
	private int size;
	/**
	 * Constructs Primes
	 * @param size
	 */
	public PrimesCollection(int size) {
		this.size = size;
	
	}
	@Override
	public Iterator<Integer> iterator() {
		return new PrimeIteraor();
	}
	/**
	 * Class {@link PrimeIteraor} allows you to iterate over collection of 
	 * prime numbers. Primes numbers aren't generated in <i>O(1)</i> instead 
	 * of that they are generated in time complexity of <i>O(sqrt(N)/2)</i> which is <i>O(sqrt(N))</i>
	 * @author dario
	 *
	 */
	class PrimeIteraor implements Iterator<Integer> {

		/**
		 * number of iterated primes 
		 */
		private int current = 0;
		/**
		 * last generated prime number
		 */
		private int lastGenerated = 1;
		
		@Override
		public boolean hasNext() {
			return current < size;
		}
		@Override
		public Integer next() {
			++lastGenerated;
			++current;
			for(;;++lastGenerated) {
				if(isPrime(lastGenerated)) break;
			}
			return lastGenerated;
		}
		/**
		 * Returns true if the number is prime.
		 * @param number which is going to be tested if it is prime 
		 * @return true if the number is prime, false otherwise
		 */
		private boolean isPrime(int number) {
			
			if(number < 2) return false;
			if(number == 2) return true;
			if(number%2==0) return false;
			for(int i=3, max=(int)Math.sqrt(number);i<=max; i +=2) {
				if(number % i == 0) return false;
			}
			return true;
		}
		
	}
}
