package hr.fer.zemris.java.hw17.jvdraw.shapeButtons;

import java.awt.Graphics2D;
import java.awt.Color;

import hr.fer.zemris.java.hw17.jvdraw.colorArea.IColorProvider;

/**
 * Implementation of line tool.
 * 
 * @author Jelić, Nikola
 */
public class Line extends ToolImpl {

	/**
	 * Constructor that sets foreground color.
	 * 
	 * @param fgColor foreground color.
	 * @param startX Start x-axis.
	 * @param startY Start y-axis.
	 * @param endX End x-axis. 
	 * @param endY End y-axis.
	 */
	public Line(Color fgColor, Integer startX, Integer startY, Integer endX, Integer endY) {
		super(fgColor, startX, startY, endX, endY);
	}
	
	/**
	 * Constructor that sets foreground color from provider.
	 * 
	 * @param fgProvider foreground color provider.
	 * @param startX Start x-axis.
	 * @param startY Start y-axis.
	 * @param endX End x-axis. 
	 * @param endY End y-axis. 
	 */
	public Line(IColorProvider fgProvider, Integer startX, Integer startY, Integer endX, Integer endY) {
		super(fgProvider.getCurrentColor(), startX, startY, endX, endY);
	}

	/**
	 * Constructor that sets foreground color from provider.
	 * 
	 * @param fgProvider foreground color provider.
	 */
	public Line(IColorProvider fgProvider) {
		this(fgProvider, null, null, null, null);
	}
	
	@Override
	public void paint(Graphics2D g2d) {
		g2d.setColor(fgColor);
		if (startX != null && endX != null) {
			g2d.drawLine(startX, startY, endX, endY);
		} else if (startX != null && tmpX != null) {
			g2d.drawLine(startX, startY, tmpX, tmpY);
		}
	}

	@Override
	public String toString() {
		return "Line ("+startX+","+startY+")-("+endX+","+endY+")";
	}

	@Override
	protected void setData() {
	}
}
