package hr.fer.zemris.java.gui.charts;

import java.util.ArrayList;
import java.util.List;

/**
 * This class saves data used for representing barChart.
 * 
 * @author Jelić, Nikola
 */
public class BarChart {

	/**
	 * Data as (x,y) pairs.
	 */
	private List<XYValue> data = new ArrayList<XYValue>();

	/**
	 * X axis label.
	 */
	private String xName;
	/**
	 * Y axis label.
	 */
	private String yName;
	/**
	 * Minimal y value.
	 */
	private int yMin;
	/**
	 * Maximal y value.
	 */
	private int yMax;
	/**
	 * Gap between y values on axis.
	 */
	private int ySpace;

	/**
	 * Constructor sets values and validates data.
	 * 
	 * @param data   Data as (x,y) pairs.
	 * @param xName  X axis label.
	 * @param yName  Y axis label.
	 * @param yMin   Minimal y value.
	 * @param yMax   Maximal y value.
	 * @param ySpace Gap between y values on axis.
	 * 
	 * @throws IllegalArgumentException if some of y value is not valid.
	 */
	public BarChart(List<XYValue> data, String xName, String yName, int yMin, int yMax, int ySpace) {
		super();
		this.data = data;
		this.xName = xName;
		this.yName = yName;
		this.yMin = yMin;
		this.yMax = yMax;
		this.ySpace = ySpace;

		if (yMin < 0) {
			throw new IllegalArgumentException("yMin is less than zero.");
		}
		if (yMin >= yMax) {
			throw new IllegalArgumentException("yMin is not less than yMax.");
		}
		if ((yMax - yMin) % ySpace != 0) {
			this.yMax = ((yMax - yMin) / ySpace + 1) * ySpace;
		}
		for (XYValue d : data) {
			if (d.getY() < yMin) {
				throw new IllegalArgumentException(d.getY() + " is less than yMin.");
			}
		}
	}

	/**
	 * Getter of data.
	 * 
	 * @return data List of {@link XYValue}.
	 */
	public List<XYValue> getData() {
		return data;
	}

	/**
	 * Getter of xName.
	 * 
	 * @return xName as String.
	 */
	public String getxName() {
		return xName;
	}

	/**
	 * Getter of yName.
	 * 
	 * @return yName as String.
	 */
	public String getyName() {
		return yName;
	}

	/**
	 * Getter of yMin.
	 * 
	 * @return yMin as int.
	 */
	public int getyMin() {
		return yMin;
	}

	/**
	 * Getter of yMax.
	 * 
	 * @return yMax as int.
	 */
	public int getyMax() {
		return yMax;
	}

	/**
	 * Getter of ySpace.
	 * 
	 * @return ySpace as int.
	 */
	public int getySpace() {
		return ySpace;
	}
}
