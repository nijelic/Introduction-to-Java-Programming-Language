package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;
import hr.fer.zemris.java.raytracer.model.Point3D;

import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import java.util.List;

/**
 * Example of casting rays in the scene.
 */
public class RayCaster {

	/**
	 * Epsilon constant
	 */
	private static final double EPSILON = 1e-6;

	/**
	 * Main method.
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);
	}

	/**
	 * This method returns {@link IRayTracerProducer}. Goes through pixels and calls
	 * function for detail computation.
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
				short[] rgb = new short[3];
				int offset = 0;
				// iteration through pixels
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(x / (width - 1.) * horizontal)
								.sub(yAxis.scalarMultiply(y / (height - 1.) * vertical)));
						Ray ray = Ray.fromPoints(eye, screenPoint);
						tracer(scene, ray, rgb);
						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
						offset++;
					}
				}

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
		// i.e. ambient component
		rgb[0] = 0;
		rgb[1] = 0;
		rgb[2] = 0;
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
