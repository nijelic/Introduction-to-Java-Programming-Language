package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Copies current {@link TurtleState} and pushes to {@link Context}.
 * */
public class PushCommand implements Command {

  /**
   * Copies current {@link TurtleState} and pushes to {@link Context}.
   * @throws EmptyStackException - if there is no current state
   * */
  @Override
  public void execute(Context ctx, Painter painter) {
    TurtleState copy = ctx.getCurrentState().copy();
    ctx.pushState( copy );
  }
}
