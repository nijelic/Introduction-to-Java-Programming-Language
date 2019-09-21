package hr.fer.zemris.lsystems.impl.commands;


import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Used for rotating current {@link TurtleState}.
 * */
public class RotateCommand implements Command {

  /**
   * angle of rotation
   * */
  private double angle;
  
  /**
   * Constructor sets angle.
   * */
  public RotateCommand(double angle) {
    this.angle = angle;
  }
  
  /**
   * Rotates current {@link TurtleState}.
   * @throws EmptyStackException - if there is no current state
   * */
  @Override
    public void execute(Context ctx, Painter painter) {
      ctx.getCurrentState().getOrientation().rotate(angle);
    }
}
