package hr.fer.zemris.java.hw17.jvdraw.objects;

/**
 * Model of geometric object.
 * 
 * @author Jelić, Nikola
 */
public abstract class GeometricalObject {

	/**
	 * Accepts visitor.
	 * 
	 * @param v visitor to be accepted.
	 */
	public abstract void accept(GeometricalObjectVisitor v);

	/**
	 * Creates object's editor.
	 * 
	 * @return editor of object.
	 */
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();

	/**
	 * Adds listener to object.
	 * 
	 * @param l listener to be added.
	 */
	public abstract void addGeometricalObjectListener(GeometricalObjectListener l);

	/**
	 * Removes listener to object.
	 * 
	 * @param l listener to be removed.
	 */
	public abstract void removeGeometricalObjectListener(GeometricalObjectListener l);
}
