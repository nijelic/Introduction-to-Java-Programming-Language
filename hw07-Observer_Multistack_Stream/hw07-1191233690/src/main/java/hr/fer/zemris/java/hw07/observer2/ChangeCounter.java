package hr.fer.zemris.java.hw07.observer2;

/**
 * Class used as {@link IntegerStorageObserver} for counting changes in
 * {@link IntegerStorage}. When valueChanged is called it prints number of
 * changes.
 */
public class ChangeCounter implements IntegerStorageObserver {

	/**
	 * Counts changes.
	 */
	private int changeCounter;

	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		++changeCounter;
		System.out.println("Number of value changes since tracking: " + changeCounter);
	}
}
