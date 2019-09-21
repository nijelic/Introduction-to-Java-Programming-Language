package hr.fer.zemris.java.hw07.observer2;

/**
 * Instances of SquareValue class prints a square of the updated value in the
 * {@link IntegerStorage} to the standard output.
 */
public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		int value = istorage.getCurrentValue();
		System.out.println("Provided new value: " + value + ", square is " + value * value);
	}
}
