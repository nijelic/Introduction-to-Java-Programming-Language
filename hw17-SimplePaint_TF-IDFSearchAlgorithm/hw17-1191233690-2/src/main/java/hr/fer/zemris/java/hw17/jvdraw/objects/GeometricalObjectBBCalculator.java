package hr.fer.zemris.java.hw17.jvdraw.objects;

import java.awt.Point;
import java.awt.Rectangle;

import hr.fer.zemris.java.hw17.jvdraw.shapeButtons.Circle;
import hr.fer.zemris.java.hw17.jvdraw.shapeButtons.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.shapeButtons.Line;
import hr.fer.zemris.java.hw17.jvdraw.shapeButtons.ToolCircle;

/**
 * Bounding box calculator. Used before exporting.
 * 
 * @author Jelić, Nikola
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {

	/**
	 * Top-left corner of the image.
	 */
	private Point topLeft;
	/**
	 * Bottom-right corner of the image.
	 */
	private Point bottomRight;

	@Override
	public void visit(Circle circle) {
		circleCalc(circle);
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		circleCalc(filledCircle);
	}

	@Override
	public void visit(Line line) {

		if (line.getStartX() == null || line.getEndX() == null) {
			return;
		}
		if(topLeft == null) {
			topLeft = new Point();
			bottomRight = new Point();
			
			topLeft.x = Math.min(line.getStartX(), line.getEndX());
			topLeft.y = Math.min(line.getStartY(), line.getEndY());
			bottomRight.x = Math.max(line.getStartX(), line.getEndX());
			bottomRight.y = Math.max(line.getStartY(), line.getEndY());
		}
		if (topLeft.x > Math.min(line.getStartX(), line.getEndX())) {
			topLeft.x = Math.min(line.getStartX(), line.getEndX());
		}
		if (topLeft.y > Math.min(line.getStartY(), line.getEndY())) {
			topLeft.y = Math.min(line.getStartY(), line.getEndY());
		}
		if (bottomRight.x < Math.max(line.getStartX(), line.getEndX())) {
			bottomRight.x = Math.max(line.getStartX(), line.getEndX());
		}
		if (bottomRight.y < Math.max(line.getStartY(), line.getEndY())) {
			bottomRight.y = Math.max(line.getStartY(), line.getEndY());
		}

	}

	/**
	 * Calculates corners of circles.
	 * 
	 * @param circle
	 */
	public void circleCalc(ToolCircle circle) {
		if (circle.getStartX() == null || circle.getR() == null) {
			return;
		}
		
		if(topLeft == null) {
			topLeft = new Point();
			bottomRight = new Point();
			
			topLeft.x = circle.getStartX() - circle.getR();
			topLeft.y = circle.getStartY() - circle.getR();
			bottomRight.x = circle.getStartX() + circle.getR();
			bottomRight.y = circle.getStartY() + circle.getR();
		}
		if (topLeft.x > circle.getStartX() - circle.getR()) {
			topLeft.x = circle.getStartX() - circle.getR();
		}
		if (topLeft.y > circle.getStartY() - circle.getR()) {
			topLeft.y = circle.getStartY() - circle.getR();
		}
		if (bottomRight.x < circle.getStartX() + circle.getR()) {
			bottomRight.x = circle.getStartX() + circle.getR();
		}
		if (bottomRight.y < circle.getStartY() + circle.getR()) {
			bottomRight.y = circle.getStartY() + circle.getR();
		}
	}
	
	/**
	 * Returns bounding box.
	 * 
	 * @return bounding box.
	 */
	public Rectangle getBoundingBox() {
		if(topLeft == null || bottomRight == null) {
			return null;
		}
		return new Rectangle(topLeft.x, topLeft.y, bottomRight.x-topLeft.x, bottomRight.y-topLeft.y);
	}
}
