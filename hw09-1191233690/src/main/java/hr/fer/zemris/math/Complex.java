package hr.fer.zemris.math;

import static java.lang.Math.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class models complex numbers.
 * 
 * @author Jelić, Nikola
 */
public class Complex {

	/**
	 * Real Part
	 */
	private double re;
	/**
	 * Imaginary Part
	 */
	private double im;
	/**
	 * Complex zero
	 */
	public static final Complex ZERO = new Complex(0, 0);
	/**
	 * Complex one
	 */
	public static final Complex ONE = new Complex(1, 0);
	/**
	 * Complex negative one
	 */
	public static final Complex ONE_NEG = new Complex(-1, 0);
	/**
	 * Complex imaginary one
	 */
	public static final Complex IM = new Complex(0, 1);
	/**
	 * Complex negative imaginary one
	 */
	public static final Complex IM_NEG = new Complex(0, -1);
	/**
	 * Constant Epsilon - used for comparing
	 */
	final static double EPSILON = 1E-7;

	/**
	 * Default constructor
	 */
	public Complex() {
		this.re = 0;
		this.im = 0;
	}

	/**
	 * Constructor
	 * 
	 * @param re real part
	 * @param im imaginary part
	 */
	public Complex(double re, double im) {
		super();
		this.re = re;
		this.im = im;
	}

	/**
	 * Gets real part.
	 * 
	 * @return real part
	 */
	public double getRe() {
		return re;
	}

	/**
	 * Gets imaginary part.
	 * 
	 * @return imaginary part
	 */
	public double getIm() {
		return im;
	}

	/**
	 * Returns magnitude of number.
	 * 
	 * @return magnitude
	 */
	public double module() {
		return Math.sqrt(re * re + im * im);
	}

	/**
	 * Multiplication of this and complex number.
	 * 
	 * @param c complex number
	 * @return complex number, new instance
	 */
	public Complex multiply(Complex c) {
		return fromMagnitudeAndAngle(module() * c.module(), getAngle(this) + getAngle(c));
	}

	/**
	 * Divides this and complex number.
	 * 
	 * @param c complex number
	 * @return complex number, new instance
	 */
	public Complex divide(Complex c) {
		return fromMagnitudeAndAngle(module() / c.module(), getAngle(this) - getAngle(c));
	}

	/**
	 * Adds this with complex number and returns new result.
	 * 
	 * @param c complex number
	 * @return complex number, new instance
	 */
	public Complex add(Complex c) {
		return new Complex(re + c.getRe(), im + c.getIm());
	}

	/**
	 * Subs this with complex number and returns new result.
	 * 
	 * @param c complex number
	 * @return complex number, new instance
	 */
	public Complex sub(Complex c) {
		return new Complex(re - c.getRe(), im - c.getIm());
	}

	/**
	 * Returns negative complex number of this.
	 * 
	 * @return negative complex number of this as new instance
	 */
	public Complex negate() {
		return new Complex(-re, -im);
	}

	/**
	 * Returns nth power of this number.
	 * 
	 * @param n the power
	 * @return complex number
	 */
	public Complex power(int n) {
		return fromMagnitudeAndAngle(pow(module(), n), n * getAngle(this));
	}

	/**
	 * Returns n nth roots of this number. Returns empty list if n<=0.
	 * 
	 * @param n
	 * @return array of complex numbers
	 */
	public List<Complex> root(int n) {

		List<Complex> roots = new ArrayList<>();
		if (n <= 0) {
			return roots;
		}

		double magnitudeRoot = pow(module(), 1.0 / n);
		double angle = getAngle(this);

		for (int i = 0; i < n; ++i) {
			roots.add(fromMagnitudeAndAngle(magnitudeRoot, (angle + 2 * i * PI) / n));
		}
		return roots;
	}

	@Override
	public String toString() {
		StringBuilder number = new StringBuilder();

		// if this.real almost zero
		if (!closeEnough(re, 0d)) {
			number.append(Double.toString(re));
		} else {
			number.append("0.0");
		}

		// if this.imaginary almost zero
		if (!closeEnough(im, 0d)) {
			if (im > 0) {
				number.append("+i");
			} else {
				number.append("-i");
			}
			number.append(Double.toString(Math.abs(im)));
		} else {
			number.append("+i0.0");
		}
		return number.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(im, re);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Complex other = (Complex) obj;
		return closeEnough(im, other.getIm()) && closeEnough(re, other.getRe());
	}

	/**
	 * Returns true if two numbers are close enough
	 * 
	 * @param x
	 * @param y
	 * @return true if almost equal, else false
	 */
	private boolean closeEnough(double x, double y) {
		return x - y < EPSILON && y - x < EPSILON;
	}

	/**
	 * Returns angle of number.
	 * 
	 * @return real part
	 */
	private static double getAngle(Complex c) {
		if (atan2(c.getIm(), c.getRe()) < 0) {
			return atan2(c.getIm(), c.getRe()) + 2 * PI;
		}
		return atan2(c.getIm(), c.getRe());
	}

	/**
	 * Creates new ComplexNumber from magnitude and angle.
	 * 
	 * @param magnitude of complex number
	 * @param angle     of complex number
	 * @return complex number
	 * @throws IllegalArgumentException if magnitude < 0
	 */
	private static Complex fromMagnitudeAndAngle(double magnitude, double angle) throws IllegalArgumentException {
		if (magnitude < 0) {
			throw new IllegalArgumentException("magnitude < 0");
		}
		return new Complex(magnitude * cos(angle), magnitude * sin(angle));
	}

}
