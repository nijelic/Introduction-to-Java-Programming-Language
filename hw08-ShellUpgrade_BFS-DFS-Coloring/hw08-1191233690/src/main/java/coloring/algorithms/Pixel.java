package coloring.algorithms;

import java.util.Objects;

/**
 * This class is used for saving location of pixels
 * */
public class Pixel {

	/**
	 * x-axis
	 * */
	public int x;
	/**
	 * y-axis
	 * */
	public int y;
	
	/**
	 * Constructor sets x and y axes
	 * @param x axis
	 * @param y axis
	 * */
	public Pixel(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
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
		Pixel other = (Pixel) obj;
		return x == other.x && y == other.y;
	}
	
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
	
	
}
