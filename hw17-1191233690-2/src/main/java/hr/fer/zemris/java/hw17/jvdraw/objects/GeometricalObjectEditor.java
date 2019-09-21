package hr.fer.zemris.java.hw17.jvdraw.objects;

import javax.swing.JPanel;

/**
 * Abstract editor. Will be used while editing geometric objects.
 * 
 * @author Jelić, Nikola
 */
public abstract class GeometricalObjectEditor extends JPanel {
	
	/**
	 * Serial version identifier.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Checks if editing is validate.
	 */
	public abstract void checkEditing();

	/**
	 * Accepts editing.
	 */
	public abstract void acceptEditing();
}
