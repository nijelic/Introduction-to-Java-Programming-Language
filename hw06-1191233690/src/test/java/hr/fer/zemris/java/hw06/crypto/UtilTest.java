package hr.fer.zemris.java.hw06.crypto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UtilTest {

	@Test
	void testBytetohex() {
		byte[] bytes = new byte[] {1, -82, 34};
		assertEquals("01ae22", Util.bytetohex(bytes));
		bytes = new byte[] {127, 127, -127, 0};
		assertEquals("7f7f8100", Util.bytetohex(bytes));
	}

	@Test
	void testHextobyte() {
		assertEquals(1, Util.hextobyte("01aE22")[0]);
		assertEquals(-82, Util.hextobyte("01aE22")[1]);
		assertEquals(34, Util.hextobyte("01aE22")[2]);
		assertEquals(127, Util.hextobyte("7F7f8100")[0]);
		assertEquals(-127, Util.hextobyte("7F7f8100")[2]);
		assertEquals(0, Util.hextobyte("7F7f8100")[3]);
		
		assertEquals(0, Util.hextobyte("").length);
		
		// odd-sized
		assertThrows(IllegalArgumentException.class , ()->Util.hextobyte("1aE22"));
		// invalid characters
		assertThrows(IllegalArgumentException.class , ()->Util.hextobyte("1GaE22"));
		assertThrows(IllegalArgumentException.class , ()->Util.hextobyte("1aE:f2"));
		assertThrows(IllegalArgumentException.class , ()->Util.hextobyte("1gaE22"));
	}

}
