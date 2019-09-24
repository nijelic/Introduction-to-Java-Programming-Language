package hr.fer.zemris.java.hw17.jvdraw.objects;

import java.util.Objects;

import hr.fer.zemris.java.hw17.jvdraw.shapeButtons.Line;
import hr.fer.zemris.java.hw17.jvdraw.objects.editors.LineEditor;
import hr.fer.zemris.java.hw17.jvdraw.objects.editors.CircleEditor;
import hr.fer.zemris.java.hw17.jvdraw.objects.editors.FilledCircleEditor;
import hr.fer.zemris.java.hw17.jvdraw.shapeButtons.Circle;
import hr.fer.zemris.java.hw17.jvdraw.shapeButtons.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.shapeButtons.ToolImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link GeometricalObject}.
 * 
 * @author Jelić, Nikola
 */
public class GeometricalObjectImpl extends GeometricalObject {

	/**
	 * Tool which is used for drawing objact.
	 */
	private ToolImpl tool;
	/**
	 * Listeners of object's change.
	 */
	private List<GeometricalObjectListener> listeners = new ArrayList<>();

	/**
	 * Constructor sets tool.
	 *  
	 * @param tool to be set.
	 */
	public GeometricalObjectImpl(ToolImpl tool) {
		super();
		this.tool = Objects.requireNonNull(tool);
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		if(tool instanceof Line) {
			v.visit((Line)tool);
		} else if(tool instanceof Circle) {
			v.visit((Circle)tool);
		} else {
			v.visit((FilledCircle)tool);
		}		
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		if(tool instanceof Line) {
			return new LineEditor((Line)tool, listeners, this);
		} else if(tool instanceof Circle) {
			return new CircleEditor((Circle)tool, listeners, this);
		} else {
			return new FilledCircleEditor((FilledCircle)tool, listeners, this);
		}
	}

	@Override
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.add(l);
	}
	@Override
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.remove(l);
	}

	@Override
	public String toString() {
		return tool.toString();
	};

	
}
