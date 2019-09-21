package hr.fer.zemris.java.gui.layouts.demo;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Container;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import java.awt.Color;

/**
 * This is some demonstration of custom layout manager.
 * 
 */
public class DemoFrame1 extends JFrame {
	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor sets start.
	 */
	public DemoFrame1() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
		pack();
	}

	/**
	 * This method is used for initialization.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(3));
		cp.add(l("tekst 1"), new RCPosition(1, 1));
		cp.add(l("tekst 2"), new RCPosition(2, 3));
		cp.add(l("tekst stvarno najdulji"), new RCPosition(2, 7));
		cp.add(l("tekst kraći"), new RCPosition(4, 2));
		cp.add(l("tekst srednji"), new RCPosition(4, 5));
		cp.add(l("tekst"), new RCPosition(4, 7));
	}

	/**
	 * This method makes Yellow labels.
	 * @param text used as label
	 * @return {@link JLabel}
	 * */
	private JLabel l(String text) {
		JLabel l = new JLabel(text);
		l.setBackground(Color.YELLOW);
		l.setOpaque(true);
		return l;
	}

	/**
	 * Main method.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new DemoFrame1().setVisible(true);
		});
	}
}
