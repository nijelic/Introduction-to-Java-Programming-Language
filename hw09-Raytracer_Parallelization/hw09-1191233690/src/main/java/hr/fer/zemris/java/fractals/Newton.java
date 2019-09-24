package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexRootedPolynomial;

import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.List;

import java.lang.InterruptedException;
import java.util.concurrent.ExecutionException;

import java.util.concurrent.Callable;

/**
 * This class makes one kind of fractal images: fractals derived from
 * Newton-Raphson iteration. Asks user to enter two or more complex numbers.
 * 
 * @author Jelić, Nikola
 */
public class Newton {

	/**
	 * used to check if iteration converged
	 */
	private static final double CONVERGENCE_THRESHOLD = 1E-3;
	/**
	 * used to check which root is the closest
	 */
	private static final double ROOT_THRESHOLD = 0.002;
	/**
	 * maximum number of iterations of one pixel
	 */
	private static final int MAX_ITERATIONS = 16;

	/**
	 * Main method.
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.\n"
				+ "Please enter at least two roots, one root per line. Enter 'done' when done.");

		Scanner sc = new Scanner(System.in);
		int rootNum = 1;
		List<Complex> roots = new ArrayList<>();

		while (true) {
			System.out.print("Root " + rootNum++ + "> ");
			String line = sc.nextLine();
			if (line.equals("done")) {
				break;
			}
			try {
				roots.add(parseComplex(line));
			} catch (RuntimeException e) {
				System.out.println(e.getMessage());
				--rootNum;
			}
		}

		if (roots.size() < 2) {
			System.out.println("You gave too little numbers. Expected: at least two roots.");
			sc.close();
			return;
		}
		Complex[] arrayRoots = new Complex[roots.size()];
		for (int i = 0; i < arrayRoots.length; ++i) {
			arrayRoots[i] = roots.get(i);
		}
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(Complex.ONE, arrayRoots);
		Producer p = new Producer(crp);
		FractalViewer.show(p);
		p.shutdown();
		sc.close();
	}

	/**
	 * This class calls compute method from {@link NewtonRaphson}.
	 */
	public static class DoJob implements Callable<Void> {

		/**
		 * Polynomial which is computed
		 */
		ComplexRootedPolynomial crp;
		/**
		 * Real minimum
		 */
		double reMin;
		/**
		 * Real maximum
		 */
		double reMax;
		/**
		 * Imaginary minimum
		 */
		double imMin;
		/**
		 * Imaginary maximum
		 */
		double imMax;
		/**
		 * width of viewer
		 */
		int width;
		/**
		 * height of viewer
		 */
		int height;
		/**
		 * minimum of y-axis observed segment
		 */
		int yMin;
		/**
		 * maximum of y-axis observed segment
		 */
		int yMax;
		/**
		 * Used for calculating pixel's color
		 */
		int m;
		/**
		 * values for pixels
		 */
		short[] data;
		/**
		 * Used for breaking calculations
		 */
		AtomicBoolean cancel;

		/**
		 * Constructor sets fields.
		 * 
		 * @param crp    Polynomial to compute
		 * @param reMin  Real minimum
		 * @param reMax  Real maximum
		 * @param imMin  Imaginary minimum
		 * @param imMax  Imaginary maximum
		 * @param width  of viewer
		 * @param height of viewer
		 * @param ymin   of observed segment
		 * @param ymax   of observed segment
		 * @param m      used for calculating pixel's color
		 * @param data   which is filled with values for pixels
		 * @param cancel called for breaking
		 */
		public DoJob(ComplexRootedPolynomial crp, double reMin, double reMax, double imMin, double imMax, int width,
				int height, int yMin, int yMax, int m, short[] data, AtomicBoolean cancel) {
			super();
			this.crp = crp;
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.cancel = cancel;
		}

		@Override
		public Void call() {
			NewtonRaphson.compute(crp, CONVERGENCE_THRESHOLD, MAX_ITERATIONS, ROOT_THRESHOLD, reMin, reMax, imMin,
					imMax, width, height, m, yMin, yMax, data, cancel);
			return null;
		}
	}

	/**
	 * Implementation of {@link IFractalProducer}.
	 */
	public static class Producer implements IFractalProducer {

		/**
		 * Polynomial used for calculations
		 */
		private ComplexRootedPolynomial crp;
		/**
		 * Pool of threads, workers.
		 */
		private ExecutorService pool;

		/**
		 * Producer constructor sets crp and initializes pool.
		 * 
		 * @param crp the ComplexRootedPolynomial
		 */
		public Producer(ComplexRootedPolynomial crp) {
			super();
			this.crp = crp;
			pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(),
					new DaemonicThreadFactory());
		}

		/**
		 * Shutdown the pool.
		 */
		public void shutdown() {
			pool.shutdown();
		}

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			System.out.println("Image of fractal will appear shortly. Thank you.");

			System.out.println(reMin + " " + reMax + " " + imMin + " " + imMax + " " + width + " " + height);
			short[] data = new short[width * height];
			int m = crp.toComplexPolynom().order() + 1;
			int yDivided = height / (8 * Runtime.getRuntime().availableProcessors());
			if (yDivided == 0) {
				yDivided = 1;
			}
			final int tracks = height / yDivided;
			pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(),
					new DaemonicThreadFactory());
			List<Future<Void>> results = new ArrayList<>();

			for (int i = 0; i < tracks; i++) {
				int yMin = i * yDivided;
				int yMax = (i + 1) * yDivided - 1;
				if (i == tracks - 1) {
					yMax = height - 1;
				}
				DoJob job = new DoJob(crp, reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data, cancel);
				results.add(pool.submit(job));
			}
			for (Future<Void> job : results) {
				try {
					job.get();
				} catch (InterruptedException | ExecutionException e) {
				}
			}

			System.out.println("Calculation is over. Sending data to GUI!");
			observer.acceptResult(data, (short) m, requestNo);
		}
	}

	/**
	 * ThreadFactory of daemon Threads.
	 */
	public static class DaemonicThreadFactory implements ThreadFactory {

		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r);
			t.setDaemon(true);
			return t;
		}
	}

	/**
	 * Parses input of complex number. Expects maximum one real and maximum one
	 * imaginary part, respectively.
	 * 
	 * @param line of input
	 */
	public static Complex parseComplex(String line) {
		char[] data = line.replaceAll("\\s+", "").toCharArray();
		int index = 0;
		StringBuilder sb = new StringBuilder();
		boolean point = false;

		// will count how many imaginary parts and real parts are in line
		int imaginary = 0;
		int real = 0;

		// used for values
		double re = 0;
		double im = 0;

		while (index < data.length) {
			if (Character.isDigit(data[index])) {
				sb.append(data[index++]);
			} else if (data[index] == 'i') {
				++imaginary;
				++index;
			} else if (data[index] == '.') {
				if (point) {
					throw new RuntimeException("Unparsable number: double decimal point.");
				}
				point = true;
				sb.append(data[index++]);
			} else if (data[index] != '-' && data[index] != '+') {
				throw new RuntimeException("Wrong input: available inputs are 'done' or real than imaginary part.");
			}

			if (index == data.length || data[index] == '-' || data[index] == '+') {
				if (imaginary > 0) {
					// more than one imaginary parts in input
					if (imaginary > 1) {
						throw new RuntimeException(
								"Unparsable comlex number: expected maximum one real and maximum one imaginary part, respectively.");
					}
					if (sb.length() == 0
							|| (sb.length() == 1 && (sb.toString().equals("+") || sb.toString().equals("-")))) {
						sb.append("1");
					}
					try {
						im = Double.parseDouble(sb.toString());
					} catch (NumberFormatException e) {
						throw new RuntimeException("Unparsable number: " + sb.toString());
					}
					++imaginary;
				} else {
					// more than one real parts in input
					if (real > 0) {
						throw new RuntimeException(
								"Unparsable comlex number: expected maximum one real and maximum one imaginary part, respectively.");
					}
					if (sb.length() > 0) {
						try {
							re = Double.parseDouble(sb.toString());
						} catch (NumberFormatException e) {
							throw new RuntimeException("Unparsable number: " + sb.toString());
						}
						++real;
					}
				}
				sb = new StringBuilder();
				point = false;

				if (index < data.length) {
					sb.append(data[index++]);
				}
			}
		}
		return new Complex(re, im);
	}

}
