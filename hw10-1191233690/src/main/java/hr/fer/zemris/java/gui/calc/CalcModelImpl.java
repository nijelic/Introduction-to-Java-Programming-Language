package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Back end model of calculator. Used for {@link Calculator} GUI.
 * 
 * @author Jelić, Nikola
 */
public class CalcModelImpl implements CalcModel {

	/**
	 * Used to check if value is editable.
	 */
	private boolean editable = true;
	/**
	 * Used to check if value is positive.
	 */
	private boolean positive = true;
	/**
	 * Builds number from digits, sign and decimal point.
	 */
	private StringBuilder digits = new StringBuilder();
	/**
	 * The current value.
	 */
	private double value;
	/**
	 * Saved active operand.
	 */
	private Double activeOperand;
	/**
	 * Saved binary operation.
	 */
	private DoubleBinaryOperator pendingBinaryOperation = null;
	/**
	 * List of listeners.
	 */
	private List<CalcValueListener> calcValueListeners = new ArrayList<>();

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if (!editable) {
			throw new CalculatorInputException("Model is not editable.");
		}
		try {
			char newDigit = (char) (digit + '0');
			boolean inf = false;
			if (Double.isFinite(value)) {
				inf = true;
			}
			value = Double.parseDouble(digits.toString() + newDigit);
			if (Double.isInfinite(value) && inf) {
				throw new CalculatorInputException("Too big number.");
			}
			if (digits.length() == 1 && digits.charAt(0) == '0') {
				digits = new StringBuilder(newDigit);
			} else {
				digits.append(newDigit);
			}

			calcValueListeners.forEach(l -> l.valueChanged(this));

		} catch (NumberFormatException e) {
			throw new CalculatorInputException("Unparsable string with added digit.");
		}
	}

	@Override
	public String toString() {
		if (digits.length() == 0) {
			return positive ? "0" : "-0";
		}
		return digits.toString();
	}

	@Override
	public double getValue() {
		return value;
	}

	@Override
	public void setValue(double value) {
		this.value = value;
		editable = false;
		digits = new StringBuilder(Double.toString(value));
		calcValueListeners.forEach(l -> l.valueChanged(this));
	}

	@Override
	public boolean isEditable() {
		return editable;
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if (!editable) {
			throw new CalculatorInputException("Value is not editable.");
		}
		positive = !positive;
		value = -value;
		if (digits.length() > 0) {
			if (digits.charAt(0) != '-') {
				digits.insert(0, '-');
			} else {
				digits.deleteCharAt(0);
			}
		}
		calcValueListeners.forEach(l -> l.valueChanged(this));
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if (!editable) {
			throw new CalculatorInputException("Value is not editable.");
		}
		if (digits.indexOf(".") != -1) {
			throw new CalculatorInputException("Number already contains decimal point.");
		}
		digits.append(".");
		try {
			value = Double.parseDouble(digits.toString());
			calcValueListeners.forEach(l -> l.valueChanged(this));
		} catch (NumberFormatException e) {
			throw new CalculatorInputException(e.toString());
		}
	}

	@Override
	public void clear() {
		value = 0;
		digits = new StringBuilder();
		editable = true;
		calcValueListeners.forEach(l -> l.valueChanged(this));
	}

	@Override
	public void clearAll() {
		clear();
		clearActiveOperand();
		pendingBinaryOperation = null;
	}

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		calcValueListeners.add(Objects.requireNonNull(l));
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		calcValueListeners.remove(l);
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if (activeOperand == null) {
			throw new IllegalStateException("There is no active operand.");
		}
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
	}

	@Override
	public void clearActiveOperand() {
		activeOperand = null;
	}

	@Override
	public boolean isActiveOperandSet() {
		return activeOperand != null;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingBinaryOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		pendingBinaryOperation = op;
	}
}
