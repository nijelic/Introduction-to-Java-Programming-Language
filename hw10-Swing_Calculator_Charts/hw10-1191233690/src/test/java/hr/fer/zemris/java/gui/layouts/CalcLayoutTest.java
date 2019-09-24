package hr.fer.zemris.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Dimension;

class CalcLayoutTest {

	@Test
	void testCalcLayoutInt() {
		assertThrows(CalcLayoutException.class, () -> new CalcLayout(-1));
	}

	@Test
	void testExceptionsRCPostion() {
		JPanel p = new JPanel(new CalcLayout(3));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("x"), new RCPosition(1, 2)));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("y"), new RCPosition(1, 5)));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("z"), new RCPosition(-2, 7)));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("w"), new RCPosition(6, 2)));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("z"), new RCPosition(2, 8)));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("z"), new RCPosition(0, 7)));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("z"), new RCPosition(2, 0)));
	}

	@Test
	void testExceptionsString() {
		JPanel p = new JPanel(new CalcLayout(3));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("x"), "1,2"));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("y"), "1,5"));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("z"), "-2,7"));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("w"), "6,2"));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("z"), "2,8"));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("z"), "0,7"));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("z"), "2,0"));
	}

	@Test
	void testExceptionsExistingPosition() {
		JPanel p = new JPanel(new CalcLayout(3));
		p.add(new JLabel("x"), new RCPosition(1, 1));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("z"), "1,1"));
	}


	@Test
	void test1() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(10,30));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(20,15));
		p.add(l1, new RCPosition(2,2));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		assertEquals(158, dim.height);
		assertEquals(152, dim.width);
	}
	
	@Test
	void test2() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(108,15));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(16,30));
		p.add(l1, new RCPosition(1,1));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		assertEquals(158, dim.height);
		assertEquals(152, dim.width);
	}

	

}
