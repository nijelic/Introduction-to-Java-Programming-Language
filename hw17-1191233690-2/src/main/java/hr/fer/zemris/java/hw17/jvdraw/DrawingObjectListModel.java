package hr.fer.zemris.java.hw17.jvdraw;

import javax.swing.AbstractListModel;

import hr.fer.zemris.java.hw17.jvdraw.drawingModel.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.drawingModel.DrawingModelListener;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;

/**
 * List model for drawing objects.
 * 
 * @author Jelić, Nikola
 */
public class DrawingObjectListModel extends AbstractListModel<String> {

	/**
	 * Serial version identifier.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Drawing model used as collection of {@link GeometricalObject}s
	 */
	private DrawingModel dm;

	@Override
	public String getElementAt(int index) {
		return dm.getObject(index).toString();
	}

	@Override
	public int getSize() {
		return dm.getSize();
	}

	/**
	 * Constructor that sets {@link DrawingModel} and sets listener.
	 * 
	 * @param dm {@link DrawingModel}
	 */
	public DrawingObjectListModel(DrawingModel dm) {
		this.dm = dm;
		dm.addDrawingModelListener(new DrawingModelListener() {

			@Override
			public void objectsRemoved(DrawingModel source, int index0, int index1) {
				fireIntervalRemoved(this, index0, index1);
			}

			@Override
			public void objectsChanged(DrawingModel source, int index0, int index1) {
				fireContentsChanged(this, index0, index1);
			}

			@Override
			public void objectsAdded(DrawingModel source, int index0, int index1) {
				fireIntervalAdded(this, index0, index1);
			}
		});
	}

}
