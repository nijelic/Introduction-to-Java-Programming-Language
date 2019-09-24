package hr.fer.zemris.java.custom.scripting.exec;

import java.util.function.BiFunction;

/**
 * Wrapper of Objects but has special relation to null, Integer, Double or
 * String. If value is some of mentioned than it is possible to apply methods
 * add, subtract, multiply, divide and numCompare.
 */
public class ValueWrapper {

	/**
	 * Value of wrapper
	 * */
	private Object value;

	/**
	 * Base constructor sets value.
	 * @param value to be set
	 * */
	public ValueWrapper(Object value) {
		super();
		this.value = value;
	}

	/**
	 * Getter of value.
	 * @return value of {@link ValueWrapper}
	 * */
	public Object getValue() {
		return value;
	}

	/**
	 * Setter of value.
	 * @param value to set.
	 * */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Adds param to value if compatible, else throws exception.
	 * @param incValue to be added
	 * @throws RuntimeException if error occurs
	 * */
	public void add(Object incValue) {
		BiFunction<Double, Double, Double> function = (v1, v2) -> (v1 + v2);
		calculation(incValue, function);
	}

	/**
	 * Subtracts decValue from value if possible or throws exception.
	 * @param decValue to be subtracted
	 * @throws RuntimeException if error occurs
	 * */
	public void subtract(Object decValue) {
		BiFunction<Double, Double, Double> function = (v1, v2) -> (v1 - v2);
		calculation(decValue, function);
	}

	/**
	 * Multiplies value and mulValue if possible or throws exception.
	 * @param mulValue to be multiplied with value
	 * @throws RuntimeException if error occurs
	 * */
	public void multiply(Object mulValue) {
		BiFunction<Double, Double, Double> function = (v1, v2) -> (v1 * v2);
		calculation(mulValue, function);
	}

	/**
	 * Divides value by divValue if possible or throws exception.
	 * @param divValue divide value
	 * @throws RuntimeException if error occurs 
	 * @throws ArithmeticException if division by zero with Integers occurs
	 * */
	public void divide(Object divValue) {
		BiFunction<Double, Double, Double> function = (v1, v2) -> (v1 / v2);
		calculation(divValue, function);
	}

	/**
	 * Comapares value and withValue if are null, Integer, Double or
	 * String and can be interpreted as numeric else throws exception.
	 * @param withValue compares with value
	 * @throws RuntimeException if error occurs
	 * */
	public int numCompare(Object withValue) {
		try {
			value = parseType(value);
		} catch (RuntimeException e) {
			throw new RuntimeException("Private value of ValueWrapper has wrong format. " + e.getMessage());
		}
		try {
			withValue = parseType(withValue);
		} catch (RuntimeException e) {
			throw new RuntimeException("Argument of function has wrong format.  " + e.getMessage());
		}
		return Double.compare(((Number) value).doubleValue(), ((Number) withValue).doubleValue());
	}

	/**
	 * Calculates value and saves it to value.
	 * @param sndValue value second function argument
	 * @param function to be applied
	 * @throws RuntimeException if error occurs
	 * @throws ArithmeticException if division by zero with Integers occurs
	 * */
	private void calculation(Object sndValue, BiFunction<Double, Double, Double> function) {
		try {
			value = parseType(value);
		} catch (RuntimeException e) {
			throw new RuntimeException("Private value of ValueWrapper has wrong format. " + e.getMessage());
		}
		try {
			sndValue = parseType(sndValue);
		} catch (RuntimeException e) {
			throw new RuntimeException("Argument of function has wrong format.  " + e.getMessage());
		}
		if (value instanceof Integer && sndValue instanceof Integer) {
			Double newValue = function.apply(((Number) value).doubleValue(), ((Number) sndValue).doubleValue());
			if (Double.isInfinite(newValue)) {
				throw new ArithmeticException("/ by zero");
			}
			value = (Integer) newValue.intValue();
			return;
		}
		value = function.apply(((Number) value).doubleValue(), ((Number) sndValue).doubleValue());
	}

	/**
	 * Returns parsed object or throws exception.
	 * @param val value of Object
	 * @return parsed object
	 * @throws RuntimeException if error occurs
	 * */
	private Object parseType(Object val) {
		if (val == null) {
			val = Integer.valueOf(0);
			return val;
		}
		if (val instanceof String) {
			try {
				val = Integer.parseInt((String) val);
				return val;
			} catch (NumberFormatException e) {
				try {
					val = Double.parseDouble((String) val);
					return val;
				} catch (NumberFormatException f) {
					throw new RuntimeException("String is not parsable to Integer nor Double.");
				}
			}
		}
		if (!(val instanceof Double) && !(val instanceof Integer)) {
			throw new RuntimeException("Should be null or instance of String, Double or Integer.");
		}
		return val;
	}
}