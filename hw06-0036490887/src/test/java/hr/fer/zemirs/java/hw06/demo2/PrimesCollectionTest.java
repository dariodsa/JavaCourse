package hr.fer.zemirs.java.hw06.demo2;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import hr.fer.zemris.java.hw06.demo2.PrimesCollection;

public class PrimesCollectionTest {
	
	@Test
	public void testFirstTwoPrimeNumbers() {
		PrimesCollection primes = new PrimesCollection(2);
		List<Integer> results = new ArrayList<>();
		for(Integer prime: primes) {
			results.add(prime);
		}
		Assert.assertArrayEquals(results.toArray(new Integer[0]), new Integer[] {2,3});
	}
	
	@Test
	public void testFirstFivePrimeNumbers() {
		PrimesCollection primes = new PrimesCollection(5);
		List<Integer> results = new ArrayList<>();
		for(Integer prime: primes) {
			results.add(prime);
		}
		Assert.assertArrayEquals(results.toArray(new Integer[0]), new Integer[] {2,3,5,7,11});
	}
	
	@Test
	public void testPrimePairs() {
		PrimesCollection primes = new PrimesCollection(3);
		PrimesCollection primes2 = new PrimesCollection(3);
		List<Integer> results = new ArrayList<>();
		for(Integer prime: primes) {
			results.add(prime);
			for(Integer prime2: primes2) {
				results.add(prime2);
			}
		}
		Assert.assertArrayEquals(results.toArray(new Integer[0]), new Integer[] {2,2,3,5,3,2,3,5,5,2,3,5});
	}
}
