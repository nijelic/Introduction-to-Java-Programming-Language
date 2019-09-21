package hr.fer.zemris.java.hw17.jvdraw.drawingModel;

import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;

/**
 * Interface for drawing model. Idea is to have all geometric objects saved in collection and draw this model.
 * 
 * @author Jelić, Nikola
 */
public interface DrawingModel {
	/**
	 * Gets number of {@link GeometricalObject}s.
	 * 
	 * @return size.
	 */
	public int getSize();

	/**
	 * Returns object at index.
	 * 
	 * @param index used to find specific object.
	 * @return {@link GeometricalObject} at index position.
	 */
	public GeometricalObject getObject(int index);

	/**
	 * Adds {@link GeometricalObject} to the end of collection.
	 * 
	 * @param object to be added.
	 */
	public void add(GeometricalObject object);

	/**
	 * Removes {@link GeometricalObject} from collection.
	 * 
	 * @param object to be removed.
	 */
	public void remove(GeometricalObject object);

	/**
	 * Moves object by some offset.
	 * 
	 * @param object to be moved.
	 * @param offset of the moving object.
	 */
	public void changeOrder(GeometricalObject object, int offset);

	/**
	 * Returns index position of object.
	 * 
	 * @param object which index you want to find out.
	 * @return index of given object.
	 */
	public int indexOf(GeometricalObject object);

	/**
	 * Clears all objects from collection.
	 */
	public void clear();

	/**
	 * Clears modified flag.
	 */
	public void clearModifiedFlag();

	/**
	 * Returns whether is model modified.
	 * 
	 * @return boolean.
	 */
	public boolean isModified();

	/**
	 * Adds DrawingModelListener.
	 * 
	 * @param l listener to be added.
	 */
	public void addDrawingModelListener(DrawingModelListener l);

	/**
	 * Removes DrawingModelListener.
	 * 
	 * @param l listener to be removed.
	 */
	public void removeDrawingModelListener(DrawingModelListener l);
}
