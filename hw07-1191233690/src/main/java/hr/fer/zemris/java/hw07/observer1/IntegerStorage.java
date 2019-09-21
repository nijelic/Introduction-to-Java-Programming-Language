package hr.fer.zemris.java.hw07.observer1;

import java.util.List;
import java.util.Objects;
import java.util.ArrayList;

/**
 * This class is the Subject in Observer pattern. It has one integer value and
 * observers are: {@link SquareValue}, {@link ChangeCounter} and
 * {@link DoubleValue}.
 */
public class IntegerStorage {

	/**
	 * Value of integer.
	 */
	private int value;
	/**
	 * Used for hosting observers.
	 */
	private List<IntegerStorageObserver> observers = new ArrayList<>();

	/**
	 * Constructor which sets value.
	 * 
	 * @param initialValue initializes value.
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
	}

	/**
	 * Adds observer implemented as "CopyAndWrite Observer Pattern"
	 * 
	 * @param observer to be added.
	 * @throws NullPointerException if observer is null.
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if (observers.contains(Objects.requireNonNull(observer))) {
			return;
		}
		observers = new ArrayList<>(observers);
		observers.add(observer);
	}

	/**
	 * Removes observer implemented as "CopyAndWrite Observer Pattern".
	 * 
	 * @param observer to be removed.
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		observers = new ArrayList<>(observers);
		observers.remove(observer);
	}

	/**
	 * Clears all observers is implemented as "CopyAndWrite Observer Pattern".
	 */
	public void clearObservers() {
		observers = new ArrayList<>(observers.size());
	}

	/**
	 * Gets value.
	 * 
	 * @return current value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets value and notifies all observers.
	 * 
	 * @param value to be updated
	 */
	public void setValue(int value) {

		if (this.value != value) {
			this.value = value;
			if (observers != null) {
				for (IntegerStorageObserver observer : observers) {
					observer.valueChanged(this);
				}
			}
		}
	}
}
