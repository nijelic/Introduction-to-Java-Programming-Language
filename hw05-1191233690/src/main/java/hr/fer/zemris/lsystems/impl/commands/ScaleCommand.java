package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Used for scaling effectiveLength of current {@link TurtleState} with factor.
 * */
public class ScaleCommand implements Command {

  /**
   * Scaler of effectiveLength
   * */
  private double factor;
  
  /**
   * Constructor sets factor.
   * */
  public ScaleCommand(double factor) {
    this.factor = factor;
  }
  
  /**
   * Updates effectiveLength of current {@link TurtleState} by factor.
   * */
  @Override
  public void execute(Context ctx, Painter painter) {
    double effectiveLength = ctx.getCurrentState().getEffectiveLength();
    ctx.getCurrentState().setEffectiveLength(factor * effectiveLength);
  }
}
