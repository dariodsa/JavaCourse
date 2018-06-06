package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * It pops the turtle state from the {@link Context}.
 * 
 * @author dario
 *
 */
public class PopCommand implements Command {

	/**
	 * It pops the turtle state from the {@link Context}.
	 * 
	 * @param ctx
	 *            - {@link Context} context of the working process
	 * @param painter
	 *            - {@link Painter} allows you to draw lines
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.popState();

	}

}
