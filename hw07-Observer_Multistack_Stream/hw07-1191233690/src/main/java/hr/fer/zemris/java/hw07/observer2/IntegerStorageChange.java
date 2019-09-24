package hr.fer.zemris.java.hw07.observer2;

/**
 * This class is used for sending updated changes from Subject to Observers.
 * It has read-only properties.
 * */
public class IntegerStorageChange {

	/**
	 * Reference to Subject.
	 * */
	protected IntegerStorage reference;
	/**
	 * Value before change.
	 * */
	protected int valueBefore;
	/**
	 * Value after change.
	 * */
	protected int currentValue;

	/**
	 * Constructor sets data.
	 * */
	public IntegerStorageChange(IntegerStorage reference, int valueBefore, int currentValue) {
		super();
		this.reference = reference;
		this.valueBefore = valueBefore;
		this.currentValue = currentValue;
	}

	
	/**
	 * Reference getter.
	 * @return reference of {@link IntegerStorage}
	 * */
	public IntegerStorage getReference() {
		return reference;
	}

	/**
	 * Value before change getter.
	 * @return valueBefore change
	 * */
	public int getValueBefore() {
		return valueBefore;
	}

	/**
	 * Current value getter.
	 * @return current value of {@link IntegerStorage}
	 * */
	public int getCurrentValue() {
		return currentValue;
	}
}
