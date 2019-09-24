package hr.fer.zemris.java.hw17.jvdraw.objects;

/**
 * Listener of {@link GeometricalObject}.
 * 
 * @author Jelić, Nikola
 */
public interface GeometricalObjectListener {
	/**
	 * Method is called if object changes.
	 * 
	 * @param o that changes.
	 */
	public void geometricalObjectChanged(GeometricalObject o);
}
