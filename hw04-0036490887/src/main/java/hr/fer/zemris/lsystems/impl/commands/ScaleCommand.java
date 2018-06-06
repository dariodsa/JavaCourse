package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * {@link ScaleCommand} scales the initial move, or basic step of the
 * {@link TurtleState}.
 * 
 * @author dario
 *
 */
public class ScaleCommand implements Command {

	private double factor;

	/**
	 * Constructs {@link ScaleCommand} with double factor as the parameter.
	 * 
	 * @param factor
	 *            how big or small should be current turtle step, intital move
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}

	/**
	 * It sets the turtle step by the factor.
	 * 
	 * @param ctx
	 *            - {@link Context} context of the working process
	 * @param painter
	 *            - {@link Painter} allows you to draw lines
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentTurtleState().setStep(factor * ctx.getCurrentTurtleState().getStep());

	}
}
