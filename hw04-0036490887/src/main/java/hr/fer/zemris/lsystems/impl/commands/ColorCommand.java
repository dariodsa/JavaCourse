package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * {@link ColorCommand} sets the color of the {@link TurtleState} which will be
 * on the peek of the {@link Context}.
 * 
 * @author dario
 *
 */
public class ColorCommand implements Command {

	private Color color;

	/**
	 * Constructs {@link ColorCommand} with the color set as their attribute.
	 * 
	 * @param color
	 */
	public ColorCommand(Color color) {
		this.color = color;
	}

	/**
	 * Sets the color of the turtle on the peek of the context.
	 * 
	 * @param ctx
	 *            - {@link Context} context of the working process
	 * @param painter
	 *            - {@link Painter} allows you to draw lines
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentTurtleState().setColor(color);

	}
}
