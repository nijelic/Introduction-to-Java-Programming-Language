package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import hr.fer.zemris.math.Complex;

class ComplexRootedPolynomialTest {

	@Test
	void testApply() {
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(Complex.ONE, new Complex[] {
				Complex.ONE,
				Complex.ONE_NEG,
				Complex.IM,
				Complex.IM_NEG
		});

		assertEquals(Complex.ZERO, crp.apply(Complex.ONE_NEG));
		assertEquals(Complex.ZERO, crp.apply(Complex.ONE));
		assertEquals(Complex.ZERO, crp.apply(Complex.IM_NEG));
		
		crp = new ComplexRootedPolynomial(new Complex(0,10), new Complex[] {
				new Complex(2,-3),
				new Complex(7.5,2.7)
		});
		assertEquals(new Complex(404,290), crp.apply(new Complex(-1,2)));
		assertEquals(new Complex(266,218), crp.apply(Complex.IM));
	}

	@Test
	void testIndexOfClosestRootFor() {
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(Complex.ONE, new Complex[] {
				Complex.ONE,
				Complex.ONE_NEG,
				Complex.IM,
				Complex.IM_NEG
		});
		System.out.println(crp.indexOfClosestRootFor(new Complex(0.5,0.6), 1));
		assertEquals(2, crp.indexOfClosestRootFor(new Complex(0.5,0.6), 1));
	}

}
