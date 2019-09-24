package hr.fer.zemris.java.hw17.jvdraw.shapeButtons;

import java.awt.Color;
import java.awt.Graphics2D;

import hr.fer.zemris.java.hw17.jvdraw.colorArea.IColorProvider;

/**
 * Implementation of filled circle tool.
 * 
 * @author Jelić, Nikola
 */
public class FilledCircle extends ToolCircle {

	/**
	 * Background color.
	 */
	private Color bgColor;
	
	/**
	 * Constructor that sets foreground and background color.
	 * 
	 * @param fgColor foreground color.
	 * @param bgColor background color.
	 * @param startX Start x-axis.
	 * @param startY Start y-axis.
	 * @param endX End x-axis. 
	 * @param endY End y-axis.
	 */
	public FilledCircle(Color fgColor, Color bgColor, Integer startX, Integer startY,
			Integer endX, Integer endY) {
		super(fgColor, startX, startY, endX, endY);
		this.bgColor = bgColor;
	}
	
	/**
	 * Constructor that sets foreground color and background color from provider.
	 * 
	 * @param bgProvider background color provider.
	 * @param fgProvider foreground color provider.
	 * @param startX Start x-axis.
	 * @param startY Start y-axis.
	 * @param endX End x-axis. 
	 * @param endY End y-axis. 
	 */
	public FilledCircle(IColorProvider fgProvider, IColorProvider bgProvider, Integer startX, Integer startY,
			Integer endX, Integer endY) {
		super(fgProvider, startX, startY, endX, endY);
		this.bgColor = bgProvider.getCurrentColor();
	}

	/**
	 * Constructor that sets foreground color and background color from providers. 
	 * 
	 * @param fgProvider foreground color provider.
	 * @param bgProvider background color provider.
	 */
	public FilledCircle(IColorProvider fgProvider, IColorProvider bgProvider) {
		this(fgProvider, bgProvider, null, null, null, null);
	}

	/**
	 * Background getter.
	 * 
	 * @return background color.
	 */
	public Color getBgColor() {
		return bgColor;
	}

	/**
	 * Background setter.
	 * 
	 * @param bgColor background color.
	 */
	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
	}


	@Override
	public void paint(Graphics2D g2d) {
		g2d.setColor(bgColor);
		if (startX != null && endX != null) {
			
			g2d.fillOval(startX - r, startY - r, 2 * r, 2 * r);
			g2d.setColor(fgColor);
			g2d.drawOval(startX - r, startY - r, 2 * r, 2 * r);
		} else if (startX != null && tmpX != null) {
			
			r = (int) Math.sqrt((startX - tmpX) * (startX - tmpX) + (startY - tmpY) * (startY - tmpY));
			g2d.fillOval(startX - r, startY - r, 2 * r, 2 * r);
			g2d.setColor(fgColor);
			g2d.drawOval(startX - r, startY - r, 2 * r, 2 * r);
		}
	}

	@Override
	public String toString() {
		return "Filled circle ("+startX+","+startY+"), "+r+", #"+Integer.toHexString(bgColor.getRGB()).substring(2);
	}
	
}