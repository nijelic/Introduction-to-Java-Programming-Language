package hr.fer.zemris.java.hw17.jvdraw.drawingModel;

/**
 * Listeners whether object is removed, changed or added to
 * {@link DrawingModel}.
 * 
 * @author Jelić, Nikola
 */
public interface DrawingModelListener {

	/**
	 * This method activates if object is added to model.
	 * 
	 * @param source DrawingModel to which object was added.
	 * @param index0 first index of interval of change.
	 * @param index1 second index of interval of change.
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * This method activates if object is removed from model.
	 * 
	 * @param source DrawingModel from which object was removed.
	 * @param index0 first index of interval of change.
	 * @param index1 second index of interval of change.
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);

	/**
	 * This method activates if object is changed.
	 * 
	 * @param source DrawingModel in which object was changed.
	 * @param index0 first index of interval of change.
	 * @param index1 second index of interval of change.
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);
}
