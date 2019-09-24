package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Used for actions and updating TurtleState.
 * */
public interface Command {

  /**
   * This method executes command.
   * @param ctx Context of fractal
   * @param painter Used for fractal drawing
   * */
  void execute(Context ctx, Painter painter);
}
