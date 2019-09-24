/**
 * {@link hr.fer.zemris.lsystems.impl.commands.ColorCommand}
 * Used for updating color of current {@link TrueTypeFont}.
 * 
 * 
 * {@link hr.fer.zemris.lsystems.impl.commands.DrawCommand}
 * Used for drawing line and updating position of current {@link TurtleState}.
 * 
 * 
 * {@link hr.fer.zemris.lsystems.impl.commands.PopCommand}
 * Removes current {@link TurtleState} from Context.
 * 
 * 
 * {@link hr.fer.zemris.lsystems.impl.commands.PushCommand}
 * Copies current {@link TurtleState} and pushes to {@link Context}.
 * 
 * 
 * {@link hr.fer.zemris.lsystems.impl.commands.RotateCommand}
 * Used for rotating current {@link TurtleState}.
 * 
 * 
 * {@link hr.fer.zemris.lsystems.impl.commands.SkipCommand}
 * Used for updating position of current {@link TurtleState}. {@link TurtleState} shifts without drawing.
 * 
 * 
 * {@link hr.fer.zemris.lsystems.impl.commands.ScaleCommand}
 * Used for scaling effectiveLength of current {@link TurtleState} with factor.
 *
 * @author Jelić, Nikola
 *
 */
package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
