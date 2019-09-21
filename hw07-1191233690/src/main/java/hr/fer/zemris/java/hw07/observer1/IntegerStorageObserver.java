package hr.fer.zemris.java.hw07.observer1;

/**
 * Observer interface for {@link IntegerStorage}.
 */
public interface IntegerStorageObserver {

	/**
	 * Gets notification from {@link IntegerStorage} of its change and updates
	 * state.
	 * 
	 * @param istorage is used for getting updated value.
	 */
	public void valueChanged(IntegerStorage istorage);
}