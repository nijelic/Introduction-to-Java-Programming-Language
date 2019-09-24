package hr.fer.zemris.java.gui.prim;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.gui.prim.PrimDemo.PrimListModel;

class PrimDemoTest {

	@Test
	void testPrimListModel() {
		PrimListModel plm = new PrimListModel();
		assertEquals(1, plm.getSize());
		assertEquals(1, plm.getElementAt(0));
		plm.next();
		plm.next();
		plm.next();
		assertEquals(4, plm.getSize());
		assertEquals(5, plm.getElementAt(3));
	}

}
