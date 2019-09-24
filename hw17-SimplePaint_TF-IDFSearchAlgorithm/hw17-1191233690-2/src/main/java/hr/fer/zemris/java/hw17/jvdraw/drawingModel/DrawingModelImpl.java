package hr.fer.zemris.java.hw17.jvdraw.drawingModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObjectListener;

/**
 * Implementation of {@link DrawingModel}.
 * 
 * @author Jelić, Nikola
 */
public class DrawingModelImpl implements DrawingModel {

	/**
	 * List of objects.
	 */
	private List<GeometricalObject> objects = new ArrayList<>();
	/**
	 * List of listeners.
	 */
	private List<DrawingModelListener> listeners = new ArrayList<>();
	/**
	 * Flag that has info whether was model modified.
	 */
	private boolean modified;

	@Override
	public int getSize() {
		return objects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		return objects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		objects.add(Objects.requireNonNull(object));
		object.addGeometricalObjectListener(new GeometricalObjectListener() {
			
			@Override
			public void geometricalObjectChanged(GeometricalObject o) {
				int index = DrawingModelImpl.this.indexOf(object);
				DrawingModelImpl dm = DrawingModelImpl.this;
				listeners.forEach(l->l.objectsChanged(dm, index, index));
			}
		});
		listeners.forEach(l->l.objectsAdded(this, getSize()-1, getSize()-1));
		modified = true;
	}

	@Override
	public void remove(GeometricalObject object) {
		int index = objects.indexOf(object);
		objects.remove(object);
		listeners.forEach(l->l.objectsRemoved(this, index, index));
		modified = true;
	}

	@Override
	public void changeOrder(GeometricalObject object, int offset) {
		int index = indexOf(Objects.requireNonNull(object));
		index += offset; 
		if (0<=index && index < getSize() ) {
			objects.remove(object);
			objects.add(index, object);
			modified = true;
			final int idx = index;
			listeners.forEach(l->l.objectsChanged(this, idx-offset, idx));
		}
	}

	@Override
	public int indexOf(GeometricalObject object) {
		return objects.indexOf(object);
	}

	@Override
	public void clear() {
		if(getSize()==0) {
			return;
		}
		int index = getSize()-1;
		objects.clear();
		listeners.forEach(l->l.objectsRemoved(this, 0, index));
		modified = true;
	}

	@Override
	public void clearModifiedFlag() {
		modified = false;
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		listeners.add(l);
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);
	}
}
