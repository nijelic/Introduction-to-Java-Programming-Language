package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Used for drawing line and updating position of current {@link TurtleState}.
 * */
public class DrawCommand implements Command {

  /**
   * coefficient of step
   * */
  private double step;

  /**
   * Constructor sets step.
   * @param step used for calculating shift of {@link TurtleState}
   * */
  public DrawCommand(double step) {
    this.step = step;
  }
  
  /**
   * Calculates new position of current {@link TurtleState} and draws line. 
   * */
  @Override
  public void execute(Context ctx, Painter painter) {
   
    Vector2D position = ctx.getCurrentState().getPosition();
    Vector2D orientation = ctx.getCurrentState().getOrientation();
    double effectiveLength = ctx.getCurrentState().getEffectiveLength();
    
    double x0 = position.getX();
    double y0 = position.getY();
    double x1 = position.getX() + step * effectiveLength * orientation.getX();
    double y1 = position.getY() + step * effectiveLength * orientation.getY();
   
    painter.drawLine(x0, y0, x1, y1, ctx.getCurrentState().getColor(), 1f);
    ctx.getCurrentState().setPosition(new Vector2D(x1, y1)); 
  }
}
