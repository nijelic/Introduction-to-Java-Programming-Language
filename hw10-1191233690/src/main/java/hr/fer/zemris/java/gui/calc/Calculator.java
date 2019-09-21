package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.util.function.DoubleBinaryOperator;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.SwingConstants;

import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

import static java.lang.Math.*;
import java.util.function.DoubleUnaryOperator;

import java.util.Stack;

/**
 * This is Calculator GUI. Has basic operations like calculator on Windows XP.
 * 
 * @author Jelić, Nikola
 */
public class Calculator extends JFrame {

	/**
	 * Serial version ID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Model used for back end calculations.
	 */
	private CalcModelImpl model = new CalcModelImpl();
	/**
	 * Label that writes results and inputs.
	 */
	private JLabel label = new JLabel("0", SwingConstants.RIGHT);
	/**
	 * Stack used for saving numbers.
	 */
	private Stack<Double> stack = new Stack<>();

	/**
	 * Default Constructor sets initial state.
	 */
	public Calculator() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
		pack();
	}

	/**
	 * This method sets initial state. Sets all buttons: {@link NumButton}s,
	 * {@link UnaryButton}s, {@link BiButton}s and {@link InverseButton}s.
	 */
	private void initGUI() {

		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(3));

		label.setBackground(Color.YELLOW);
		label.setOpaque(true);
		cp.add(label, new RCPosition(1, 1));
		model.addCalcValueListener((e) -> {
			label.setText(model.toString());
		});
		label.setFont(label.getFont().deriveFont(20f));

		JCheckBox checkBox = new JCheckBox("Inv");
		checkBox.setBackground(Color.getHSBColor(0.4f, 0.9f, 0.5f));
		checkBox.setFont(checkBox.getFont().deriveFont(20f));
		checkBox.setForeground(Color.lightGray);

		// numbers
		cp.add(new NumButton("0", model), new RCPosition(5, 3));
		cp.add(new NumButton("1", model), new RCPosition(4, 3));
		cp.add(new NumButton("2", model), new RCPosition(4, 4));
		cp.add(new NumButton("3", model), new RCPosition(4, 5));
		cp.add(new NumButton("4", model), new RCPosition(3, 3));
		cp.add(new NumButton("5", model), new RCPosition(3, 4));
		cp.add(new NumButton("6", model), new RCPosition(3, 5));
		cp.add(new NumButton("7", model), new RCPosition(2, 3));
		cp.add(new NumButton("8", model), new RCPosition(2, 4));
		cp.add(new NumButton("9", model), new RCPosition(2, 5));

		// basic binary operations
		cp.add(new BiButton("/", model, (o1, o2) -> o1 / o2), new RCPosition(2, 6));
		cp.add(new BiButton("*", model, (o1, o2) -> o1 * o2), new RCPosition(3, 6));
		cp.add(new BiButton("-", model, (o1, o2) -> o1 - o2), new RCPosition(4, 6));
		cp.add(new BiButton("+", model, (o1, o2) -> o1 + o2), new RCPosition(5, 6));

		cp.add(new BiButton("=", model, null), new RCPosition(1, 6));

		// Unary special operations
		cp.add(new UnaryButton("clr", (e) -> {
			model.clear();
		}), new RCPosition(1, 7));
		cp.add(new UnaryButton("res", (e) -> {
			model.clearAll();
		}), new RCPosition(2, 7));
		cp.add(new UnaryButton("push", (e) -> {
			stack.push(model.getValue());
		}), new RCPosition(3, 7));
		cp.add(new UnaryButton("pop", (e) -> {
			if (stack.isEmpty()) {
				label.setText("Stack is empty.");
			} else {
				model.setValue(stack.pop());
			}
		}), new RCPosition(4, 7));

		// change sign
		cp.add(new UnaryButton("+/-", (e) -> {
			try {
				model.swapSign();
			} catch (IllegalStateException | IllegalArgumentException | CalculatorInputException f) {
			}
		}), new RCPosition(5, 4));

		// adds decimal point
		cp.add(new UnaryButton(".", (e) -> {
			try {
				model.insertDecimalPoint();
			} catch (IllegalStateException | IllegalArgumentException | CalculatorInputException f) {
			}
		}), new RCPosition(5, 5));

		// button for reciprocal
		cp.add(new UnaryButton("1/x", (e) -> {
			model.setValue(1 / model.getValue());
		}), new RCPosition(2, 1));

		// Buttons with inverses
		InverseButton sin = new InverseButton("sin", model, checkBox, Math::asin, Math::sin);
		cp.add(sin, new RCPosition(2, 2));

		InverseButton log = new InverseButton("log", model, checkBox, (num) -> pow(10, num), Math::log10);
		cp.add(log, new RCPosition(3, 1));

		InverseButton cos = new InverseButton("cos", model, checkBox, Math::acos, Math::cos);
		cp.add(cos, new RCPosition(3, 2));

		InverseButton ln = new InverseButton("ln", model, checkBox, Math::exp, Math::log);
		cp.add(ln, new RCPosition(4, 1));

		InverseButton tan = new InverseButton("tan", model, checkBox, Math::atan, Math::tan);
		cp.add(tan, new RCPosition(4, 2));

		BiInvButton xn = new BiInvButton("x^n", model, checkBox, (x, n) -> pow(x, 1 / n), Math::pow);
		xn.setBackground(Color.getHSBColor(0.9f, 0.5f, 0.5f));
		cp.add(xn, new RCPosition(5, 1));

		InverseButton ctg = new InverseButton("ctg", model, checkBox, (num) -> Math.atan(1 / num),
				(num) -> 1 / Math.tan(num));
		cp.add(ctg, new RCPosition(5, 2));

		// adds listener for checking checkbox
		checkBox.addActionListener((l) -> {
			if (checkBox.isSelected()) {
				sin.setText("arcsin");
				cos.setText("arccos");
				tan.setText("arctan");
				ctg.setText("arcctg");
				log.setText("10^x");
				ln.setText("e^x");
				xn.setText("x^(1/n)");
			} else {
				sin.setText("sin");
				cos.setText("cos");
				tan.setText("tan");
				ctg.setText("ctg");
				log.setText("log");
				ln.setText("ln");
				xn.setText("x^n");
			}
		});
		cp.add(checkBox, new RCPosition(5, 7));
	}

	/**
	 * Custom {@link JButton} for Numbers.
	 */
	private class NumButton extends JButton {

		/**
		 * serial version ID
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Constructor adds listener that inserts digit into model and sets color and
		 * font of buttons.
		 * 
		 * @param text  Name of button and digit to be inserted into model
		 * @param model used for inserting digit
		 */
		public NumButton(String text, CalcModelImpl model) {
			super(text);
			super.setBackground(Color.getHSBColor(0.5f, 0.5f, 0.5f));
			super.setForeground(Color.lightGray);
			super.setFont(super.getFont().deriveFont(20f));
			super.addActionListener((e) -> {
				try {
					model.insertDigit(text.charAt(0) - '0');
				} catch (IllegalStateException | IllegalArgumentException | CalculatorInputException f) {
				}
			});
		}
	}

	/**
	 * Custom {@link JButton} for Binary operators.
	 */
	private class BiButton extends JButton {

		/**
		 * serial version ID
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Constructor adds listener that operates on operands with
		 * {@link DoubleBinaryOperator} and sets color and font of buttons
		 * 
		 * @param text  Name of button and name of binary operation
		 * @param model used for calculation
		 * @param o     the {@link DoubleBinaryOperator} which operates on operands in
		 *              the model
		 */
		public BiButton(String text, CalcModelImpl model, DoubleBinaryOperator o) {
			super(text);
			super.setBackground(Color.getHSBColor(0.5f, 0.5f, 0.7f));
			super.setForeground(Color.black);
			super.setFont(super.getFont().deriveFont(20f));
			super.addActionListener((e) -> {
				try {
					if (!model.isActiveOperandSet()) {
						model.setActiveOperand(model.getValue());
					} else if (model.getPendingBinaryOperation() != null) {
						double operand = model.getActiveOperand();
						double active = model.getPendingBinaryOperation().applyAsDouble(operand, model.getValue());
						model.setActiveOperand(active);
					}
					model.setPendingBinaryOperation(o);
					model.clear();
					label.setText(Double.toString(model.getActiveOperand()));
				} catch (IllegalStateException | IllegalArgumentException | CalculatorInputException f) {
				}
			});
		}
	}

	/**
	 * Custom {@link JButton} for Unary operators.
	 */
	private class UnaryButton extends JButton {

		/**
		 * serial version ID
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Constructor adds listener and sets color and font of buttons
		 * 
		 * @param text  Name of button and name of unary operation
		 * @param model used for calculation
		 * @param a     the action listener
		 */
		public UnaryButton(String text, ActionListener a) {
			super(text);
			super.setBackground(Color.getHSBColor(0.9f, 0.5f, 0.5f));
			super.setForeground(Color.lightGray);
			super.setFont(super.getFont().deriveFont(20f));
			super.addActionListener(a);
		}
	}

	/**
	 * Custom {@link JButton} for Buttons with inverses.
	 */
	private class InverseButton extends JButton {

		/**
		 * serial version ID
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Used for Unary Operators Constructor adds listener and sets color and font of
		 * buttons. Listener checks if there Binary operator already exists and makes
		 * calculations.
		 * 
		 * @param text  Name of button and name of operation
		 * @param model used for calculation
		 * @param box   the {@link JCheckBox} to determine if box is checked
		 * @param o1    the operator of inverse
		 * @param o2    the normal operator
		 */
		public InverseButton(String text, CalcModelImpl model, JCheckBox box, DoubleUnaryOperator o1,
				DoubleUnaryOperator o2) {
			super(text);
			super.setBackground(Color.getHSBColor(0.9f, 0.5f, 0.5f));
			super.setForeground(Color.lightGray);
			super.setFont(super.getFont().deriveFont(20f));
			super.addActionListener((e) -> {
				if (box.isSelected()) {
					model.setValue(o1.applyAsDouble(model.getValue()));
				} else {
					model.setValue(o2.applyAsDouble(model.getValue()));
				}
			});
		}
	}

	/**
	 * Custom {@link JButton} for the x^n operation and its inverse.
	 */
	private class BiInvButton extends JButton {

		/**
		 * serial version ID
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Used for Binary operator. Constructor adds listener and sets color and font
		 * of buttons. Listener checks if there Binary operator already exists and makes
		 * calculations.
		 * 
		 * @param text  Name of button and name of operation
		 * @param model used for calculation
		 * @param box   the {@link JCheckBox} to determine if box is checked
		 * @param o1    the operator of inverse
		 * @param o2    the normal operator
		 */
		public BiInvButton(String text, CalcModelImpl model, JCheckBox box, DoubleBinaryOperator o1,
				DoubleBinaryOperator o2) {
			super(text);
			super.setFont(super.getFont().deriveFont(20f));
			super.setForeground(Color.lightGray);
			super.addActionListener((e) -> {
				try {
					if (!model.isActiveOperandSet()) {
						model.setActiveOperand(model.getValue());
					} else if (model.getPendingBinaryOperation() != null) {
						double operand = model.getActiveOperand();
						double active = model.getPendingBinaryOperation().applyAsDouble(operand, model.getValue());
						model.setActiveOperand(active);
					}
					if (box.isSelected()) {
						model.setPendingBinaryOperation(o1);
					} else {
						model.setPendingBinaryOperation(o2);
					}
					model.clear();
					label.setText(Double.toString(model.getActiveOperand()));
				} catch (IllegalStateException | IllegalArgumentException | CalculatorInputException f) {
				}
			});
		}
	}

	/**
	 * Main method starts program.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Calculator().setVisible(true);
		});
	}
}
