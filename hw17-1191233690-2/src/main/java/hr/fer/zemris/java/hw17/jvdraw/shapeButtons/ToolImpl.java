package hr.fer.zemris.java.hw17.jvdraw.shapeButtons;

import java.awt.Color;
import java.awt.event.MouseEvent;

/**
 * Abstract implementation of the {@link Tool}.
 * 
 * @author Jelić, Nikola
 */
public abstract class ToolImpl implements Tool {

	/**
	 * Starting x-axis.
	 */
	protected Integer startX;
	/**
	 * Starting y-axis.
	 */
	protected Integer startY;
	/**
	 * Temporary x-axis.
	 */
	protected Integer tmpX;
	/**
	 * Temporary y-axis.
	 */
	protected Integer tmpY;
	/**
	 * Ending x-axis.
	 */
	protected Integer endX;
	/**
	 * Ending y-axis.
	 */
	protected Integer endY;
	/**
	 * Foreground color that all geometric objects have.
	 */
	protected Color fgColor;
	
	/**
	 * Constructor that sets data.
	 * 
	 * @param fgColor Foreground color that all geometric objects have.
	 * @param startX Starting x-axis.
	 * @param startY Starting y-axis.
	 * @param endX Ending x-axis.
	 * @param endY Ending y-axis.
	 */
	public ToolImpl(Color fgColor, Integer startX, Integer startY, Integer endX, Integer endY) {
		super();
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.fgColor = fgColor;
	}
	
	/**
	 * Constructor that sets only fgColor.
	 * 
	 * @param fgColor to be set.
	 */
	public ToolImpl(Color fgColor) {
		this(fgColor, null, null, null, null);
	}

	/**
	 * Setter of startX.
	 * 
	 * @param startX Starting x-axis.
	 */
	public void setStartX(Integer startX) {
		this.startX = startX;
	}

	/**
	 * Setter of startY.
	 * 
	 * @param startY Starting y-axis.
	 */
	public void setStartY(Integer startY) {
		this.startY = startY;
	}

	/**
	 * Setter of endX.
	 * 
	 * @param endX Ending x-axis.
	 */
	public void setEndX(Integer endX) {
		this.endX = endX;
	}

	/**
	 * Setter of endY.
	 * 
	 * @param endY Ending y-axis.
	 */
	public void setEndY(Integer endY) {
		this.endY = endY;
	}

	/**
	 * Setter of fgColor.
	 * 
	 * @param fgColor Foreground color that all geometric objects have.
	 */
	public void setFgColor(Color fgColor) {
		this.fgColor = fgColor;
	}

	/**
	 * Setter of startX.
	 * 
	 * @return startX Starting x-axis.
	 */
	public Integer getStartX() {
		return startX;
	}


	/**
	 * Getter of startY.
	 * 
	 * @return startY Starting y-axis.
	 */
	public Integer getStartY() {
		return startY;
	}


	/**
	 * Getter of tmpX.
	 * 
	 * @return tmpX Temporary x-axis.
	 */
	public Integer getTmpX() {
		return tmpX;
	}


	/**
	 * Getter of tmpY.
	 * 
	 * @return tmpY Temporary y-axis.
	 */
	public Integer getTmpY() {
		return tmpY;
	}


	/**
	 * Getter of endX.
	 * 
	 * @return endX Ending x-axis.
	 */
	public Integer getEndX() {
		return endX;
	}


	/**
	 * Getter of endY.
	 * 
	 * @return endY Ending y-axis.
	 */
	public Integer getEndY() {
		return endY;
	}


	/**
	 * Getter of fgColor.
	 * 
	 * @return fgColor Foreground color that all geometric objects have.
	 */
	public Color getFgColor() {
		return fgColor;
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		setStartOrEnd(e);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		tmpX = e.getX();
		tmpY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		tmpX = e.getX();
		tmpY = e.getY();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		tmpX = e.getX();
		tmpY = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		setStartOrEnd(e);
		tmpX = null;
		tmpY = null;
	}

	/**
	 * Sets start or ending point.
	 * 
	 * @param e MouseEvent that has informations.
	 */
	private void setStartOrEnd(MouseEvent e) {
		if (startX == null) {
			startX = e.getX();
			startY = e.getY();
		} else if (endX == null) {
			endX = e.getX();
			endY = e.getY();
			setData();
		}
	}
	
	/**
	 * This method sets data and is called by setStartOrEnd method from {@link ToolImpl}.
	 */
	abstract protected void setData();
}
