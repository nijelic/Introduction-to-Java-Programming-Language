package hr.fer.zemris.java.fractals;

import java.util.concurrent.atomic.AtomicBoolean;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * This class is used to compute Newton-Raphson iteration of one image segment.
 * 
 * @author Jelić, Nikola
 */
public class NewtonRaphson {

	/**
	 * Method called to compute iteration of one image segment and saves pixels into
	 * data.
	 * 
	 * @param crp                  Polynomial to compute
	 * @param convergenceThreshold used to check if iteration converged
	 * @param maxIter              maximum number of iterations of one pixel
	 * @param rootThreshold        used to check which root is the closest
	 * @param reMin                Real minimum
	 * @param reMax                Real maximum
	 * @param imMin                Imaginary minimum
	 * @param imMax                Imaginary maximum
	 * @param width                of viewer
	 * @param height               of viewer
	 * @param ymin                 of observed segment
	 * @param ymax                 of observed segment
	 * @param data                 which is filled with values for pixels
	 * @param cancel               called for breaking
	 */
	public static void compute(ComplexRootedPolynomial crp, double convergenceThreshold, int maxIter,
			double rootThreshold, double reMin, double reMax, double imMin, double imMax, int width, int height, int m,
			int ymin, int ymax, short[] data, AtomicBoolean cancel) {

		int xmin = 0;
		int xmax = width - 1;
		ComplexPolynomial derivated = crp.toComplexPolynom().derive();

		for (int y = ymin; y <= ymax; ++y) {
			if (cancel.get()) {
				break;
			}
			for (int x = xmin; x <= xmax; ++x) {
				Complex zn = mapToComplexPlain(x, y, width, height, reMin, reMax, imMin, imMax);
				Complex znOld = zn;
				int iter = 0;
				do {
					znOld = zn;
					zn = zn.sub(crp.apply(zn).divide(derivated.apply(zn)));
					iter++;
				} while (zn.sub(znOld).module() > convergenceThreshold && iter < maxIter);
				data[y * width + x] = (short) (crp.indexOfClosestRootFor(zn, rootThreshold) + 1);
			}
		}
	}

	/**
	 * Calculates complex value of pixel.
	 * 
	 * @param x      axis of pixel
	 * @param y      axis of pixel
	 * @param reMin  Real minimum
	 * @param reMax  Real maximum
	 * @param imMin  Imaginary minimum
	 * @param imMax  Imaginary maximum
	 * @param width  of viewer
	 * @param height of viewer
	 * 
	 * @return approximated complex value of pixel
	 */
	private static Complex mapToComplexPlain(int x, int y, int width, int height, double reMin, double reMax,
			double imMin, double imMax) {
		double newX = (x + 0.5) * (reMax - reMin) / width + reMin;
		double newY = (height - 0.5 - y) * (imMax - imMin) / height + imMin;
		return new Complex(newX, newY);
	}
}
