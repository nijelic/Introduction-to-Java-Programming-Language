package hr.fer.zemris.java.raytracer.model;

import static java.lang.Math.*;
import hr.fer.zemris.java.raytracer.model.RayIntersection;

/**
 * This class models sphere by extending {@link GraphicalObject}.
 * 
 * @author Jelić, Nikola
 */
public class Sphere extends GraphicalObject {

	/**
	 * Center of sphere.
	 */
	private Point3D center;
	/**
	 * Radius of sphere.
	 */
	private double radius;
	/**
	 * Coefficient of red color for diffuse component.
	 */
	private double kdr;
	/**
	 * Coefficient of green color for diffuse component.
	 */
	private double kdg;
	/**
	 * Coefficient of blue color for diffuse component.
	 */
	private double kdb;
	/**
	 * Coefficient of red color for reflective component.
	 */
	private double krr;
	/**
	 * Coefficient of green color for reflective component.
	 */
	private double krg;
	/**
	 * Coefficient of blue color for reflective component.
	 */
	private double krb;
	/**
	 * Coefficient of n (power) for reflective component.
	 */
	private double krn;

	/**
	 * Constructor of {@link Sphere} sets private fields.
	 * 
	 * @param center center of {@link Sphere}
	 * @param radius radius of {@link Sphere}
	 * @param kdr    Coefficient of red color for diffuse component.
	 * @param kdg    Coefficient of green color for diffuse component.
	 * @param kdb    Coefficient of blue color for diffuse component.
	 * @param krr    Coefficient of red color for reflective component.
	 * @param krg    Coefficient of green color for reflective component.
	 * @param krb    Coefficient of blue color for reflective component.
	 * @param krn    Coefficient of n (power) for reflective component.
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		super();
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	/**
	 * Returns closest {@link RayIntersection} between ray and sphere. Returns null
	 * if intersection doesn't exist.
	 * 
	 * @param ray ray that intersect sphere.
	 * @return closest intersection or null if doesn't exist.
	 */
	public RayIntersection findClosestRayIntersection(Ray ray) {
		double a = pow(ray.direction.x, 2) + pow(ray.direction.y, 2) + pow(ray.direction.z, 2);
		double b = 2 * ((ray.start.x - center.x) * ray.direction.x + (ray.start.y - center.y) * ray.direction.y
				+ (ray.start.z - center.z) * ray.direction.z);
		double c = pow(ray.start.x - center.x, 2) + pow(ray.start.y - center.y, 2) + pow(ray.start.z - center.z, 2)
				- pow(radius, 2);
		double D = pow(b, 2) - 4 * a * c;
		if (D < 0) {
			return null;
		}
		double lambda1 = (-b + sqrt(D)) / (2 * a);
		double lambda2 = (-b - sqrt(D)) / (2 * a);
		double lambda = min(lambda1, lambda2);
		Point3D point = new Point3D(ray.start.x + lambda * ray.direction.x, ray.start.y + lambda * ray.direction.y,
				ray.start.z + lambda * ray.direction.z);

		return new SphereRayIntersection(point, point.sub(ray.start).norm(), true, kdr, kdg, kdb, krr, krg, krb, krn,
				point.sub(center).modifyNormalize());
	}

	/**
	 * This class extends {@link RayIntersection}.
	 */
	private static class SphereRayIntersection extends RayIntersection {

		/**
		 * Coefficient of red color for diffuse component.
		 */
		private double kdr;
		/**
		 * Coefficient of green color for diffuse component.
		 */
		private double kdg;
		/**
		 * Coefficient of blue color for diffuse component.
		 */
		private double kdb;
		/**
		 * Coefficient of red color for reflective component.
		 */
		private double krr;
		/**
		 * Coefficient of green color for reflective component.
		 */
		private double krg;
		/**
		 * Coefficient of blue color for reflective component.
		 */
		private double krb;
		/**
		 * Coefficient of n (power) for reflective component.
		 */
		private double krn;
		/**
		 * Normal of sphere at intersection.
		 */
		private Point3D normal;

		/**
		 * Constructor sets all private fields
		 * 
		 * @param point    Point of intersection.
		 * @param distance Distance between intersection and start point of ray.
		 * @param kdr      Coefficient of red color for diffuse component.
		 * @param kdg      Coefficient of green color for diffuse component.
		 * @param kdb      Coefficient of blue color for diffuse component.
		 * @param krr      Coefficient of red color for reflective component.
		 * @param krg      Coefficient of green color for reflective component.
		 * @param krb      Coefficient of blue color for reflective component.
		 * @param krn      Coefficient of n (power) for reflective component.
		 * @param normal   Normal of sphere at intersection.
		 */
		public SphereRayIntersection(Point3D point, double distance, boolean outer, double kdr, double kdg, double kdb,
				double krr, double krg, double krb, double krn, Point3D normal) {
			super(point, distance, outer);
			this.kdr = kdr;
			this.kdg = kdg;
			this.kdb = kdb;
			this.krr = krr;
			this.krg = krg;
			this.krb = krb;
			this.krn = krn;
			this.normal = normal;
		}

		@Override
		public double getKdb() {
			return kdb;
		}

		@Override
		public double getKdg() {
			return kdg;
		}

		@Override
		public double getKdr() {
			return kdr;
		}

		@Override
		public double getKrb() {
			return krb;
		}

		@Override
		public double getKrg() {
			return krg;
		}

		@Override
		public double getKrn() {
			return krn;
		}

		@Override
		public double getKrr() {
			return krr;
		}

		@Override
		public Point3D getNormal() {
			return normal;
		}

	}
}
