package hr.fer.zemris.java.hw07.demo2;

/**
 * First demo of {@link PrimesCollection}.
 * */
public class PrimesDemo1 {

	/**
	 * Main method.
	 * */
	public static void main(String[] args) {
		
		PrimesCollection primesCollection = new PrimesCollection(5); // 5: how many of them
		for(Integer prime : primesCollection) {
		 System.out.println("Got prime: "+prime);
		}
	}
}
