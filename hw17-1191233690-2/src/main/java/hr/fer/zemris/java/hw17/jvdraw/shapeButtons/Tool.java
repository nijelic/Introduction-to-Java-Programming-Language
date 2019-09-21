package hr.fer.zemris.java.hw17.jvdraw.shapeButtons;

import java.awt.event.MouseEvent;
import java.awt.Graphics2D;

/**
 * This interface is used to allow user to draw geometric objects.
 * 
 * @author Jelić, Nikola
 */
public interface Tool {
	/**
	 * Record state of pressed mouse.
	 * 
	 * @param e MouseEvent.
	 */
	public void mousePressed(MouseEvent e);
	
	/**
	 * Record state of released mouse.
	 * 
	 * @param e MouseEvent.
	 */
	public void mouseReleased(MouseEvent e);

	/**
	 * Record state of clicked mouse.
	 * 
	 * @param e MouseEvent.
	 */
	public void mouseClicked(MouseEvent e);

	/**
	 * Record state of moved mouse.
	 * 
	 * @param e MouseEvent.
	 */
	public void mouseMoved(MouseEvent e);

	/**
	 * Record state of dragged mouse.
	 * 
	 * @param e MouseEvent.
	 */
	public void mouseDragged(MouseEvent e);

	/**
	 * Paints object to the graphics.
	 * 
	 * @param g2d graphics where object should be painted.
	 */
	public void paint(Graphics2D g2d);
}
