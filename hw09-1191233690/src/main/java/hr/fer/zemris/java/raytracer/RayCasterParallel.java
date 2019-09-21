package hr.fer.zemris.java.raytracer;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

/**
 * Example of parallel version of {@link RayCaster}.
 * */
public class RayCasterParallel {

	/**
	 * Epsilon constant.
	 */
	private static final double EPSILON = 1e-6;

	/**
	 * Main method runs show.
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);
	}

	/**
	 * Job extends {@link RecursiveAction} and is used for recursive 'divide et
	 * impera' calculation.
	 */
	public static class Job extends RecursiveAction {
		private static final long serialVersionUID = 1L;

		/**
		 * Point at the eye of viewer.
		 */
		private Point3D eye;
		/**
		 * Orientation of the x-axis.
		 */
		private Point3D xAxis;
		/**
		 * Orientation of the y-axis.
		 */
		private Point3D yAxis;
		/**
		 * Orientation of the z-axis.
		 */
		private Point3D zAxis;
		/**
		 * Location of upper left screen corner.
		 */
		private Point3D screenCorner;
		/**
		 * Scene of space.
		 */
		private Scene scene;
		/**
		 * Horizontal length of screen.
		 */
		private double horizontal;
		/**
		 * Vertical length of screen.
		 */
		private double vertical;
		/**
		 * Horizontal number of pixels of screen.
		 */
		private int width;
		/**
		 * Vertical number of pixels of screen.
		 */
		private int height;
		/**
		 * Variable that is set if calculation needs to be canceled.
		 */
		private AtomicBoolean cancel;
		/**
		 * Sets where calculation starts.
		 */
		private int yMin;
		/**
		 * Sets where calculation ends.
		 */
		private int yMax;
		/**
		 * Array that saves red color of pixel.
		 */
		private short[] red;
		/**
		 * Array that saves green color of pixel.
		 */
		private short[] green;
		/**
		 * Array that saves blue color of pixel.
		 */
		private short[] blue;
		/**
		 * Threshold for calling computeDirect method.
		 */
		static final int threshold = 16;

		/**
		 * Constructor sets all private fields.
		 * 
		 * @param eye          Point at the eye of viewer.
		 * @param xAxis        Orientation of the x-axis.
		 * @param yAxis        Orientation of the y-axis.
		 * @param zAxis        Orientation of the z-axis.
		 * @param screenCorner Location of upper left screen corner.
		 * @param scene        Scene of space.
		 * @param horizontal   Horizontal length of screen.
		 * @param vertical     Vertical length of screen.
		 * @param width        Horizontal number of pixels of screen.
		 * @param height       Vertical number of pixels of screen.
		 * @param cancel       Variable that is set if calculation needs to be canceled.
		 * @param yMin         Sets where calculation starts.
		 * @param yMax         Sets where calculation ends.
		 * @param red          Array that saves red color of pixel.
		 * @param green        Array that saves green color of pixel.
		 * @param blue         Array that saves blue color of pixel.
		 */
		public Job(Point3D eye, Point3D xAxis, Point3D yAxis, Point3D zAxis, Point3D screenCorner, Scene scene,
				double horizontal, double vertical, int width, int height, int yMin, int yMax, short[] red,
				short[] green, short[] blue, AtomicBoolean cancel) {
			super();
			this.eye = eye;
			this.xAxis = xAxis;
			this.yAxis = yAxis;
			this.zAxis = zAxis;
			this.screenCorner = screenCorner;
			this.scene = scene;
			this.horizontal = horizontal;
			this.vertical = vertical;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.cancel = cancel;
		}

		@Override
		public void compute() {
			if (yMax - yMin + 1 <= threshold) {
				computeDirect();
				return;
			}
			invokeAll(
					new Job(eye, xAxis, yAxis, zAxis, screenCorner, scene, horizontal, vertical, width, height, yMin,
							yMin + (yMax - yMin) / 2, red, green, blue, cancel),
					new Job(eye, xAxis, yAxis, zAxis, screenCorner, scene, horizontal, vertical, width, height,
							yMin + (yMax - yMin) / 2 + 1, yMax, red, green, blue, cancel));
		}

		/**
		 * Final main computation which calculates value of each pixel in segment.
		 */
		private void computeDirect() {
			short[] rgb = new short[3];

			for (int y = yMin; y <= yMax; y++) {
				if (cancel.get()) {
					break;
				}
				for (int x = 0; x < width; x++) {
					Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(x / (width - 1.) * horizontal)
							.sub(yAxis.scalarMultiply(y / (height - 1.) * vertical)));
					Ray ray = Ray.fromPoints(eye, screenPoint);
					tracer(scene, ray, rgb);
					red[y * width + x] = rgb[0] > 255 ? 255 : rgb[0];
					green[y * width + x] = rgb[1] > 255 ? 255 : rgb[1];
					blue[y * width + x] = rgb[2] > 255 ? 255 : rgb[2];
				}
			}
		}
	}

	/**
	 * This method returns {@link IRayTracerProducer}. Makes pool and invokes
	 * {@link Job} which calculates recursively.
	 * 
	 * @return {@link IRayTracerProducer}
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {

			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical,
					int width, int height, long requestNo, IRayTracerResultObserver observer, AtomicBoolean cancel) {

				System.out.println("Započinjem izračune...");

				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];
				Point3D zAxis = view.sub(eye).normalize();
				Point3D yAxis = viewUp.sub(zAxis.scalarMultiply(viewUp.scalarProduct(zAxis))).normalize();
				Point3D xAxis = zAxis.vectorProduct(yAxis);
				Point3D screenCorner = view
						.add(xAxis.scalarMultiply(-horizontal / 2).add(yAxis.scalarMultiply(vertical / 2)));

				Scene scene = RayTracerViewer.createPredefinedScene();

				ForkJoinPool pool = new ForkJoinPool();
				pool.invoke(new Job(eye, xAxis, yAxis, zAxis, screenCorner, scene, horizontal, vertical, width, height,
						0, height - 1, red, green, blue, cancel));
				pool.shutdown();

				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
		};
	}

	/**
	 * This method determines color of pixel.
	 * 
	 * @param scene scene of space
	 * @param ray   ray from eye through pixel
	 * @param rgb   array where color is saved
	 */
	protected static void tracer(Scene scene, Ray ray, short[] rgb) {
		rgb[0] = 0;
		rgb[1] = 0;
		rgb[2] = 0;
		RayIntersection closest = findClosestIntersection(scene, ray);
		if (closest == null) {
			return;
		}

		determineColorFor(closest, rgb, scene, ray);
	}

	/**
	 * This method finds closest object in scene and returns intersection.
	 * 
	 * @param scene scene of space
	 * @param ray   from eye of viewer
	 * @return {@link RayIntersection} intersection of ray and object
	 */
	protected static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
		List<GraphicalObject> objects = scene.getObjects();
		RayIntersection intersection = null;
		boolean first = true;
		double min = 0;
		for (int i = 0, len = objects.size(); i < len; ++i) {
			RayIntersection tmp = objects.get(i).findClosestRayIntersection(ray);
			if (tmp != null) {
				if (first) {
					min = tmp.getDistance() + EPSILON;
					intersection = tmp;
					first = false;
				} else if (tmp.getDistance() < min + EPSILON) {
					intersection = tmp;
					min = tmp.getDistance();
				}
			}

		}
		return intersection;
	}

	/**
	 * This methods determines color at intersection S.
	 * 
	 * @param S     the intersection
	 * @param rgb   array of color where color is saved
	 * @param scene scene of space
	 * @param ray
	 */
	protected static void determineColorFor(RayIntersection S, short[] rgb, Scene scene, Ray ray) {

		List<LightSource> lights = scene.getLights();

		for (int i = 0, len = lights.size(); i < len; ++i) {
			LightSource light = lights.get(i);
			Point3D sPoint = S.getPoint();
			Ray rayFromLight = Ray.fromPoints(light.getPoint(), sPoint);
			RayIntersection tmp = findClosestIntersection(scene, rayFromLight);
			if (tmp != null && pointsEquals(tmp.getPoint(), sPoint)) {
				Point3D l = rayFromLight.direction.negate();
				Point3D n = S.getNormal();
				Point3D r = n.scalarMultiply(2 * n.scalarProduct(l)).sub(l);
				double rv = r.scalarProduct(ray.direction.negate());
				double product = l.scalarProduct(n);

				rgb[0] += light.getR() * (S.getKdr() * product + S.getKrr() * (Math.pow(rv, S.getKrn())));
				rgb[1] += light.getG() * (S.getKdg() * product + S.getKrr() * (Math.pow(rv, S.getKrn())));
				rgb[2] += light.getB() * (S.getKdb() * product + S.getKrr() * (Math.pow(rv, S.getKrn())));
			}
		}
	}

	/**
	 * Compares two points.
	 * 
	 * @param a first point
	 * @param b second point
	 * @return true if points are equals, else false
	 */
	protected static boolean pointsEquals(Point3D a, Point3D b) {
		if (a == null || b == null) {
			return false;
		}
		return Math.abs(a.x - b.x) < EPSILON && Math.abs(a.y - b.y) < EPSILON && Math.abs(a.z - b.z) < EPSILON;
	}
}
