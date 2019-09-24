package hr.fer.zemris.java.gui.layouts.demo;

import javax.swing.WindowConstants;
import javax.swing.JLabel;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import hr.fer.zemris.java.gui.layouts.RCPosition;
import java.awt.Color;

/**
 * This is some demonstration of custom layout manager.
 */
public class Demo1 extends JFrame {

	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor sets start.
	 */
	public Demo1() {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
	}

	/**
	 * This method is used for initialization.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(3));

		JLabel l1 = new JLabel("x");
		l1.setBackground(Color.CYAN);
		l1.setOpaque(true);
		cp.add(l1, new RCPosition(1, 1));

		JLabel l2 = new JLabel("y");
		l2.setBackground(Color.CYAN);
		l2.setOpaque(true);
		cp.add(l2, new RCPosition(2, 3));

		JLabel l3 = new JLabel("z");
		l3.setBackground(Color.CYAN);
		l3.setOpaque(true);
		cp.add(l3, new RCPosition(2, 7));

		JLabel l4 = new JLabel("w");
		l4.setBackground(Color.CYAN);
		l4.setOpaque(true);
		cp.add(l4, new RCPosition(4, 2));

		JLabel l5 = new JLabel("a");
		l5.setBackground(Color.CYAN);
		l5.setOpaque(true);
		cp.add(l5, new RCPosition(4, 5));

		JLabel l6 = new JLabel("b");
		l6.setBackground(Color.CYAN);
		l6.setOpaque(true);
		cp.add(l6, new RCPosition(4, 7));

		setSize(getPreferredSize());
	}

	/**
	 * Main method.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Demo1().setVisible(true);
		});
	}
}
