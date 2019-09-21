package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This class is map that allows the user to store multiple values for same key
 * and it provides a stack-like abstraction.
 */
public class ObjectMultistack {

	/**
	 * This class is used to implement stack.
	 */
	private static class MultistackEntry {
		/**
		 * Value of element of stack.
		 */
		private ValueWrapper value;
		/**
		 * Points to next element in stack.
		 */
		private MultistackEntry next;

		/**
		 * Constructor that sets value and next pointer.
		 * 
		 * @param value value of element
		 * @param next  next element in stack
		 */
		public MultistackEntry(ValueWrapper value, MultistackEntry next) {
			super();
			this.value = value;
			this.next = next;
		}
	}

	/**
	 * Used for saving data.
	 * */
	private Map<String, MultistackEntry> map = new HashMap<>();

	/**
	 * Pushes new value to stack at keyName.
	 * @param keyName key of the stack 
	 * @param valueWrapper which needs to push 
	 * */
	public void push(String keyName, ValueWrapper valueWrapper) {
		MultistackEntry entry = map.get(Objects.requireNonNull(keyName));
		MultistackEntry newEntry = new MultistackEntry(Objects.requireNonNull(valueWrapper), entry);

		if (entry == null) {
			map.put(keyName, newEntry);
		} else {
			map.replace(keyName, newEntry);
		}
	}

	/**
	 * Returns value of the top of the stack at keyName and removes that element.
	 * @param keyName Name of key to pop
	 * @return value of top of stack
	 * @throws EmptyStackException if stack is empty
	 * */
	public ValueWrapper pop(String keyName) {
		ValueWrapper peek = peek(keyName);
		MultistackEntry entry = map.get(Objects.requireNonNull(keyName));

		if (entry.next == null) {
			map.remove(keyName);
		} else {
			map.replace(keyName, entry.next);
		}
		return peek;
	}

	/**
	 * Peeks into the stack of a keyName if possible and returns {@link ValueWrapper}.
	 * @param keyName where to peek
	 * @return value at top of stack of keyName
	 * @throws EmptyStackException if stack is empty
	 * */
	public ValueWrapper peek(String keyName) {
		MultistackEntry entry = map.get(Objects.requireNonNull(keyName));
		if (entry == null) {
			throw new EmptyStackException();
		}
		return entry.value;
	}

	/**
	 * Returns true if stack of the keyName is empty, else false.
	 * @param keyName name of key
	 * @return true if stack of the keyName is empty, else false.
	 * */
	public boolean isEmpty(String keyName) {
		return map.get(keyName) == null;
	}
}