package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.*;

import java.util.EmptyStackException;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

class ObjectMultistackTest {

	ObjectMultistack multistack;

	@Test
	void testPush() {
		multistack = new ObjectMultistack();

		assertThrows(NullPointerException.class, () -> multistack.push(null, new ValueWrapper("str")));
		assertThrows(NullPointerException.class, () -> multistack.push("str", null));

		multistack.push("ana", new ValueWrapper(17.4));
		multistack.push("tin", new ValueWrapper("17.4"));
		multistack.push("ana", new ValueWrapper(new HashMap<String, String>()));
		multistack.push("ana", new ValueWrapper(-11));
		assertEquals(-11, multistack.peek("ana").getValue());
	}

	@Test
	void testPop() {
		multistack = new ObjectMultistack();
		HashMap map = new HashMap<String, String>();

		multistack.push("ana", new ValueWrapper(17.4));
		multistack.push("tin", new ValueWrapper("17.4"));
		multistack.push("ana", new ValueWrapper(map));
		multistack.push("ana", new ValueWrapper(-11));

		assertEquals(-11, multistack.pop("ana").getValue());

		// pushing after poping
		multistack.push("ana", new ValueWrapper(7));
		assertEquals(7, multistack.pop("ana").getValue());

		assertEquals(map, multistack.pop("ana").getValue());

		// exceptions
		assertEquals(17.4, multistack.pop("ana").getValue());
		assertThrows(EmptyStackException.class, () -> multistack.pop("ana"));

		assertEquals("17.4", multistack.pop("tin").getValue());
		assertThrows(EmptyStackException.class, () -> multistack.pop("tin"));

		// pushing after clearing all
		multistack.push("ana", new ValueWrapper(17.4));
		assertEquals(17.4, multistack.pop("ana").getValue());
	}

	@Test
	void testPeek() {
		multistack = new ObjectMultistack();
		HashMap map = new HashMap<String, String>();

		multistack.push("ana", new ValueWrapper(17.4));
		multistack.push("tin", new ValueWrapper("17.4"));
		multistack.push("ana", new ValueWrapper(map));
		multistack.push("ana", new ValueWrapper(-11));

		assertEquals(-11, multistack.peek("ana").getValue());
		multistack.pop("ana");

		assertEquals(map, multistack.peek("ana").getValue());
		multistack.pop("ana");

		assertEquals(17.4, multistack.pop("ana").getValue());
		assertThrows(EmptyStackException.class, () -> multistack.peek("ana"));

		assertEquals("17.4", multistack.pop("tin").getValue());
		assertThrows(EmptyStackException.class, () -> multistack.peek("tin"));

		// Peeks after clearing all and pushing new value
		multistack.push("ana", new ValueWrapper(17.4));
		assertEquals(17.4, multistack.pop("ana").getValue());
	}

	@Test
	void testIsEmpty() {
		multistack = new ObjectMultistack();
		HashMap map = new HashMap<String, String>();

		assertTrue(multistack.isEmpty("ana"));

		multistack.push("ana", new ValueWrapper(17.4));
		multistack.push("tin", new ValueWrapper("17.4"));
		multistack.push("ana", new ValueWrapper(map));
		multistack.push("ana", new ValueWrapper(-11));

		assertFalse(multistack.isEmpty("ana"));
		assertFalse(multistack.isEmpty("tin"));

		assertEquals(-11, multistack.pop("ana").getValue());
		assertEquals(map, multistack.pop("ana").getValue());

		assertEquals(17.4, multistack.pop("ana").getValue());
		assertTrue(multistack.isEmpty("ana"));

		assertEquals("17.4", multistack.pop("tin").getValue());
		assertTrue(multistack.isEmpty("tin"));
	}

}
