package hr.fer.zemris.math;

/**
 * This class models {@link Complex} polynomial.
 * 
 * @author Jelić, Nikola
 */
public class ComplexPolynomial {

	/**
	 * Data of coefficients of complex polynomial.
	 */
	private Complex[] coefficients;

	/**
	 * Constructor sets coefficients.
	 * 
	 * @param factors/coefficients
	 */
	public ComplexPolynomial(Complex... factors) {
		coefficients = new Complex[factors.length];
		for (int i = 0; i < factors.length; ++i) {
			coefficients[i] = factors[i];
		}
	}

	/**
	 * Returns order of this polynomial; eg. For (7+2i)z^3+2z^2+5z+1 returns 3.
	 * 
	 * @return order of polynomial.
	 */
	public short order() {
		return (short) (coefficients.length - 1);
	}

	/**
	 * Multiplies {@link ComplexPolynomial} p with this.
	 * 
	 * @param p multiplier
	 * @return result as {@link ComplexPolynomial}
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] newCoefficients = new Complex[coefficients.length + p.order()];
		for (int i = 0; i < newCoefficients.length; ++i) {
			newCoefficients[i] = Complex.ZERO;
		}
		for (int j = 0; j <= p.order(); j++) {
			for (int i = 0; i < coefficients.length; ++i) {
				newCoefficients[i + j] = newCoefficients[i + j].add(p.coefficients[j].multiply(coefficients[i]));
			}
		}
		return new ComplexPolynomial(newCoefficients);
	}

	/**
	 * Computes first derivative of this polynomial; for example, for
	 * (7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5
	 * 
	 * @return derivative of this
	 */
	public ComplexPolynomial derive() {
		if (order() == 0) {
			Complex[] derivative = new Complex[1];
			derivative[0] = Complex.ZERO;
			return new ComplexPolynomial(derivative);
		}
		Complex[] derivative = new Complex[order()];
		for (int i = 0; i < derivative.length; ++i) {
			derivative[i] = coefficients[i + 1].multiply(new Complex(i + 1, 0));
		}
		return new ComplexPolynomial(derivative);
	}

	/**
	 * Computes polynomial value at given point z
	 * 
	 * @param given point z
	 * @return evaluation of polynomial at given point z
	 */
	public Complex apply(Complex z) {
		Complex evaluation = coefficients[order()];
		for (int i = order() - 1; i >= 0; --i) {
			evaluation = evaluation.multiply(z).add(coefficients[i]);
		}
		return evaluation;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(" + coefficients[order()] + ")" + "*z^" + order());
		for (int i = order() - 1; i > 0; --i) {
			sb.append("+(" + coefficients[i] + ")" + "z^" + i);
		}
		sb.append("+(" + coefficients[0] + ")");
		return sb.toString();
	}
}
