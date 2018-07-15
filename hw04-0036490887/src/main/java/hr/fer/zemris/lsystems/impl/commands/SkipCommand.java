package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * {@link SkipCommand} move turtle from the {@link TurtleState} which is on the
 * peek of the {@link Context} to the new position.
 * 
 * @author dario
 *
 */
public class SkipCommand implements Command {
	private double move;

	/**
	 * Constructs {@link SkipCommand} with the double move as the parameter.
	 * 
	 * @param move
	 *            how big turtle move should be
	 */
	public SkipCommand(double move) {
		this.move = move;
	}

	/**
	 * Move turtle to the new position <b>wihtout</b> drawing line on the screen.
	 * 
	 * @param ctx
	 *            - {@link Context} context of the working process
	 * @param painter
	 *            - {@link Painter} allows you to draw lines
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentTurtleState();
		double step = currentState.getStep();
		Vector2D newPosition = currentState.getPosition().translated(currentState.getDirection().scaled(step * move));
		ctx.getCurrentTurtleState().getPosition().translate(currentState.getDirection().scaled(step * move));

	}
}
