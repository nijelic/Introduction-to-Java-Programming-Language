package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class ValueWrapperTest {

	ValueWrapper v;
	
	@Test
	void testValueWrapper() {
		// should not throw
		new ValueWrapper(null);
		new ValueWrapper(new ArrayList<String>());
		new ValueWrapper("auto");
		new ValueWrapper("56.75F");
		new ValueWrapper("1481331.025");
		new ValueWrapper("-1481331.025e-2");
		new ValueWrapper(Integer.valueOf(135));
		new ValueWrapper(Integer.valueOf(0));
		new ValueWrapper(Integer.valueOf(-1353151));
		new ValueWrapper(Double.valueOf(12E-12));
		new ValueWrapper(Double.valueOf(12.22e12));
		new ValueWrapper(Double.valueOf(0));
	}

	@Test
	void testGetValue() {
		assertEquals(null, new ValueWrapper(null).getValue());
		assertEquals("56.75F", new ValueWrapper("56.75F").getValue());
		assertEquals("1481331.025", new ValueWrapper("1481331.025").getValue());
		assertEquals("-1481331.025e-2", new ValueWrapper("-1481331.025e-2").getValue());
		assertEquals(Integer.valueOf(0), new ValueWrapper(Integer.valueOf(0)).getValue());
		assertEquals(Integer.valueOf(-1353151), new ValueWrapper(Integer.valueOf(-1353151)).getValue());
		assertEquals(Double.valueOf(12E-12), new ValueWrapper(Double.valueOf(12E-12)).getValue());
	}

	@Test
	void testSetValue() {
		v = new ValueWrapper("56.75F");
		v.setValue(Float.valueOf(56.75F));
		assertEquals(Float.valueOf(56.75F), v.getValue());
		v.setValue(null);
		assertEquals(null, v.getValue());
	}

	@Test
	void testAdd() {
		
		ValueWrapper vv1 = new ValueWrapper(Boolean.valueOf(true));
		assertThrows(RuntimeException.class, ()->vv1.add(Integer.valueOf(5)));
		
		ValueWrapper vv2 = new ValueWrapper(Integer.valueOf(5));
		assertThrows(RuntimeException.class, ()->vv2.add(Boolean.valueOf(true)));
		
		v = new ValueWrapper("10.75F");
		v.add(Integer.valueOf(-1353151));
		assertEquals(Double.class, v.getValue().getClass());
		assertEquals(-1353140.25, v.getValue());
		
		v.setValue(null);
		v.add("-10000");
		assertEquals(Integer.class, v.getValue().getClass());
		assertEquals(-10000, v.getValue());
		
		v.setValue(Double.valueOf(11.7e3));
		v.add(Integer.valueOf(-10000));
		assertEquals(Double.class, v.getValue().getClass());
		assertEquals(1700.0, v.getValue());
	}

	@Test
	void testSubtract() {
		ValueWrapper vv1 = new ValueWrapper(Boolean.valueOf(true));
		assertThrows(RuntimeException.class, ()->vv1.subtract(Integer.valueOf(5)));
		
		ValueWrapper vv2 = new ValueWrapper(Integer.valueOf(5));
		assertThrows(RuntimeException.class, ()->vv2.subtract(Boolean.valueOf(true)));
		
		v = new ValueWrapper("10.75F");
		v.subtract(Integer.valueOf(-1353151));
		assertEquals(Double.class, v.getValue().getClass());
		assertEquals(1353161.75, v.getValue());
		
		v.setValue(null);
		v.subtract("-10000");
		assertEquals(Integer.class, v.getValue().getClass());
		assertEquals(10000, v.getValue());
		
		v.setValue(Double.valueOf(11.7e3));
		v.subtract(Integer.valueOf(10000));
		assertEquals(Double.class, v.getValue().getClass());
		assertEquals(1700.0, v.getValue());
	}

	@Test
	void testMultiply() {
		v = new ValueWrapper("11e3");
		v.multiply(Integer.valueOf(3));
		assertEquals(Double.class, v.getValue().getClass());
		assertEquals(33e3, v.getValue());
		
		v = new ValueWrapper(null);
		v.multiply(null);
		assertEquals(Integer.class, v.getValue().getClass());
		assertEquals(0, v.getValue());
		
		v = new ValueWrapper("11e3");
		v.multiply(null);
		assertEquals(Double.class, v.getValue().getClass());
		assertEquals(0., v.getValue());
		
		v = new ValueWrapper(-1./3);
		v.multiply(3);
		assertEquals(Double.class, v.getValue().getClass());
		assertEquals(-1., v.getValue());
		
		assertThrows(RuntimeException.class, ()->v.multiply(Boolean.TRUE));
	}

	@Test
	void testDivide() {
		v = new ValueWrapper("10.75F");
		v.divide(Integer.valueOf(10));
		assertEquals(Double.class, v.getValue().getClass());
		assertEquals(1.075, v.getValue());
		
		v.setValue(null);
		v.divide("-10000");
		assertEquals(Integer.class, v.getValue().getClass());
		assertEquals(0, v.getValue());
		
		v.setValue(Double.valueOf(11.7e3));
		v.divide(null);
		assertEquals(Double.class, v.getValue().getClass());
		assertTrue(Double.isInfinite((Double)v.getValue()));
		
		v.setValue("-10000");
		assertThrows(ArithmeticException.class, ()->v.divide(null));
		v.divide(3);
		assertEquals(Integer.class, v.getValue().getClass());		
		assertEquals(-10000/3, v.getValue());
	}

	@Test
	void testNumCompare() {
		v = new ValueWrapper("10.75F");
		assertTrue(v.numCompare(Integer.valueOf(10)) > 0);
		
		v.setValue(Integer.valueOf(10));
		assertTrue(v.numCompare(Integer.valueOf(10)) == 0);
		
		v.setValue("10.75e-3F");
		assertTrue(v.numCompare(Double.valueOf(10.75e-3)) == 0);
		
		v.setValue(null);
		assertTrue(v.numCompare(Double.valueOf(0)) == 0);
		assertTrue(v.numCompare(null) == 0);
		
		assertThrows(RuntimeException.class, ()->v.numCompare("auto"));
		assertThrows(RuntimeException.class, ()->v.numCompare("1.1.1"));
	}

	@Test
	void testExceptions() {
		v = new ValueWrapper("auto");
		assertThrows(RuntimeException.class, ()->v.add(Integer.valueOf(7)));
		v = new ValueWrapper(12);
		assertThrows(RuntimeException.class, ()->v.divide("auto"));
		
		v = new ValueWrapper( new ValueWrapper(new ValueWrapper(7)) );
		assertThrows(RuntimeException.class, ()->v.add(Integer.valueOf(7)));
		v = new ValueWrapper(12);
		assertThrows(RuntimeException.class, ()->v.divide( new ValueWrapper(new ValueWrapper(7)) ) );
	}
	
}
