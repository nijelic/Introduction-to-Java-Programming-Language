package hr.fer.zemris.java.gui.charts;

/**
 * This class is used for saving pairs (x,y). Read-only.
 * 
 * @author Jelić, Nikola
 * */
public class XYValue {

	/**
	 * x value
	 * */
	private int x;
	/**
	 * y value
	 * */
	private int y;

	/**
	 * Constructor sets all prive fields.
	 * 
	 * @param x the x value
	 * @param y the y value
	 * */
	public XYValue(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * Getter of x value.
	 * @return x the x value
	 * */
	public int getX() {
		return x;
	}

	/**
	 * Getter of y value.
	 * @return y the y value
	 * */
	public int getY() {
		return y;
	}

}
