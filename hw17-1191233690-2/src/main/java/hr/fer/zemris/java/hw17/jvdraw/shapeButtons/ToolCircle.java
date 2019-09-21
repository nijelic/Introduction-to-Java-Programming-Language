package hr.fer.zemris.java.hw17.jvdraw.shapeButtons;

import java.awt.Color;
import java.awt.Graphics2D;

import hr.fer.zemris.java.hw17.jvdraw.colorArea.IColorProvider;

/**
 * Abstract implementation of ToolImpl used by circles.
 * 
 * @author Jelić, Nikola
 */
public abstract class ToolCircle extends ToolImpl{

	/**
	 * Radius of circle.
	 */
	protected Integer r;
	
	/**
	 * Constructor that sets color, start and end point.
	 * 
	 * @param fgColor {@link Color} of foreground.
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 */
	public ToolCircle(Color fgColor, Integer startX, Integer startY, Integer endX, Integer endY) {
		super(fgColor, startX, startY, endX, endY);
		setData();
	}
	
	/**
	 * Constructor that sets color provider, start and end point.
	 * 
	 * @param fgColor {@link IColorProvider} of foreground.
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 */
	public ToolCircle(IColorProvider fgProvider, Integer startX, Integer startY, Integer endX, Integer endY) {
		super(fgProvider.getCurrentColor(), startX, startY, endX, endY);
	}
	
	/**
	 * Getter of radius.
	 * 
	 * @return radius.
	 */
	public Integer getR() {
		return r;
	}

	/**
	 * Setter of radius.
	 * 
	 * @param r radius of circle.
	 */
	public void setR(int r) {
		this.r = r;
	}
	
	@Override
	public void paint(Graphics2D g2d) {	
	}
	
	@Override
	protected void setData() {
		r = (int) Math.sqrt((startX - endX) * (startX - endX) + (startY - endY) * (startY - endY));
	}
}
