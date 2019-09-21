package hr.fer.zemris.java.hw17.jvdraw.objects;

import java.awt.Graphics;
import java.awt.Graphics2D;

import hr.fer.zemris.java.hw17.jvdraw.shapeButtons.Circle;
import hr.fer.zemris.java.hw17.jvdraw.shapeButtons.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.shapeButtons.Line;

/**
 * Used for painting {@link GeometricalObject}s.
 * 
 * @author Jelić, Nikola
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor {

	/**
	 * Graphics to be painted.
	 */
	private Graphics2D g2d;
	
	/**
	 * Constructor sets graphics.
	 * 
	 * @param g to be set.
	 */
	public GeometricalObjectPainter(Graphics g) {
		super();
		this.g2d = (Graphics2D)g;
	}

	@Override
	public void visit(Circle circle) {
		circle.paint(g2d);
	}
	
	@Override
	public void visit(FilledCircle filledCircle) {
		filledCircle.paint(g2d);
	}
	
	@Override
	public void visit(Line line) {
		line.paint(g2d);
	}
}
