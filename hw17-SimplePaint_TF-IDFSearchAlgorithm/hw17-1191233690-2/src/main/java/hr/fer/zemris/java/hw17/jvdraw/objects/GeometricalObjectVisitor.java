package hr.fer.zemris.java.hw17.jvdraw.objects;

import hr.fer.zemris.java.hw17.jvdraw.shapeButtons.Line;
import hr.fer.zemris.java.hw17.jvdraw.shapeButtons.Circle;
import hr.fer.zemris.java.hw17.jvdraw.shapeButtons.FilledCircle;

/**
 * Visitor of {@link GeometricalObject}.
 * 
 * @author Jelić, Nikola
 */
public interface GeometricalObjectVisitor {
	
	/**
	 * Visitor method where object is type of line.
	 * 
	 * @param line
	 */
	 public abstract void visit(Line line);
	 /**
	  * Visitor method where object is type of circle.
	  * 
	  * @param circle
	  */
	 public abstract void visit(Circle circle);
	 /**
	  * Visitor method where object is type of filled circle.
	  * 
	  * @param filledCircle
	  */
	 public abstract void visit(FilledCircle filledCircle);
}
