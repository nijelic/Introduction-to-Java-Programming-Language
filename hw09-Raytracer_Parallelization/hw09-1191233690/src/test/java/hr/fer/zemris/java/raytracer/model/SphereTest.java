package hr.fer.zemris.java.raytracer.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import hr.fer.zemris.java.raytracer.model.Ray;
class SphereTest {

	@Test
	void testSphere() {
		Sphere s = new Sphere(new Point3D(0,0,0), 1, 0, 0, 0, 0, 0, 0, 0);
		Ray r = new Ray(new Point3D(10, 0, 0), new Point3D(-1,0,0));
		Ray r1 = new Ray(new Point3D(10, 0, 0), new Point3D(0,1,0));
		assertEquals(9., s.findClosestRayIntersection(r).getDistance());
		assertEquals(null, s.findClosestRayIntersection(r1));
	}
}
