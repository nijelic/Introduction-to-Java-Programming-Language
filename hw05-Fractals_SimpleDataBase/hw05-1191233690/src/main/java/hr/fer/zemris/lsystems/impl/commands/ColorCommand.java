package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

import java.awt.Color;

/**
 * Used for updating color of current {@link TrueTypeFont}.
 * */
public class ColorCommand implements Command {

  /**
   * new color
   * */
  Color color;
  
  /**
   * Constructor sets color.
   * */
  public ColorCommand(Color color) {
    this.color = color;
  }
  
  /**
   * Updates color of current {@link TrueTypeFont}.
   * */
  @Override
    public void execute(Context ctx, Painter painter) {
      ctx.getCurrentState().setColor(color);
    }
}
