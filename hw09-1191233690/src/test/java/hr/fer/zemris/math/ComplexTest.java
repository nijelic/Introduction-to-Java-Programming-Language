package hr.fer.zemris.math;


import static java.lang.Math.sqrt;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


class ComplexTest {

	@Test
	void testComplex() {
		assertEquals(0, new Complex().getRe());
		assertEquals(0., new Complex().getIm());
	}

	@Test
	void testComplexDoubleDouble() {
		Complex z = new Complex(-2.3, 7.2);
		assertEquals(-2.3, z.getRe());
		assertEquals(7.2, z.getIm());
	}

	@Test
	void testGetRe() {
		Complex z = new Complex(-2.3, 7.2);
		Complex z2 = new Complex(0, 0);
		Complex z3 = new Complex(1, 1);
		Complex z4 = new Complex(-1, 1);
		Complex z5 = new Complex(1, -1);

		assertEquals(-2.3, z.getRe());
		assertEquals(0., z2.getRe());
		assertEquals(1., z3.getRe());
		assertEquals(-1., z4.getRe());
		assertEquals(1., z5.getRe());
	}

	@Test
	void testGetIm() {
		Complex z = new Complex(-2.3, 7.2);
		Complex z2 = new Complex(0, 0);
		Complex z3 = new Complex(1, 1);
		Complex z4 = new Complex(-1, 1);
		Complex z5 = new Complex(1, -1);

		assertEquals(7.2, z.getIm());
		assertEquals(0., z2.getIm());
		assertEquals(1., z3.getIm());
		assertEquals(1., z4.getIm());
		assertEquals(-1., z5.getIm());
	}

	@Test
	void testModule() {
		assertEquals(sqrt(2.3*2.3+7.2*7.2), new Complex(-2.3, 7.2).module());
		assertEquals(0, new Complex(0, 0).module());
		assertEquals(sqrt(2), new Complex(-1, 1).module());
	}

	@Test
	void testmultiplytiply() {
		Complex z = new Complex(-2.3, 7.2);
		Complex z2 = new Complex(0, 0);
		Complex z3 = new Complex(1, 1);
		Complex z4 = new Complex(-1, 1);
		Complex z5 = new Complex(1, -1);

		assertEquals(new Complex(-9.5, 4.9), z.multiply(z3));
		assertEquals(z2, z2.multiply(z4));
		assertEquals(new Complex(0, 2), z4.multiply(z5));
	}

	@Test
	void testdivideide() {
		Complex z = new Complex(-2.3, 7.2);
		Complex z2 = new Complex(0, 0);
		Complex z3 = new Complex(1, 1);
		Complex z4 = new Complex(-1, 1);
		Complex z5 = new Complex(1, -1);

		assertEquals(new Complex(2.45, 4.75), z.divide(z3));
		assertEquals(z2, z2.divide(z4));
		assertEquals(new Complex(-1, 0), z4.divide(z5));
	}

	@Test
	void testAdd() {
		Complex z = new Complex(-2.3, 7.2);
		Complex z2 = new Complex(0, 0);
		Complex z3 = new Complex(1, 1);
		Complex z4 = new Complex(-1, 1);
		Complex z5 = new Complex(1, -1);

		assertEquals(new Complex(-1.3, 8.2), z.add(z3));
		assertEquals(z4, z2.add(z4));
		assertEquals(z2, z4.add(z5));
	}

	@Test
	void testSub() {
		Complex z = new Complex(-2.3, 7.2);
		Complex z2 = new Complex(0, 0);
		Complex z3 = new Complex(1, 1);
		Complex z4 = new Complex(-1, 1);
		Complex z5 = new Complex(1, -1);

		assertEquals(new Complex(-3.3, 6.2), z.sub(z3));
		assertEquals(z5, z2.sub(z4));
		assertEquals(new Complex(-2, 2), z4.sub(z5));
	}

	@Test
	void testNegate() {
		Complex z = new Complex(-2.3, 7.2);
		assertEquals(2.3, z.negate().getRe());
		assertEquals(-7.2, z.negate().getIm());
	}

	@Test
	void testPower() {
		Complex z = new Complex(-2.3, 7.2);
		Complex z2 = new Complex(0, 0);
		Complex z3 = new Complex(-1, 1);

		assertEquals(new Complex(345.529, -258.984), z.power(3));
		assertEquals(z2, z2.power(7));
		assertEquals(new Complex(1, 0), z3.power(0));

		assertEquals(-0.5, z3.power(-1).getRe(), 1e-7);
		assertEquals(-0.5, z3.power(-1).getIm(), 1e-7);
		assertEquals(1, z3.power(0).getRe(), 1e-7);
		assertEquals(0, z3.power(0).getIm(), 1e-7);
	}

	@Test
	void testRoot() {
		Complex z = new Complex(1, 1);
		Complex z2 = new Complex(0, 0);

		assertEquals(0.16766, z.root(5).get(1).getRe(), 1E-4);
		assertEquals(1.05858, z.root(5).get(1).getIm(), 1E-4);
		assertEquals(0, z2.root(5).get(1).getIm(), 1E-4);
		assertEquals(0, z2.root(5).get(1).getRe(), 1E-4);

		assertTrue(z.root(-1).isEmpty());
		assertTrue(z.root(0).isEmpty());
	}

	@Test
	void testToString() {
		Complex z = new Complex(-2.3, 7.2);
		Complex z2 = new Complex(0, 0);
		Complex z3 = new Complex(1, 1);

		assertEquals("-2.3+7.2i", z.toString());
		assertEquals("0.0", z2.toString());
		assertEquals("1.0+1.0i", z3.toString());
	}

}
