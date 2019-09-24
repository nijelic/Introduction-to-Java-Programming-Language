package hr.fer.zemris.java.hw17.jvdraw.colorArea;

import java.awt.Color;

/**
 * Interface of color provider.
 * 
 * @author Jelić, Nikola
 */
public interface IColorProvider {
	/**
	 * CurrentColor getter.
	 * 
	 * @return currentColor.
	 */
	public Color getCurrentColor();

	/**
	 * Adds {@link ColorChangeListener} to collection.
	 * 
	 * @param l the listener.
	 */
	public void addColorChangeListener(ColorChangeListener l);

	/**
	 * REmoves {@link ColorChangeListener} from collection.
	 * 
	 * @param l the listener.
	 */
	public void removeColorChangeListener(ColorChangeListener l);
}
