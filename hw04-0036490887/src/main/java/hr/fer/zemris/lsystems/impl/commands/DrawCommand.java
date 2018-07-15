package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;
/**
 * {@link DrawCommand} draws a line with {@link Painter} from the {@link TurtleState} which is on the peek 
 * of the {@link Context}.
 * @author dario
 *
 */
public class DrawCommand implements Command{
	private double move;
	/**
	 * Constructs {@link DrawCommand} with the double move as the atribute.
	 * @param move - how big move should turtle make
	 */
	public DrawCommand(double move) {
		this.move = move;
	}
	/**
	 * Draws line from current {@link TurtleState} to the new {@link TurtleState}.
	 * @param ctx - {@link Context} context of the working process
	 * @param painter - {@link Painter} allows you to draw lines 
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentTurtleState();
		double step = currentState.getStep();
		Vector2D newPosition = currentState.getPosition().translated(currentState.getDirection().scaled(step*move));
		
		painter.drawLine(
				currentState.getPosition().getX(), 
				currentState.getPosition().getY(),
				newPosition.getX(), newPosition.getY(), 
				currentState.getColor(), 1);
		ctx.getCurrentTurtleState().getPosition().translate(currentState.getDirection().scaled(step*move));
		
	}
}
