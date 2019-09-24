package hr.fer.zemris.java.hw07.observer1;

/**
 * Class used as {@link IntegerStorageObserver} for writing double value (i.e.
 * “value * 2”) of the current value which is stored in subject, but only first
 * n times since its registration with the subject.
 */
public class DoubleValue implements IntegerStorageObserver {

	/**
	 * Counts calls.
	 */
	private int numberOfCalls;
	/**
	 * Used for setting number of times.
	 */
	private int limit;

	/**
	 * Constructor sets number of times.
	 */
	public DoubleValue(int limit) {
		this.limit = limit;
	}

	@Override
	public void valueChanged(IntegerStorage istorage) {
		if (numberOfCalls++ < limit) {
			System.out.println("Double value: " + 2 * istorage.getValue());
		} else {
			istorage.removeObserver(this);
		}
	}
}
