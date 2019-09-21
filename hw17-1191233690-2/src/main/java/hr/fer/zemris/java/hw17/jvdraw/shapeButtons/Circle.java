package hr.fer.zemris.java.hw17.jvdraw.shapeButtons;

import java.awt.Graphics2D;
import java.awt.Color;

import hr.fer.zemris.java.hw17.jvdraw.colorArea.IColorProvider;

/**
 * Implementation of circle tool.
 * 
 * @author Jelić, Nikola
 */
public class Circle extends ToolCircle{

	/**
	 * Constructor that sets foreground color.
	 * 
	 * @param fgColor foreground color.
	 * @param startX Start x-axis.
	 * @param startY Start y-axis.
	 * @param endX End x-axis. 
	 * @param endY End y-axis.
	 */
	public Circle(Color fgColor, Integer startX, Integer startY, Integer endX, Integer endY) {
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
	public Circle(IColorProvider fgProvider, Integer startX, Integer startY, Integer endX, Integer endY) {
		super(fgProvider, startX, startY, endX, endY);
	}
	
	/**
	 * Constructor that sets foreground color from provider.
	 * 
	 * @param fgProvider foreground color provider.
	 */
	public Circle(IColorProvider fgProvider) {
		this(fgProvider, null, null, null, null);
	}
	
	@Override
	public void paint(Graphics2D g2d) {
		g2d.setColor(fgColor);
		if (startX != null && endX != null) {
			g2d.drawOval(startX-r, startY-r, 2*r, 2*r);
		} else if (startX != null && tmpX != null) {
			r = (int)Math.sqrt((startX-tmpX)*(startX-tmpX) + (startY-tmpY)*(startY-tmpY));
			g2d.drawOval(startX-r, startY-r, 2*r, 2*r);
		}
	}
	
	@Override
	public String toString() {
		return "Circle ("+startX+","+startY+"), "+r;
	}	
}
