package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * {@link RotateCommand} sets up the angle parameter and it will rotate turtle
 * by the given angle, on which is on the peek of the {@link Context}.
 * 
 * @author dario
 *
 */
public class RotateCommand implements Command {
	private double angle;

	/**
	 * It constructs the {@link RotateCommand} with the angle as the parameter.
	 * 
	 * @param angle
	 *            - parameter of the {@link RotateCommand} which is used in
	 *            {@link #execute(Context, Painter) execute}
	 */
	public RotateCommand(double angle) {
		this.angle = angle;
	}

	/**
	 * 
	 * @param ctx
	 *            - {@link Context} context of the working process
	 * @param painter
	 *            - {@link Painter} allows you to draw lines
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentTurtleState().getDirection().rotate(angle);

	}
}
