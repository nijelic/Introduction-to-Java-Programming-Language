package hr.fer.zemris.java.hw07.demo2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class is used for generating first n primes.
 */
public class PrimesCollection implements Iterable<Integer> {

	/**
	 * Number that says how many primes will be generated.
	 */
	private int numberOfPrimes;

	/**
	 * Constructor that sets numberOfPrimes.
	 * 
	 * @param numberOfPrimes
	 */
	public PrimesCollection(int numberOfPrimes) {
		super();
		this.numberOfPrimes = numberOfPrimes;
	}

	/**
	 * Returns Iterator.
	 * @return iterator which is generator of primes
	 * */
	@Override
	public Iterator<Integer> iterator() {
		return new MyIterator(numberOfPrimes);
	}

	/**
	 * Implementation of primes generator.
	 * */
	private static class MyIterator implements Iterator<Integer> {

		/**
		 * Current prime.
		 * */
		private int prime = 2;
		/**
		 * Number that says how many primes will be generated.
		 * */
		private int numberOfPrimes;

		/**
		 * Constructor that sets numberOfPrimes.
		 * @param numberOfPrimes 
		 * */
		public MyIterator(int numberOfPrimes) {
			this.numberOfPrimes = numberOfPrimes;
		}

		/**
		 * Returns true if has left any prime, else false.
		 * @return true if has left any prime, else false.
		 * */
		@Override
		public boolean hasNext() {
			return numberOfPrimes > 0;
		}

		/**
		 * Returns next prime.
		 * @return next prime.
		 * */
		@Override
		public Integer next() {
			if (numberOfPrimes == 0) {
				throw new NoSuchElementException();
			}
			numberOfPrimes--;
			if (prime == 2) {
				prime = 3;
				return 2;
			}
			int oldPrime = prime;
			prime += 2;
			while (primalityTest(prime) == false) {
				prime += 2;
			}
			return oldPrime;
		}

		/**
		 * Tests if p is prime.
		 * @param p prime to test
		 * @return true if is prime, else false.
		 * */
		private boolean primalityTest(int p) {
			for (int i = 2; i * i <= p; ++i) {
				if (p % i == 0) {
					return false;
				}
			}
			return true;
		}
	}

}
