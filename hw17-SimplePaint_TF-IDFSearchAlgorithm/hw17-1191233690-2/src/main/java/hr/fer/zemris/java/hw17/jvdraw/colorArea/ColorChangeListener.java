package hr.fer.zemris.java.hw17.jvdraw.colorArea;

import java.awt.Color;

/**
 * Listener of color change for {@link IColorProvider}.
 * 
 * @author Jelić, Nikola
 */
public interface ColorChangeListener {
	
	/**
	 * This method is called when colorSelected in {@link IColorProvider} has changed.
	 * 
	 * @param source {@link IColorProvider} in which colorSelected has changed.
	 * @param oldColor before change.
	 * @param newColor after change.
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}
