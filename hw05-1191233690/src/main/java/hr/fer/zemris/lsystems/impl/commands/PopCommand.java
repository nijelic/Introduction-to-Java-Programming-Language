package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Removes current {@link TurtleState} from Context.
 * */
public class PopCommand implements Command {

  /**
   * Removes current {@link TurtleState} from Context.
   * @throws EmptyStackException if there is no state to remove
   * */
  @Override
  public void execute(Context ctx, Painter painter) {
    ctx.popState();
  }
}
