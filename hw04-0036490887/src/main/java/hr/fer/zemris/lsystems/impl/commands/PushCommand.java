package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * {@link PushCommand} copies current turtle state and push it again on the
 * context.
 * 
 * @see {@link Context}
 * @author dario
 *
 */
public class PushCommand implements Command {

	/**
	 * It copies {@link TurtleState} from the peek of the {@link Context} and push
	 * it again on the {@link Context}.
	 * 
	 * @param ctx
	 *            - {@link Context} context of the working process
	 * @param painter
	 *            - {@link Painter} allows you to draw lines
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.pushState(ctx.getCurrentTurtleState().copy());

	}

}
