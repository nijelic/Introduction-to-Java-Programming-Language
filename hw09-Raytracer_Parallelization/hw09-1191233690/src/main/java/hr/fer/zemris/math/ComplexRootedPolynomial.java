package hr.fer.zemris.math;

/**
 * Polynomial of {@link Complex} numbers in rooted form: constant and n roots as
 * z0*(z-z1)*(z-z2)***(z-zn).
 * 
 * @author Jelić, Nikola
 */
public class ComplexRootedPolynomial {

	/**
	 * First element is coefficient/constant and then goes roots.
	 */
	private Complex[] coefAndRoots;

	/**
	 * Constructor sets constant and roots
	 * 
	 * @param constant
	 * @param roots
	 */
	public ComplexRootedPolynomial(Complex constant, Complex... roots) {
		coefAndRoots = new Complex[roots.length + 1];
		coefAndRoots[0] = constant;
		for (int i = 0; i < roots.length; ++i) {
			coefAndRoots[i + 1] = roots[i];
		}
	}

	/**
	 * Returns value of polynomial at z point.
	 * 
	 * @param z point
	 * @return value of polynomial at z.
	 */
	public Complex apply(Complex z) {
		Complex result = coefAndRoots[0];
		for (int i = 1; i < coefAndRoots.length; ++i) {
			result = result.multiply(z.sub(coefAndRoots[i]));
		}
		return result;
	}

	/**
	 * Converts this representation to ComplexPolynomial type.
	 * 
	 * @return this representation as ComplexPolynomial
	 */
	public ComplexPolynomial toComplexPolynom() {
		Complex[] result = new Complex[coefAndRoots.length];
		for (int i = 0; i < result.length; ++i) {
			result[i] = Complex.ZERO;
		}
		buildComplexPolynom(result, coefAndRoots[0], 0, 1);
		return new ComplexPolynomial(result);
	}

	/**
	 * Builds ComplexPolynom recursively.
	 * 
	 * @param result             at first is set to zeros and will change through
	 *                           function calls
	 * @param product            of roots and constant
	 * @param degree             of variable z to which should product be added
	 * @param multiplicatorIndex index of coefAndRoots which is in process to be
	 *                           multiplied
	 */
	private void buildComplexPolynom(Complex[] result, Complex product, int degree, int multiplicatorIndex) {
		if (multiplicatorIndex == result.length) {
			result[degree] = result[degree].add(product);
			return;
		}
		buildComplexPolynom(result, product, degree + 1, multiplicatorIndex + 1);
		buildComplexPolynom(result, product.multiply(coefAndRoots[multiplicatorIndex].negate()), degree,
				multiplicatorIndex + 1);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(" + coefAndRoots[0] + ")");
		for (int i = 1; i < coefAndRoots.length; ++i) {
			sb.append("*(z-" + "(" + coefAndRoots[i] + ")" + ")");
		}
		return sb.toString();
	}

	/**
	 * Finds index of closest root for given complex number z that is within
	 * threshold. If there is no such root, returns -1.
	 * 
	 * @param z
	 * @param threshold
	 * @return index of closest root
	 */
	public int indexOfClosestRootFor(Complex z, double threshold) {
		int index = -1;
		for (int i = 1; i < coefAndRoots.length; ++i) {
			if (z.sub(coefAndRoots[i]).module() <= threshold) {
				threshold = z.sub(coefAndRoots[i]).module();
				index = i - 1;
			}
		}
		return index;
	}
}
