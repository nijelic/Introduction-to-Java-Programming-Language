package hr.fer.zemris.math;

/**
 * This class models 3D vectors.
 * 
 * @author Jelić, Nikola
 */
public class Vector3 {

	/**
	 * x axis
	 */
	private double x;
	/**
	 * y axis
	 */
	private double y;
	/**
	 * z axis
	 */
	private double z;

	/**
	 * Constructor sets x, y and z values.
	 * 
	 * @param x value
	 * @param y value
	 * @param z value
	 */
	public Vector3(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Returns norm of vector.
	 * 
	 * @return norm of vector
	 */
	public double norm() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * Returns normalized new vector.
	 * 
	 * @return new normalized vector.
	 */
	public Vector3 normalized() {
		return new Vector3(x / this.norm(), y / this.norm(), z / this.norm());
	}

	/**
	 * Returns new vector, the sum of vectors
	 * 
	 * @param other vector to be added
	 * @return new vector, the sum of vectors
	 */
	public Vector3 add(Vector3 other) {
		return new Vector3(x + other.getX(), y + other.getY(), z + other.getZ());
	}

	/**
	 * Returns new vector, the sub of vectors
	 * 
	 * @param other vector to be subtracted of this vector
	 * @return new vector, the sub of vectors
	 */
	public Vector3 sub(Vector3 other) {
		return new Vector3(x - other.getX(), y - other.getY(), z - other.getZ());
	}

	/**
	 * Returns dot product of vectors
	 * 
	 * @param other vector to be multiplied
	 * @return dot product
	 */
	public double dot(Vector3 other) {
		return x * other.getX() + y * other.getY() + z * other.getZ();
	}

	/**
	 * Returns cross product of vectors as new vector
	 * 
	 * @param other vector to be multiplied
	 * @return cross product as new vector
	 */
	public Vector3 cross(Vector3 other) {
		return new Vector3(y * other.getZ() - z * other.getY(), z * other.getX() - x * other.getZ(),
				x * other.getY() - y * other.getX());
	}

	/**
	 * Returns scaled vector
	 * 
	 * @param multiplier
	 * @return scaled vector
	 */
	public Vector3 scale(double s) {
		return new Vector3(s * x, s * y, s * z);
	}

	/**
	 * Returns cos angle between vectors.
	 * 
	 * @param other vector
	 * @return cos angle between vectors
	 */
	public double cosAngle(Vector3 other) {
		return dot(other) / (other.norm() * norm());
	}

	/**
	 * Getter of x axis
	 * 
	 * @return x axis
	 */
	public double getX() {
		return x;
	}

	/**
	 * Getter of y axis
	 * 
	 * @return y axis
	 */
	public double getY() {
		return y;
	}

	/**
	 * Getter of z axis
	 * 
	 * @return z axis
	 */
	public double getZ() {
		return z;
	}

	/**
	 * Returns vector as array.
	 * 
	 * @return vector as array
	 */
	public double[] toArray() {
		return new double[] { x, y, z };
	}

	@Override
	public String toString() {
		return "(" + String.format("%.6f", x) + ", " + String.format("%.6f", y) + ", " + String.format("%.6f", z) + ")";
	}

}
