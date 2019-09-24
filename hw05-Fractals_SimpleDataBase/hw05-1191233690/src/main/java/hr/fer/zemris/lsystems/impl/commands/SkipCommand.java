package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Used for updating position of current {@link TurtleState}. {@link TurtleState} shifts without drawing.
 * */
public class SkipCommand implements Command {
  
  /**
   * coefficient of step
   * */
  private double step;

  /**
   * Constructor sets step.
   * @param step used for calculating shift of {@link TurtleState}
   * */
  public SkipCommand(double step) {
    this.step = step;
  }
  
  /**
   * Calculates new position of current {@link TurtleState}. 
   * */
  @Override
  public void execute(Context ctx, Painter painter) {
   
    Vector2D position = ctx.getCurrentState().getPosition();
    Vector2D orientation = ctx.getCurrentState().getOrientation();
    double effectiveLength = ctx.getCurrentState().getEffectiveLength();
    

    double x1 = position.getX() + step * effectiveLength * orientation.getX();
    double y1 = position.getY() + step * effectiveLength * orientation.getY();

    ctx.getCurrentState().setPosition(new Vector2D(x1, y1)); 
  }
}
